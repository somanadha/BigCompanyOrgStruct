package com.big.company;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Class that has all the business logic for providing all the details requested
 *
 * @author Somaandha Satyadev Bulusu
 */
public final class BigCompanyController {
    private static BigCompanyController bigCompanyController = null;
    private final BigCompanyEmployeeDataBase bigCompanyOrgData = new BigCompanyEmployeeDataBase();

    /**
     * Private Instance for Singleton
     */
    private BigCompanyController() {
    }

    /**
     * Returns Singleton Instance
     *
     * @return Singleton Instance
     */
    public static synchronized BigCompanyController getInstance() {
        if (bigCompanyController == null) {
            bigCompanyController = new BigCompanyController();
            int employeeCount = bigCompanyController.bigCompanyOrgData.loadEmployeeDataFromCSV();
            BigCompanyLogger.getLogger().warning("Total employees loaded from CSV file:"+employeeCount);
        }
        return bigCompanyController;
    }

    /**
     * Gets all the underpaid managers that are available in the database
     *
     * @param minimumPercentRequired This is the criteria to decide whether a manager is underpaid or not. This
     *                               parameter value is a percentage value, so it can assume values from 0 to 100.
     *
     * @return All employees who are managers that have their salary less than the  <b>minimumPercentRequired</b>
     * when compared with the average salary of all the subordinates along with the difference value
     */
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

    /**
     * Gets all the overpaid managers that are available in the database
     *
     * @param maximumPercentAllowed This is the criteria to decide whether a manager is overpaid or not. This
     *                               parameter value is a percentage value, so it can assume values from 0 to 100.
     *
     * @return All employees who are managers that have their salary more than the  <b>maximumPercentAllowed</b>
     * when compared with the average salary of all the subordinates along with the difference value
     */
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

    /**
     * Returns all employees who are too deep in the reporting hierarchy chain that are available in the database
     *
     * @param levelGapBetweenCeoAnEmployee The middle management count between CEO any other non-CEO employee
     *
     * @return All employees who have more than <b>levelGapBetweenCeoAnEmployee</b> managers between them and CEO
     */
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
