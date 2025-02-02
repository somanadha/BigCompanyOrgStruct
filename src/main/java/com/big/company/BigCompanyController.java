package com.big.company;

import java.util.*;
import java.util.stream.Collectors;

public final class BigCompanyController {
    private static BigCompanyController bigCompanyController = null;
    private final BigCompanyOrgData bigCompanyOrgData = new BigCompanyOrgData();

    private BigCompanyController() {
    }

    public static synchronized BigCompanyController getInstance() {
        if (bigCompanyController == null) {
            bigCompanyController = new BigCompanyController();
            bigCompanyController.bigCompanyOrgData.loadEmployeeData();
        }
        return bigCompanyController;
    }

    public Map<BigCompanyEmployee, Double> getUnderpaidManagers(double minimumPercentRequired) {

        Collection<BigCompanyEmployeeImpl> employees = bigCompanyOrgData.getEmployees();
        return employees.parallelStream()
                .filter(employee -> employee.isManager() &&
                        employee.salary < minimumPercentRequired * employee.getSubordinatesAverageSalary())
                .collect(Collectors.toMap(
                        employee -> employee,
                        employee -> (employee.getSubordinatesAverageSalary() - employee.salary)));
    }

    public Map<BigCompanyEmployee, Double> getOverpaidManagers(double maximumPercentAllowed) {
        return bigCompanyOrgData.getEmployees().parallelStream()
                .filter(employee -> employee.isManager() &&
                        employee.salary > maximumPercentAllowed * employee.getSubordinatesAverageSalary())
                .collect(Collectors.toMap(employee -> employee,
                        employee -> (employee.getSubordinatesAverageSalary() - employee.salary)));
    }

//    public Map<BigCompanyEmployee, Double> getTooDeepInHierarchyEmployees(int reasonableDepth) {
//    }
  }
