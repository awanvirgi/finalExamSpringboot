package com.finalexam.trabea.education;

import com.finalexam.trabea.education.dto.ResponseEducation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EducationService {

    public List<ResponseEducation> findAll(){
        List<ResponseEducation> educations = new ArrayList<>();
        for (EducationType value : EducationType.values()) {
            educations.add(new ResponseEducation(value.name(),value.getDescription()));
        }
        return educations;
    }
}
