package com.big.company;

public abstract  class BigCompanyEmployee {
    protected Integer employeeId;
    protected String lastName;
    protected String firstName;
    protected Double salary;
    protected Integer managerId;

    protected abstract int getEmployeeLevelInHierarchy();
    protected abstract void updateEmployee(Integer employeeId, String lastName, String firstName,
                                          Double salary, BigCompanyEmployee manager);
    protected abstract void addSubordinate(BigCompanyEmployee employee);

    public String toString() {
        return "{" +
                "employeeId: " + employeeId +
                ", lastName: " + lastName +
                ", firstName: " + firstName +
                ", salary: " + salary +
                ", managerId: " + managerId +
                "}";
    }

    public String toShortString() {
        return "{" +
                "employeeId: " + employeeId +
                ", salary: " + salary +
                ", managerId: " + managerId +
                "}";
    }
}
