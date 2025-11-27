package com.finalexam.trabea.workschedule;

import com.finalexam.trabea.employee.Employee;
import com.finalexam.trabea.employee.EmployeeRepository;
import com.finalexam.trabea.error.exception.BadRequestException;
import com.finalexam.trabea.error.exception.ConflictException;
import com.finalexam.trabea.error.exception.ResourceNotFoundException;
import com.finalexam.trabea.parttimeemployee.PartTimeEmployee;
import com.finalexam.trabea.parttimeemployee.PartTimeEmployeeRepository;
import com.finalexam.trabea.workschedule.dto.RequestApproveWorkSchedule;
import com.finalexam.trabea.workschedule.dto.RequestWorkSchedule;
import com.finalexam.trabea.workschedule.dto.ResponseListWorkSchedule;
import com.finalexam.trabea.workschedule.dto.ResponseSubmision;
import com.finalexam.trabea.workshift.WorkShift;
import com.finalexam.trabea.workshift.WorkShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;
    private final WorkShiftRepository workShiftRepository;
    private final PartTimeEmployeeRepository partTimeEmployeeRepository;
    private final EmployeeRepository employeeRepository;

    public ResponseListWorkSchedule findAllWorkSchedules(Boolean nextWeek){
        LocalDate nowDay = LocalDate.now();
        int nowDayInNumber = nowDay.getDayOfWeek().getValue();
        int gapStartWeek = (nowDayInNumber - DayOfWeek.MONDAY.getValue()) ;
        int gapEndWeek = (DayOfWeek.FRIDAY.getValue()-nowDayInNumber ) ;
        LocalDate startWeek = LocalDate.now().plusDays(nextWeek?7:0).minusDays(gapStartWeek);
        LocalDate endWeek = LocalDate.now().plusDays(nextWeek?7:0).plusDays(gapEndWeek);
        return new ResponseListWorkSchedule(startWeek,endWeek,workScheduleRepository.findAll(startWeek,endWeek));
    }

    public Page<ResponseSubmision> findAllSubmission(Pageable pageable,Integer shiftId,String fullname,LocalDate startDate,LocalDate endDate){
        return workScheduleRepository.findAllSubmissionShift(pageable,shiftId,fullname,startDate,endDate);
    }

    @Transactional
    public void submissionShift(RequestWorkSchedule request){
        WorkShift workShift = workShiftRepository.findById(request.getWorkShiftId()).orElseThrow(
                ()->new ResourceNotFoundException("Shift with id %s was not found".formatted(request.getWorkShiftId()))
        );
        LocalDate startWeek = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(1);
        if (request.getWorkDate().isBefore(startWeek)){
            throw new BadRequestException("Shift submissions are only open for the upcoming week");
        }
        LocalDate endWeek = startWeek.plusDays(4);
        if (request.getWorkDate().isBefore(startWeek) || request.getWorkDate().isAfter(endWeek)){
            throw new BadRequestException("Submission date is out of the allowed range. Allowed: %s to %s.".formatted(startWeek,endWeek));
        }
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PartTimeEmployee targetPartTime = partTimeEmployeeRepository.findByWorkEmailWorkEmail(userDetails.getUsername()).orElseThrow(
                ()->new UsernameNotFoundException("Part-time employee with Email %s was not found".formatted(userDetails.getUsername()))
        );

        List<WorkSchedule> workSchedules = workScheduleRepository.findAllWorkScheduleInParTime(targetPartTime.getId(),request.getWorkDate());
        if (workSchedules.size() >= 2){
            throw new ConflictException("You have reached the maximum shift limit for this date.");
        }
        if (!workSchedules.isEmpty()){
            if (!workSchedules.getFirst().getWorkShiftId().getEndTime().equals(workShift.getStartTime())){
                if (!workSchedules.getFirst().getWorkShiftId().getStartTime().equals(workShift.getEndTime())){
                    throw new ConflictException("Shift selection must be sequential. Please select the previous or next shift.");
                }
            }
            if (workScheduleRepository.CountWorkScheduleInPartTimeThisWeek(startWeek,endWeek,targetPartTime.getId()) >= 5){
                throw new ConflictException("Weekly quota exceeded. You are limited to 5 shifts per week.");
            }
        }

        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setWorkShiftId(workShift);
        workSchedule.setWorkDate(request.getWorkDate());
        workSchedule.setPartTimeEmployeeId(targetPartTime);
        workScheduleRepository.save(workSchedule);
    }

    @Transactional
    public void approveSubmission(RequestApproveWorkSchedule request, Integer id){
        WorkSchedule workSchedule  = workScheduleRepository.findByIdAndIsApprovedIsNull(id).orElseThrow(
                ()->new ResourceNotFoundException("Submission WorkSchedule with id %s was not found".formatted(id))
        );
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Employee targetEmployee = employeeRepository.findByWorkEmailWorkEmail(userDetails.getUsername()).orElseThrow(
                ()->new UsernameNotFoundException("Employee with Email %s was not found".formatted(userDetails.getUsername()))
        );

        PartTimeEmployee targetPartTime = workSchedule.getPartTimeEmployeeId();
        List<WorkSchedule> workSchedules = workScheduleRepository.findAllWorkScheduleInParTime(targetPartTime.getId(),workSchedule.getWorkDate());
        if (workSchedules.size() >= 2){
            throw new ConflictException("PartTime have reached the maximum shift limit for this date.");
        }

        LocalDate targetday = workSchedule.getWorkDate();
        LocalDate startDate = targetday.with(DayOfWeek.MONDAY);
        LocalDate endDate = startDate.plusDays(4);

        if (!workSchedules.isEmpty()){
            if (!workSchedules.getFirst().getWorkShiftId().getEndTime().equals(workSchedule.getWorkShiftId().getStartTime())){
                if (!workSchedules.getFirst().getWorkShiftId().getStartTime().equals(workSchedule.getWorkShiftId().getEndTime())){
                    throw new ConflictException("Shift selection must be sequential. Please select the previous or next shift.");
                }
            }
            if (workScheduleRepository.CountWorkScheduleInPartTimeThisWeek(startDate,endDate,targetPartTime.getId()) >= 5){
                throw new ConflictException("Weekly quota exceeded. Part-Time Employee are limited to 5 shifts per week.");
            }
        }

        workSchedule.setIsApproved(request.getApprove());
        workSchedule.setManagerId(targetEmployee);
        workSchedule.setId(id);
        workScheduleRepository.save(workSchedule);

        if (workScheduleRepository.CountWorkScheduleInParTimeThisDay(targetPartTime.getId(),workSchedule.getWorkDate()) >= 2){
            List<WorkSchedule> proposalWorkScheduleThisDay = workScheduleRepository.findAllWorkScheduleInParTimeApproveNull(targetPartTime.getId(),workSchedule.getWorkDate());
            for (WorkSchedule schedule : proposalWorkScheduleThisDay) {
                schedule.setIsApproved(false);
                schedule.setManagerId(targetEmployee);
            }
            workScheduleRepository.saveAll(proposalWorkScheduleThisDay);
        }
        if (workScheduleRepository.CountWorkScheduleInPartTimeThisWeek(startDate,endDate,targetPartTime.getId()) >= 5){
            List<WorkSchedule> proposalWorkScheduleThisWeek = workScheduleRepository.findAllWorkScheduleInPartTimeThisWeekApprovedNull(startDate,endDate,targetPartTime.getId());
            for (WorkSchedule schedule : proposalWorkScheduleThisWeek) {
                schedule.setIsApproved(false);
                schedule.setManagerId(targetEmployee);
            }
            workScheduleRepository.saveAll(proposalWorkScheduleThisWeek);
        }
    }

}
