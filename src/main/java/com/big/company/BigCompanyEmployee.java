package com.big.company;

import lombok.Getter;

import java.util.Vector;

@Getter
public class BigCompanyEmployee {

    private final BigCompanyOrgData bigCompanyOrgData;
    private final Integer employeeId;
    private final String lastName;
    private final String firstName;
    private final Double salary;
    private final Integer managerId;
    private final Vector<Integer> subordinateIds = new Vector<>();

    public BigCompanyEmployee (BigCompanyOrgData bigCompanyOrgData, Integer employeeId,
                               String lastName, String firstName,  Double salary, Integer managerId) {
        this.bigCompanyOrgData = bigCompanyOrgData;
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName= firstName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public boolean isManager() {
        return !subordinateIds.isEmpty();
    }

    public int getLevelInHierarchy () {
        int levelInHierarchy = 1;
        BigCompanyEmployee manager = bigCompanyOrgData.getEmployeeObject(managerId);
        if (manager != null) {
            levelInHierarchy += manager.getLevelInHierarchy();
        }
        return levelInHierarchy;
    }
}
