package com.big.company;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *  This is the class that represents the employee database for the BIG COMPANY.
 *  Data for the employees is loaded from the CSV file which assumed to be available in the project root directory
 *
 * @author Somanadha Satyadev Bulusu
 */
public class BigCompanyEmployeeDataBase {
    private final Map<Integer, BigCompanyEmployee> bigCompanyEmployeeMap = new HashMap<>();

    /**
     * Loads employees data from a CSV file located in the root of the project directory
     * For each row read from the CSV file, a BigCompanyEmployee class object is created
     * </p>
     * First row the CSV file is supposed to be the header row : "employeeId,lastName,firstName,salary,managerId"
     * </p> Rest of the rows in the CSV file represents comma separated employees data as per the above format
     *
     * @return Employee count that is loaded
     */
    public int loadEmployeeDataFromCSV() {
        final String CSV_FILE_PATH = "data.csv";
        int employeeCount = -1;

        try (Reader reader = new FileReader(CSV_FILE_PATH);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            BigCompanyEmployee employee;
            BigCompanyEmployee manager;
            for (CSVRecord csvRecord : csvParser) {
                // Ignore the 1st header row
                if (employeeCount == -1) {
                    employeeCount ++;
                    continue;
                }

                try {
                    // Accessing Employee Data Values by Column Index
                    Integer employeeId = convertStringToInteger(csvRecord.get(0));
                    String lastName = csvRecord.get(1);
                    String firstName = csvRecord.get(2);
                    Double salary = convertStringToDouble(csvRecord.get(3));
                    Integer managerId = convertStringToInteger(csvRecord.get(4));

                    manager = getManagerOrCreateIfDoesNotExists(managerId);
                    employee = getEmployeeIfAlreadyExists(employeeId);
                    if (employee != null) {
                        // This happens in the case of this (manager) object is created earlier, just with employeeId
                        employee.updateEmployee(employeeId, lastName, firstName, salary, manager);
                        BigCompanyLogger.getLogger().warning("Manager object updated for Id:"+employeeId);
                    } else {
                        employee = new BigCompanyEmployee(employeeId, lastName, firstName, salary, manager);
                        BigCompanyLogger.getLogger().info("Employee created"+employee.toString());
                    }
                    manager.addSubordinate(employee);
                    bigCompanyEmployeeMap.put(employeeId, employee);
                    employeeCount++;
                } catch (BigCompanyException | NumberFormatException bce) {
                    BigCompanyLogger.getLogger().warning(bce.getMessage());
                }
            }
        } catch (IOException ioe) {
            BigCompanyLogger.getLogger().severe(ioe.getMessage());
        }
        return employeeCount;
    }

    /**
     * Return the collection of <b>BigCompanyEmployee</b> objects that are available
     *
     * @return Collection of BigCompanyEmployee objects
     */
    public Collection<BigCompanyEmployee> getEmployees() {
        return bigCompanyEmployeeMap.values();
    }

    /**
     * Each BigCompanyEmployee object contains employeeId.
     * But complete details for that object may or may not be available when it is created
     * </p>
     * So, this method checks if an employee object is available or not.
     * </p>
     * if an object is already available then this method would return that.
     * Else, this method would create one with just employeeId data.
     * </p>
     * Later when the actual employee data is available, this same object would be updated with more details
     *
     * @param employeeId EmployeeId that needs to be checked for availability and if not to be created
     *
     * @return A complete employee object if available or an incomplete object with just employeeId would be returned.
     */
    private BigCompanyEmployee getManagerOrCreateIfDoesNotExists(Integer employeeId) {
        BigCompanyEmployee employee = null;
        if  (bigCompanyEmployeeMap.containsKey(employeeId)) {
            employee = bigCompanyEmployeeMap.get(employeeId);
        } else {
            employee = new BigCompanyEmployee(employeeId);
            bigCompanyEmployeeMap.put(employeeId, employee);
            BigCompanyLogger.getLogger().warning("Out of order records. Manager created with Id:" + employeeId);
        }
        return employee;
    }

    /**
     * this method checks if an employee object is available or not.
     * if an object is already available then this method would return that.
     * Else, if a that object is not available then this method would return null
     *
     * @param employeeId EmployeeId that needs to be checked for availability
     *
     * @return BigCompanyEmployee object if available. If not null value
     */
    private BigCompanyEmployee getEmployeeIfAlreadyExists(Integer employeeId)  {
        BigCompanyEmployee employee = null;
        if  (bigCompanyEmployeeMap.containsKey(employeeId)) {
            employee = bigCompanyEmployeeMap.get(employeeId);
        }
        return employee;
    }

    /**
     * Converts String form of integer to Integer object
     * @param str Integer number as a string
     *
     * @return String converted to integer
     *
     * @throws NumberFormatException
     */
    private static Integer convertStringToInteger(String str) throws NumberFormatException{
        if (str == null || str.isEmpty()) {
            str = "0";
        }
        return Integer.parseInt(str);
    }

    /**
     * Converts String form of double to Double object
     * @param str Double number as a string
     *
     * @return String converted to Double
     *
     * @throws NumberFormatException
     */
    private static Double convertStringToDouble(String str) throws NumberFormatException{
        if (str == null || str.isEmpty()) {
            str = "0.0";
        }
        return Double.parseDouble(str);
    }
}
