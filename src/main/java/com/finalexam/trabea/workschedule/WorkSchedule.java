package com.finalexam.trabea.workschedule;

import com.finalexam.trabea.employee.Employee;
import com.finalexam.trabea.parttimeemployee.PartTimeEmployee;
import com.finalexam.trabea.workshift.WorkShift;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "WorkSchedules")
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "WorkDate")
    private LocalDate workDate;
    @Column(name = "IsApproved")
    private Boolean isApproved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ManagerId")
    private Employee managerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PartTimeEmployeeId")
    private PartTimeEmployee partTimeEmployeeId;
    @JoinColumn(name = "WorkShiftId")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkShift workShiftId;

}

