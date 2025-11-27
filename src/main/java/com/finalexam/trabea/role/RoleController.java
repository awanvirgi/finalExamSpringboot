package com.finalexam.trabea.role;

import com.finalexam.trabea.role.dto.ResponseRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<ResponseRole>> findAll(){
        return ResponseEntity.ok(roleService.findAllRole());
    }
}
