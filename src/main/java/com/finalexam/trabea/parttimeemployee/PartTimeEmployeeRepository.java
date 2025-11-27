package com.finalexam.trabea.parttimeemployee;

import com.finalexam.trabea.parttimeemployee.dto.ResponsePartTimeSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartTimeEmployeeRepository extends JpaRepository<PartTimeEmployee,Integer> {

    @Query(
            """
            SELECT 
            new com.finalexam.trabea.parttimeemployee.dto.ResponsePartTimeSummary(
                PE.id
                ,concat(PE.firstName,' ',PE.lastName) as fullname
                ,PE.personalEmail
                ,U.workEmail
                ,PE.personalPhoneNumber
                ,PE.joinDate
                )
            from PartTimeEmployee PE JOIN FETCH User U on PE.workEmail.workEmail = U.workEmail
            WHERE 
                (:fullname is null or LOWER(concat(PE.firstName,' ',PE.lastName)) LIKE LOWER(concat('%',:fullname,'%'))) 
                and PE.resignDate is null
            """)
    Page<ResponsePartTimeSummary> findAllPartTime(String fullname, Pageable pageable);

    @Query("""
            SELECT
            COUNT(PE) FROM PartTimeEmployee PE where lower(PE.firstName) = lower(:firstName) and lower(PE.lastName) = lower(:lastName)
            """)
    Integer countSameFullName(String firstName,String lastName);

    Optional<PartTimeEmployee> findByIdAndResignDateIsNull(Integer id);
    Optional<PartTimeEmployee> findByWorkEmailWorkEmail(String workEmail);

    Boolean existsByPersonalEmail(String email);
    Boolean existsByPersonalPhoneNumber(String phoneNumber);
}
