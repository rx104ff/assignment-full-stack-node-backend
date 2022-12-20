package com.ecapital.assignment.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateEmployeeDto {
    public Long id;

    @NotBlank(message = "First name cannot be blank")
    public String firstname;

    @NotBlank(message = "Last name cannot be blank")
    public String lastname;

    @PositiveOrZero(message = "Come on seriously?")
    public Integer salary;
}
