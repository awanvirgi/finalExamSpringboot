package com.finalexam.trabea.workshift;

import com.finalexam.trabea.workshift.dto.ResponseWorkShift;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("workshift")
public class WorkShiftController {

    private final WorkShiftService workShiftService;

    @GetMapping
    public ResponseEntity<List<ResponseWorkShift>> findAll(){
        return ResponseEntity.ok(workShiftService.findAll());
    }
}
