package com.finalexam.trabea.education;

import com.finalexam.trabea.education.dto.ResponseEducation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("education")
public class EducationController {
    private final EducationService educationService;

    @GetMapping
    public ResponseEntity<List<ResponseEducation>> findAll(){
        return ResponseEntity.ok(educationService.findAll());
    }
}
