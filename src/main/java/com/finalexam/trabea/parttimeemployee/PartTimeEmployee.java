package com.finalexam.trabea.parttimeemployee;

import com.finalexam.trabea.education.EducationType;
import com.finalexam.trabea.user.User;
import com.finalexam.trabea.workschedule.WorkSchedule;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "PartTimeEmployees")
public class PartTimeEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "PersonalEmail",unique = true)
    private String personalEmail;
    @Column(name = "PersonalPhoneNumber",unique = true)
    private String personalPhoneNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "LastEducation")
    private EducationType lastEducation;
    @Enumerated(EnumType.STRING)
    @Column(name = "OngoingEducation")
    private EducationType onGoingEducation;
    @Column(name = "JoinDate")
    private LocalDate joinDate;
    @Column(name = "ResignDate")
    private LocalDate resignDate;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "WorkEmail")
    private User workEmail;

    @OneToMany(fetch = FetchType.LAZY)
    private List<WorkSchedule> workSchedules;

    @PrePersist
    private void onCreate(){
        if (this.joinDate == null){
            this.joinDate = LocalDate.now();
        }
    }
}
