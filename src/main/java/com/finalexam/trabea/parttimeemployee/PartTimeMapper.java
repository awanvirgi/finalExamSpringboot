package com.finalexam.trabea.parttimeemployee;

import com.finalexam.trabea.parttimeemployee.dto.RequestPartTime;
import com.finalexam.trabea.parttimeemployee.dto.ResponsePartTime;
import com.finalexam.trabea.parttimeemployee.dto.ResponsePartTimeForm;
import com.finalexam.trabea.parttimeemployee.dto.ResponsePartTimeDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PartTimeMapper {
    PartTimeEmployee toPartTime(RequestPartTime request);

    PartTimeEmployee updatePartTime(@MappingTarget PartTimeEmployee employee, RequestPartTime requestPartTime);

    ResponsePartTimeForm toResponseForm(PartTimeEmployee employee);

    @Mapping(target = "workEmail", source = "workEmail.workEmail")
    ResponsePartTimeDetail toResponseDetail(PartTimeEmployee employee);

    @Mapping(target = "workEmail",source = "workEmail.workEmail")
    @Mapping(target = "roles",ignore = true)
    ResponsePartTime toResponse(PartTimeEmployee employee);
}
