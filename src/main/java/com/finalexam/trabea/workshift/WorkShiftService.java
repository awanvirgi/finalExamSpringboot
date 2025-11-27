package com.finalexam.trabea.workshift;

import com.finalexam.trabea.role.dto.ResponseRole;
import com.finalexam.trabea.workshift.dto.ResponseWorkShift;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkShiftService {
    private final WorkShiftRepository workShiftRepository;

    public List<ResponseWorkShift> findAll(){
        return workShiftRepository.findAll()
                .stream()
                .map(workShift ->{
                   return new ResponseWorkShift(workShift.getId(),workShift.getStartTime(),workShift.getEndTime());
                })
                .toList();
    }
}
