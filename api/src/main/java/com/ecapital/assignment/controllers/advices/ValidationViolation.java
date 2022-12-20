package com.ecapital.assignment.controllers.advices;


/**
 * Structure of a validation violation contains a field name and a message
 * */
public record ValidationViolation(String fieldName, String message) {

}
