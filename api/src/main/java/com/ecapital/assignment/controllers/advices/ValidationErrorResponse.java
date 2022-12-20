package com.ecapital.assignment.controllers.advices;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<ValidationViolation> violations = new ArrayList<>();

    public List<ValidationViolation> getViolations() {
        return violations;
    }

    public void setViolations(List<ValidationViolation> violations) {
        this.violations = violations;
    }
}
