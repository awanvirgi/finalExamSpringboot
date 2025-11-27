package com.finalexam.trabea.parttimeemployee;

import com.finalexam.trabea.parttimeemployee.dto.*;
import com.finalexam.trabea.shared.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("employees/part-time")
public class PartTimeEmployeeController {
    private final PartTimeEmployeeService partTimeEmployeeService;

    @GetMapping
    public ResponseEntity<PageResponse<ResponsePartTimeSummary>> findAllPartTime(@PageableDefault Pageable pageable,
                                                                                 @Valid @RequestParam(required = false) String fullname) {
        return ResponseEntity.ok(new PageResponse<>(partTimeEmployeeService.findAllPartTime(fullname, pageable)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponsePartTimeForm> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(partTimeEmployeeService.findById(id));
    }

    @GetMapping("{id}/details")
    public ResponseEntity<ResponsePartTimeDetail> findByIdWithDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(partTimeEmployeeService.findByIdWithDetail(id));
    }


    @PostMapping
    public ResponseEntity<ResponsePartTime> insertNewPartTime(@Valid @RequestBody RequestPartTime request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partTimeEmployeeService.registerPartTime(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponsePartTime> updatePartTime(@PathVariable Integer id,
                                                           @Valid @RequestBody RequestPartTime request) {
        return ResponseEntity.ok(
                partTimeEmployeeService.editPartTime(request, id)
        );
    }

}
