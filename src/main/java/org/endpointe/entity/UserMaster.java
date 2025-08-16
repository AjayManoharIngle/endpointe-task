package org.endpointe.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_USER_M", uniqueConstraints = {@UniqueConstraint(columnNames = "S_EMAIL")})
public class UserMaster {

    @Id
    @Column(name = "N_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name can't be null")
    @Column(name = "S_NAME", nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    @Column(name = "S_EMAIL",nullable = false, unique = true)
    private String email;

    @Column(name="S_DEPARTMENT_NAME")
    private String departmentName;

    @Column(name="N_SALARY")
    private Double salary;

    @Column(name = "DT_JOINING_DATE")
    private LocalDate joiningDate;
    
    @Column(name = "S_PASSWORD")
    private String password;
    
}