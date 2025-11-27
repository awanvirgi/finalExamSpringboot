package com.finalexam.trabea.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum RolesName {
    ADMIN("Administator"),
    MANAGER("Manager"),
    PART_TIMER("PartTimer");
    private final String description;
}
