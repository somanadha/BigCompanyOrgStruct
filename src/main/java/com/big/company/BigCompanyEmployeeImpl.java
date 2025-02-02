package com.big.company;

import lombok.Getter;
import java.util.Vector;

@Getter
public final class BigCompanyEmployeeImpl extends BigCompanyEmployee {
    private boolean isCompletelyInitialized;
    private boolean isSubordinateAverageSalaryRecalculationNeeded;
    private double subordinatesAverageSalary;

    private BigCompanyEmployee manager;
    private final Vector<BigCompanyEmployee> subordinateEmployees = new Vector<>();

    public BigCompanyEmployeeImpl(Integer employeeId) {
        this.employeeId = employeeId;
        isCompletelyInitialized = false;
    }

    public BigCompanyEmployeeImpl(Integer employeeId, String lastName, String firstName,
                                  Double salary, BigCompanyEmployee manager) {
        updateEmployee(employeeId, lastName, firstName, salary, manager);
    }

    public boolean isManager() {
        return !subordinateEmployees.isEmpty();
    }

    public Double getSubordinatesAverageSalary() {
        if (isSubordinateAverageSalaryRecalculationNeeded) {
            subordinatesAverageSalary = subordinateEmployees.stream()
                    .mapToDouble(subordinate -> subordinate.salary)
                    .average().getAsDouble();
        }
        return subordinatesAverageSalary;
    }

    public int getEmployeeLevelInHierarchy() {
        int levelInHierarchy = 1;
        if (manager != null) {
            levelInHierarchy += manager.getEmployeeLevelInHierarchy();
        }
        return levelInHierarchy;
    }

    public void updateEmployee(Integer employeeId, String lastName, String firstName,
                               Double salary, BigCompanyEmployee manager) {
        if (!isCompletelyInitialized) {
            this.employeeId = employeeId;
            this.lastName = lastName;
            this.firstName = firstName;
            this.salary = salary;
            this.manager = manager;
            this.managerId = manager.employeeId;
            isCompletelyInitialized = true;
        } else {
            //throw new Exception("Duplicate Data");
        }

    }

    public void addSubordinate(BigCompanyEmployee employee) {
        subordinateEmployees.add(employee);
        isSubordinateAverageSalaryRecalculationNeeded = true;
        // Check for recursive data
    }

    public String toString() {
        StringBuilder stringValue = new StringBuilder("{" + super.toString());
        subordinateEmployees.forEach(employee -> stringValue.append("{").append(employee.toString()).append("}"));
        return stringValue.append("}").toString();
    }

    public String toShortString() {
        StringBuilder stringValue = new StringBuilder("{" + super.toShortString());
        subordinateEmployees.forEach(employee -> stringValue.append("{").append(employee.toShortString()).append("}"));
        return stringValue.append("}").toString();
    }
}
