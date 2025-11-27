package com.finalexam.trabea.workschedule;

import com.finalexam.trabea.shared.dto.PageResponse;
import com.finalexam.trabea.workschedule.dto.RequestApproveWorkSchedule;
import com.finalexam.trabea.workschedule.dto.RequestWorkSchedule;
import com.finalexam.trabea.workschedule.dto.ResponseListWorkSchedule;
import com.finalexam.trabea.workschedule.dto.ResponseSubmision;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("schedules")
public class WorkScheduleController {
    private final WorkScheduleService workScheduleService;

    @GetMapping
    public ResponseEntity<ResponseListWorkSchedule> findAllScheduleOfWeek(@RequestParam(required = false) Boolean nextWeek) {
        return ResponseEntity.ok(workScheduleService.findAllWorkSchedules(nextWeek != null));
    }

    @PostMapping
    public ResponseEntity<Void> shiftApplication(@Valid @RequestBody RequestWorkSchedule request) {
        workScheduleService.submissionShift(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("submission")
    public ResponseEntity<PageResponse<ResponseSubmision>> findAllSubmission(
            @PageableDefault Pageable pageable
            , @RequestParam(required = false) Integer shiftId
            , @RequestParam(required = false) String fullname
            , @RequestParam(required = false) LocalDate startDate
            , @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(new PageResponse<>(workScheduleService.findAllSubmission(pageable, shiftId, fullname, startDate, endDate)));
    }

    @PatchMapping("submission/{id}")
    public ResponseEntity<Void> approveSubmission(@Valid @RequestBody RequestApproveWorkSchedule request, @PathVariable Integer id) {
        workScheduleService.approveSubmission(request, id);
        return ResponseEntity.ok().build();
    }
}
