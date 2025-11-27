package com.finalexam.trabea.user;

import com.finalexam.trabea.employee.Employee;
import com.finalexam.trabea.parttimeemployee.PartTimeEmployee;
import com.finalexam.trabea.role.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "WorkEmail")
    private String workEmail;
    @Column(name = "Password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "UserRoles",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private List<Role> roles;

    @OneToOne(mappedBy = "workEmail")
    private Employee employees;
    @OneToOne(mappedBy = "workEmail")
    private PartTimeEmployee partTimeEmployees;


}
