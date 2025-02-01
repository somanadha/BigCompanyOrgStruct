package com.big.company;

import lombok.Getter;
import lombok.Setter;

import java.util.Vector;

@Getter
public class BigCompanyEmployee {

    private final Integer employeeId;
    private final String lastName;
    private final String firstName;
    private final Double salary;
    private final Integer managerId;
    private final Vector<Integer> subordinateIds = new Vector<>();

    public BigCompanyEmployee (Integer employeeId, String lastName, String firstName, Double salary, Integer managerId) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName= firstName;
        this.salary = salary;
        this.managerId = managerId;
    }

    //public void updateEmployee (Integer employeeId, String lastName, String firstName, Double salary, Integer managerId)

    public boolean isManager() {
        return !subordinateIds.isEmpty();
    }

}
