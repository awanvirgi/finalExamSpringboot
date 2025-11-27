package com.finalexam.trabea.parttimeemployee;

import com.finalexam.trabea.error.exception.ConflictException;
import com.finalexam.trabea.error.exception.ResourceNotFoundException;
import com.finalexam.trabea.parttimeemployee.dto.*;
import com.finalexam.trabea.role.Role;
import com.finalexam.trabea.role.RoleRepository;
import com.finalexam.trabea.role.RolesName;
import com.finalexam.trabea.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartTimeEmployeeService {
    private final PartTimeEmployeeRepository partTimeEmployeeRepository;
    private final RoleRepository roleRepository;
    private final PartTimeMapper partTimeMapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.defaultpassword}") // ada di application.properties
    private String defaultPassword;


    public Page<ResponsePartTimeSummary> findAllPartTime(String fullname, Pageable pageable) {
        return partTimeEmployeeRepository.findAllPartTime(fullname, pageable);
    }

    public ResponsePartTimeDetail findByIdWithDetail(Integer id) {
        PartTimeEmployee partTimeEmployee = partTimeEmployeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Part-time employee with id %s was not found".formatted(id))
        );
        ResponsePartTimeDetail response = partTimeMapper.toResponseDetail(partTimeEmployee
        );
        response.setFullName(partTimeEmployee.getFirstName()+" "+partTimeEmployee.getLastName());
        return response;
    }

    public ResponsePartTimeForm findById(Integer id) {
        return partTimeMapper.toResponseForm(
                partTimeEmployeeRepository.findByIdAndResignDateIsNull(id).orElseThrow(
                        () -> new ResourceNotFoundException("Part-time employee with id %s was not found".formatted(id))
                )
        );
    }

    @Transactional
    public ResponsePartTime registerPartTime(RequestPartTime request) {
        if (partTimeEmployeeRepository.existsByPersonalEmail(request.getPersonalEmail())) {
            throw new ConflictException("Email has already been used");
        }
        if (partTimeEmployeeRepository.existsByPersonalPhoneNumber(request.getPersonalPhoneNumber())) {
            throw new ConflictException(("Phone number has already been used"));
        }
        PartTimeEmployee partTimeEmployee = partTimeMapper.toPartTime(request);

        User newUser = new User();
        String fullname = (request.getFirstName() + "." + request.getLastName()).toLowerCase();
        Integer countSameFullname = partTimeEmployeeRepository.countSameFullName(fullname);
        if (countSameFullname > 0) {
            fullname = fullname + (countSameFullname + 1);
        }
        String workEmail = fullname + "@trabea.co.id";
        newUser.setWorkEmail(workEmail);
        newUser.setPassword(passwordEncoder.encode(defaultPassword));
        Role role = roleRepository.findByName(RolesName.PART_TIMER).orElseThrow();
        newUser.setRoles(new ArrayList<>(List.of(role)));

        partTimeEmployee.setWorkEmail(newUser);
        PartTimeEmployee saved = partTimeEmployeeRepository.save(partTimeEmployee);
        ResponsePartTime response = partTimeMapper.toResponse(saved);
        List<String> roles = new ArrayList<>();
        for (Role role1 : saved.getWorkEmail().getRoles()) {
            roles.add(role1.getName().name());
        }
        response.setRoles(roles);
        return response;
    }

    @Transactional
    public ResponsePartTime editPartTime(RequestPartTime request, Integer id) {
        PartTimeEmployee partTimeEmployee = partTimeEmployeeRepository.findByIdAndResignDateIsNull(id).orElseThrow(
                () -> new ResourceNotFoundException("Part-time employee with id %s was not found".formatted(id))
        );
        partTimeEmployee = partTimeMapper.updatePartTime(partTimeEmployee, request);
        PartTimeEmployee saved = partTimeEmployeeRepository.save(partTimeEmployee);
        ResponsePartTime response = partTimeMapper.toResponse(saved);
        List<String> roles = new ArrayList<>();
        for (Role role1 : saved.getWorkEmail().getRoles()) {
            roles.add(role1.getName().name());
        }
        response.setRoles(roles);
        return response;
    }

}
