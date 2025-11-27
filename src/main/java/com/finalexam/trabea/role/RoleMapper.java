package com.finalexam.trabea.role;

import com.finalexam.trabea.role.dto.ResponseRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "description",source = "name.description")
    ResponseRole toResponse(Role role);
}
