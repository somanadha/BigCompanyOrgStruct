package com.big.company;

import java.util.*;
import java.util.stream.Collectors;

public final class BigCompanyController {
    private static BigCompanyController bigCompanyController = null;
    private final BigCompanyEmployeeDataBase bigCompanyOrgData = new BigCompanyEmployeeDataBase();

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

        return bigCompanyOrgData.getEmployees().parallelStream()
                .filter(employee -> employee.isManager() &&
                        employee.getSalary() < ((100 + minimumPercentRequired) * employee.getSubordinatesAverageSalary())/100)
                .collect(Collectors.toMap(
                        employee -> employee,
                        employee -> ((((100 + minimumPercentRequired) * employee.getSubordinatesAverageSalary())/100) - employee.getSalary())))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(BigCompanyEmployee::getEmployeeId))) // Sorting by Employee ID
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function (not needed but required by toMap)
                        LinkedHashMap::new // Preserve sorted order
                ));
    }

    public Map<BigCompanyEmployee, Double> getOverpaidManagers(double maximumPercentAllowed) {
        return bigCompanyOrgData.getEmployees().parallelStream()
                .filter(employee -> employee.isManager() &&
                        employee.getSalary() > ((100 + maximumPercentAllowed) * employee.getSubordinatesAverageSalary())/100)
                .collect(Collectors.toMap(employee -> employee,
                        employee -> ((((100 + maximumPercentAllowed) * employee.getSubordinatesAverageSalary())/100) - employee.getSalary())))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(BigCompanyEmployee::getEmployeeId))) // Sorting by Employee ID
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function (not needed but required by toMap)
                        LinkedHashMap::new // Preserve sorted order
                ));
    }

    public Map<BigCompanyEmployee, Integer> getTooDeepInHierarchyEmployees(int levelGapBetweenCeoAnEmployee) {
        return bigCompanyOrgData.getEmployees().parallelStream()
                .filter(employee -> (employee.getEmployeeLevelInHierarchy() - levelGapBetweenCeoAnEmployee - 2  > 0 )) // Exclude CEO and the Employee from Employee level
                .collect(Collectors.toMap(employee -> employee,
                        employee -> (employee.getEmployeeLevelInHierarchy() - levelGapBetweenCeoAnEmployee - 2))) // Exclude CEO and the Employee from Employee level
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(BigCompanyEmployee::getEmployeeId))) // Sorting by Employee ID
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function (not needed but required by toMap)
                        LinkedHashMap::new // Preserve sorted order
                ));
    }
  }
