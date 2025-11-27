package com.finalexam.trabea.role;

import com.finalexam.trabea.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "Name")
    private RolesName name;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;
}
