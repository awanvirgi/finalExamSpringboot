package com.finalexam.trabea.role;

import com.finalexam.trabea.role.dto.ResponseRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<ResponseRole> findAllRole() {
        return roleRepository.findAll()
                .stream()
                .map(role -> roleMapper.toResponse(role))
                .toList();
    }
}
