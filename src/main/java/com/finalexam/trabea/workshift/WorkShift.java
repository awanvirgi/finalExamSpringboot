package com.finalexam.trabea.workshift;

import com.finalexam.trabea.workschedule.WorkSchedule;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "WorkShifts")
public class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "StartTime")
    private LocalTime startTime;
    @Column(name = "EndTime")
    private LocalTime endTime;

    @OneToMany(mappedBy = "workShiftId")
    private List<WorkSchedule> workSchedules;

}
