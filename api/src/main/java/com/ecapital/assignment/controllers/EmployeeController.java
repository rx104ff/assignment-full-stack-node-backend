package com.ecapital.assignment.controllers;

import com.ecapital.assignment.dtos.NewEmployeeDto;
import com.ecapital.assignment.dtos.UpdateEmployeeDto;
import com.ecapital.assignment.models.Employee;
import com.ecapital.assignment.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("/employee")
    public ResponseEntity<?> newEmployee(@RequestBody @Valid NewEmployeeDto newEmployeeDto) {

        Employee employee = new Employee(newEmployeeDto.firstname, newEmployeeDto.lastname, newEmployeeDto.salary);

        employeeRepository.save(employee);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/employee")
    public ResponseEntity<?> editEmployee(@RequestBody @Valid UpdateEmployeeDto updateEmployeeDto) {

        Optional<Employee> employeeOptional = employeeRepository.findById(updateEmployeeDto.id);

        if (employeeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Employee employee = employeeOptional.get();
        employee.setFirstname(updateEmployeeDto.firstname);
        employee.setLastname(updateEmployeeDto.lastname);
        employee.setSalary(updateEmployeeDto.salary);

        employeeRepository.save(employee);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/employee/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        employeeRepository.delete(employeeOptional.get());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/employee/all")
    public ResponseEntity<?> getAllEmployees() {
        var employees = employeeRepository.findAll();

        return ResponseEntity.ok(employees);
    }
}
