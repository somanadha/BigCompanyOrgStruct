package com.big.company;

import lombok.Getter;

import java.util.Vector;

@Getter
public final class BigCompanyEmployee {
    private Integer employeeId;
    private String lastName;
    private String firstName;
    private Double salary;
    private Integer managerId;

    private boolean isCompletelyInitialized;
    private boolean isSubordinateAverageSalaryRecalculationNeeded;
    private double subordinatesAverageSalary;
    private boolean isLevelInHierarchyCalculated;
    private int levelInHierarchy;

    private BigCompanyEmployee manager;
    private final Vector<BigCompanyEmployee> subordinateEmployees = new Vector<>();

    public BigCompanyEmployee(Integer employeeId) {
        this.employeeId = employeeId;
        isCompletelyInitialized = false;
    }

    public BigCompanyEmployee(Integer employeeId, String lastName, String firstName, Double salary,
                              BigCompanyEmployee manager) {
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
        if (!isLevelInHierarchyCalculated) {
            levelInHierarchy = 1;
            if (manager != null && managerId != 0) {
                levelInHierarchy += manager.getEmployeeLevelInHierarchy();
            }
            isLevelInHierarchyCalculated = true;
        }
        return levelInHierarchy;
    }

    public void updateEmployee(Integer employeeId, String lastName, String firstName, Double salary,
                               BigCompanyEmployee manager) {
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
        return "{" +
                "employeeId: " + employeeId +
                ", lastName: " + lastName +
                ", firstName: " + firstName +
                ", salary: " + salary +
                ", managerId: " + managerId +
                ", levelInHierarchy: " + getEmployeeLevelInHierarchy() +
                "}";
    }

    public String toShortString() {
        return "{" +
                "employeeId: " + employeeId +
                ", salary: " + salary +
                ", managerId: " + managerId +
                ", levelInHierarchy: " + getEmployeeLevelInHierarchy() +
                "}";
    }

}
