package com.finalexam.trabea.workschedule;

import com.finalexam.trabea.workschedule.dto.ResponseSubmision;
import com.finalexam.trabea.workschedule.dto.ResponseWorkSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule,Integer> {

    @Query("""
       SELECT new com.finalexam.trabea.workschedule.dto.ResponseWorkSchedule(
            ws.id,
            u.id,
           CONCAT(u.firstName, ' ', u.lastName),
           ws.workDate,
           w.id
       )
       FROM WorkSchedule ws
       JOIN ws.partTimeEmployeeId u
       JOIN ws.workShiftId w
       WHERE
            ws.isApproved = true
            AND ws.workDate >= :startWeek
            AND  ws.workDate <= :endWeek
       """)
    List<ResponseWorkSchedule> findAll(LocalDate startWeek, LocalDate endWeek);

    @Query("""
       SELECT new com.finalexam.trabea.workschedule.dto.ResponseSubmision(
           ws.id,
           u.id,
           CONCAT(u.firstName, ' ', u.lastName),
           ws.workDate,
           new com.finalexam.trabea.workshift.dto.ResponseWorkShift(w.id, w.startTime, w.endTime)
       )
       FROM WorkSchedule ws
       JOIN ws.partTimeEmployeeId u
       JOIN ws.workShiftId w
       WHERE
            (:shiftId is null or w.id = :shiftId) and
            (:fullname is null or :fullname = '' or lower(concat(u.firstName,' ',u.lastName)) Like lower(concat('%',:fullname,'%')) ) and
            (:startDate is null or ws.workDate >= :startDate ) and
            (:endDate is null or ws.workDate <= :endDate) and
            ws.isApproved is null
       """)
    Page<ResponseSubmision> findAllSubmissionShift(Pageable pageable,Integer shiftId,String fullname,LocalDate startDate,LocalDate endDate);

    @Query("""
       SELECT ws
       FROM WorkSchedule ws
       JOIN ws.partTimeEmployeeId u
       JOIN FETCH ws.workShiftId w
       WHERE
            ws.isApproved = true  and
            u.id = :partTimeId and ws.workDate = :targetDate
       """)
    List<WorkSchedule> findAllWorkScheduleInParTime(Integer partTimeId,LocalDate targetDate);
    @Query("""
       SELECT ws
       FROM WorkSchedule ws
       JOIN ws.partTimeEmployeeId u
       JOIN FETCH ws.workShiftId w
       WHERE
            ws.isApproved is null and
            u.id = :partTimeId and ws.workDate = :targetDate
       """)
    List<WorkSchedule> findAllWorkScheduleInParTimeApproveNull(Integer partTimeId,LocalDate targetDate);

    @Query("""
       SELECT count(ws)
       FROM WorkSchedule ws
       JOIN ws.partTimeEmployeeId u
       WHERE
            ws.isApproved is true and
            u.id = :partTimeId and
            ws.workDate = :targetDate
       """)
    Integer CountWorkScheduleInParTimeThisDay(Integer partTimeId,LocalDate targetDate);

    @Query("""
       SELECT count(ws)
       FROM WorkSchedule ws
       JOIN ws.partTimeEmployeeId u
       WHERE
            u.id = :partTimeId
            AND ws.isApproved = true
            AND ws.workDate >= :startWeek
            AND  ws.workDate <= :endWeek
       """)
    Integer CountWorkScheduleInPartTimeThisWeek(LocalDate startWeek, LocalDate endWeek, Integer partTimeId);

    @Query("""
       SELECT ws
       FROM WorkSchedule ws
       JOIN ws.partTimeEmployeeId u
       JOIN ws.workShiftId w
       WHERE
            u.id = :partTimeId
            AND ws.isApproved is null
            AND ws.workDate >= :startWeek
            AND  ws.workDate <= :endWeek
       """)
    List<WorkSchedule> findAllWorkScheduleInPartTimeThisWeekApprovedNull(LocalDate startWeek, LocalDate endWeek,Integer partTimeId);

    Optional<WorkSchedule> findByIdAndIsApprovedIsNull(Integer id);
}
