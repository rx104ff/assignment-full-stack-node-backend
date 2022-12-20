package com.ecapital.assignment.models;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "salary")
    private Integer salary;

    public Employee() {}

    public Employee(String username, String password, Integer salary) {
        this.firstname = username;
        this.lastname = password;
        this.salary = salary;
    }

    public Long getId() {
        return this.id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getSalary() {
        return this.salary;
    }
}
