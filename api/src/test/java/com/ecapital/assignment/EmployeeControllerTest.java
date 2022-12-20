package com.ecapital.assignment;

import com.ecapital.assignment.controllers.EmployeeController;
import com.ecapital.assignment.dtos.NewEmployeeDto;
import com.ecapital.assignment.dtos.UpdateEmployeeDto;
import com.ecapital.assignment.models.Employee;
import com.ecapital.assignment.repositories.EmployeeRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeRepository repository;

    @Before
    public void setUp() throws Exception {
        reset(repository);
    }

    @Test
    public void whenPostEmployee_thenCreateEmployee() throws Exception {
        NewEmployeeDto ning = new NewEmployeeDto();
        ning.firstname = "Ning";
        ning.lastname = "Gao";
        ning.salary = 100000;

        JSONObject ningJson = new JSONObject();
        ningJson.put("firstname", ning.firstname);
        ningJson.put("lastname", ning.lastname);
        ningJson.put("salary", ning.salary);


        mvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ningJson.toString())).andExpect(status().is2xxSuccessful());

        verify(repository, VerificationModeFactory.times(1)).save(Mockito.any());
    }

    @Test
    public void whenPostEmployee_negativeSalary_thenReturnErrors() throws Exception {
        NewEmployeeDto ningNegativeSalary = new NewEmployeeDto();
        ningNegativeSalary.firstname = "Ning";
        ningNegativeSalary.lastname = "Gao";
        ningNegativeSalary.salary = -100000;

        JSONObject negativeSalaryNing = new JSONObject();
        negativeSalaryNing.put("firstname", ningNegativeSalary.firstname);
        negativeSalaryNing.put("lastname", ningNegativeSalary.lastname);
        negativeSalaryNing.put("salary", ningNegativeSalary.salary);

        var fieldNames = new ArrayList<String>();
        fieldNames.add("salary");

        var messages = new ArrayList<String>();
        messages.add("Come on seriously?");

        mvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(negativeSalaryNing.toString())).andExpect(status().isBadRequest())
                .andExpect(jsonPath("violations", hasSize(fieldNames.size()))) //ok
                .andExpect(jsonPath("violations[?(@)].fieldName").value(containsInAnyOrder(fieldNames.toArray())))
                .andExpect(jsonPath("violations[?(@)].message").value(containsInAnyOrder(messages.toArray())));

        verify(repository, VerificationModeFactory.times(0)).save(Mockito.any());
    }

    @Test
    public void whenPostEmployee_invalidBody_thenReturnErrors() throws Exception {
        NewEmployeeDto invalidInput = new NewEmployeeDto();
        invalidInput.firstname = "";
        invalidInput.lastname = "";
        invalidInput.salary = null;

        JSONObject invalidInputJson = new JSONObject();
        invalidInputJson.put("firstname", invalidInput.firstname);
        invalidInputJson.put("lastname", invalidInput.lastname);
        invalidInputJson.put("salary", invalidInput.salary);

        var fieldNames = new ArrayList<String>();
        fieldNames.add("firstname");
        fieldNames.add("lastname");
        fieldNames.add("salary");

        var messages = new ArrayList<String>();
        messages.add("First name cannot be blank");
        messages.add("Last name cannot be blank");
        messages.add("Salary must not be null");

        mvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidInputJson.toString())).andExpect(status().isBadRequest())
                .andExpect(jsonPath("violations", hasSize(fieldNames.size()))) //ok
                .andExpect(jsonPath("violations[?(@)].fieldName").value(containsInAnyOrder(fieldNames.toArray())))
                .andExpect(jsonPath("violations[?(@)].message").value(containsInAnyOrder(messages.toArray())));

        verify(repository, VerificationModeFactory.times(0)).save(Mockito.any());
    }

    @Test
    public void whenPutEmployee_thenUpdateEmployee() throws Exception {
        Employee ning = new Employee("Ning", "Gao", 100000);
        when(repository.findById(any())).thenReturn(Optional.of(ning));

        UpdateEmployeeDto ningToUpdate = new UpdateEmployeeDto();
        ningToUpdate.id = 1L;
        ningToUpdate.firstname = "Ningyuan";
        ningToUpdate.lastname = "Gao";
        ningToUpdate.salary = 110000;

        JSONObject ningJson = new JSONObject();
        ningJson.put("id", ningToUpdate.id);
        ningJson.put("firstname", ningToUpdate.firstname);
        ningJson.put("lastname", ningToUpdate.lastname);
        ningJson.put("salary", ningToUpdate.salary);


        mvc.perform(put("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ningJson.toString())).andExpect(status().is2xxSuccessful());

        verify(repository, VerificationModeFactory.times(1)).save(Mockito.any());
    }

    @Test
    public void whenPutEmployee_negativeSalary_thenReturnErrors() throws Exception {
        Employee ning = new Employee("Ning", "Gao", 100000);
        when(repository.findById(any())).thenReturn(Optional.of(ning));

        UpdateEmployeeDto ningToUpdate = new UpdateEmployeeDto();
        ningToUpdate.id = 1L;
        ningToUpdate.firstname = "Ningyuan";
        ningToUpdate.lastname = "Gao";
        ningToUpdate.salary = -110000;

        JSONObject negativeSalaryNing = new JSONObject();
        negativeSalaryNing.put("firstname", ningToUpdate.firstname);
        negativeSalaryNing.put("lastname", ningToUpdate.lastname);
        negativeSalaryNing.put("salary", ningToUpdate.salary);

        var fieldNames = new ArrayList<String>();
        fieldNames.add("salary");

        var messages = new ArrayList<String>();
        messages.add("Come on seriously?");

        mvc.perform(put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(negativeSalaryNing.toString())).andExpect(status().isBadRequest())
                .andExpect(jsonPath("violations", hasSize(fieldNames.size()))) //ok
                .andExpect(jsonPath("violations[?(@)].fieldName").value(containsInAnyOrder(fieldNames.toArray())))
                .andExpect(jsonPath("violations[?(@)].message").value(containsInAnyOrder(messages.toArray())));

        verify(repository, VerificationModeFactory.times(0)).save(Mockito.any());
    }

    @Test
    public void whenPutEmployee_invalidBody_thenReturnErrors() throws Exception {
        Employee ning = new Employee("Ning", "Gao", 100000);
        when(repository.findById(any())).thenReturn(Optional.of(ning));

        UpdateEmployeeDto invalidInput = new UpdateEmployeeDto();
        invalidInput.id = 1L;
        invalidInput.firstname = "";
        invalidInput.lastname = "";
        invalidInput.salary = null;

        JSONObject invalidInputJson = new JSONObject();
        invalidInputJson.put("firstname", invalidInput.firstname);
        invalidInputJson.put("lastname", invalidInput.lastname);
        invalidInputJson.put("salary", invalidInput.salary);

        var fieldNames = new ArrayList<String>();
        fieldNames.add("firstname");
        fieldNames.add("lastname");
        fieldNames.add("salary");

        var messages = new ArrayList<String>();
        messages.add("First name cannot be blank");
        messages.add("Last name cannot be blank");
        messages.add("Salary must not be null");

        mvc.perform(put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidInputJson.toString())).andExpect(status().isBadRequest())
                .andExpect(jsonPath("violations", hasSize(fieldNames.size()))) //ok
                .andExpect(jsonPath("violations[?(@)].fieldName").value(containsInAnyOrder(fieldNames.toArray())))
                .andExpect(jsonPath("violations[?(@)].message").value(containsInAnyOrder(messages.toArray())));

        verify(repository, VerificationModeFactory.times(0)).save(Mockito.any());
    }

    @Test
    public void whenPutEmployee_notExist_thenReturnErrors() throws Exception {
        Employee ning = new Employee("Ning", "Gao", 100000);
        when(repository.findById(1L)).thenReturn(Optional.of(ning));

        UpdateEmployeeDto ningToUpdate = new UpdateEmployeeDto();
        ningToUpdate.id = 2L;
        ningToUpdate.firstname = "Ningyuan";
        ningToUpdate.lastname = "Gao";
        ningToUpdate.salary = 110000;

        JSONObject ningJson = new JSONObject();
        ningJson.put("id", ningToUpdate.id);
        ningJson.put("firstname", ningToUpdate.firstname);
        ningJson.put("lastname", ningToUpdate.lastname);
        ningJson.put("salary", ningToUpdate.salary);

        mvc.perform(put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ningJson.toString())).andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Employee does not exist")));

        verify(repository, VerificationModeFactory.times(0)).save(Mockito.any());
    }

    @Test
    public void whenDeleteEmployee_thenDeleteEmployee() throws Exception {
        Employee ning = new Employee("Ning", "Gao", 100000);
        repository.save(ning);
        when(repository.findById(1L)).thenReturn(Optional.of(ning));


        mvc.perform(delete("/api/employee")
                        .param("id", String.valueOf(1))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        verify(repository, VerificationModeFactory.times(1)).delete(Mockito.any());
    }

    @Test
    public void whenDeleteEmployee_notExist_thenReturnNotFound() throws Exception {
        Employee ning = new Employee("Ning", "Gao", 100000);
        repository.save(ning);
        when(repository.findById(1L)).thenReturn(Optional.of(ning));


        mvc.perform(delete("/api/employee")
                        .param("id", String.valueOf(2))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Employee does not exist")));

        verify(repository, VerificationModeFactory.times(0)).delete(Mockito.any());
    }

    @Test
    public void whenGetAllEmployees_thenReturnEmployees() throws Exception {
        Employee ning = new Employee("Ning", "Gao", 100000);
        Employee kevin = new Employee("Kevin", "Minamoto", 100000);
        Employee bob = new Employee("Bob", "Taira", 100000);

        var employees = new ArrayList<Employee>();
        employees.add(ning);
        employees.add(kevin);
        employees.add(bob);

        when(repository.findAll()).thenReturn(employees);

        mvc.perform(get("/api/employee/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(employees.size())));
    }
}

