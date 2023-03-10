package com.ecapital.assignment.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class NewEmployeeDto {
    @NotBlank(message = "First name cannot be blank")
    public String firstname;

    @NotBlank(message = "Last name cannot be blank")
    public String lastname;

    @NotNull(message = "Salary must not be null")
    @PositiveOrZero(message = "Come on seriously?")
    public Integer salary;
}
