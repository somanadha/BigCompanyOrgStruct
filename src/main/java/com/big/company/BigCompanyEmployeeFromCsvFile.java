package com.big.company;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *  This is the class that represents the employee database for the BIG COMPANY.
 *  Data for the employees is loaded from the CSV file which assumed to be available in the project root directory
 *
 * @author Somanadha Satyadev Bulusu
 */
public class BigCompanyEmployeeFromCsvFile implements BigCompanyEmployeeProvider {
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
    @Override
    public int loadEmployeeData() {
        final String CSV_FILE_PATH = "data.csv";
        int lineReadCount = -1;

        try (Reader reader = new FileReader(CSV_FILE_PATH);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            BigCompanyEmployee employee;
            BigCompanyEmployee manager;
            for (CSVRecord csvRecord : csvParser) {
                // Ignore the 1st header row
                if (lineReadCount == -1) {
                    lineReadCount ++;
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
                        // Control enters into this block if employee was created earlier just with employeeId as part
                        // of assigning this employee as a manager to some other employee:
                        employee.updateEmployee(employeeId, lastName, firstName, salary, manager);
                        BigCompanyLogger.getLogger().log(Level.WARNING,"Employee updated for Id:{0}", employeeId);
                    } else {
                        // Control enters into this block if employee is being created first time with all details:
                        employee = new BigCompanyEmployee(employeeId, lastName, firstName, salary, manager);
                        BigCompanyLogger.getLogger().log(Level.INFO, "Employee created{0}", employee.toString());
                    }
                    manager.addSubordinate(employee);
                    bigCompanyEmployeeMap.put(employeeId, employee);
                    lineReadCount++;
                } catch (BigCompanyException | ArrayIndexOutOfBoundsException | NumberFormatException bce) {
                    BigCompanyLogger.getLogger().log(Level.WARNING, bce.getMessage()+": Line #"+lineReadCount);
                }
            }
        } catch (IOException ioe) {
            BigCompanyLogger.getLogger().log(Level.SEVERE, ioe.getMessage());
        }
        return lineReadCount;
    }

    /**
     * Return the collection of <b>BigCompanyEmployee</b> objects that are available
     *
     * @return Collection of BigCompanyEmployee objects
     */
    @Override
    public Collection<BigCompanyEmployee> getEmployees() {
        return bigCompanyEmployeeMap.values();
    }

    /**
     * Each BigCompanyEmployee object contains employeeId. But complete details for that object may or may not be
     * available when it is created.
     * </p>
     * This happens in the case when an employee object is being created with a managerId present, but there is no
     * manager object that exists with that mangerId (employeeId).
     * </p>
     * So, this method checks if a manager  object is available or not.
     * </p>
     * if an object is already available then this method would return that.
     * Else, this method would create one with just employeeId (managerId).
     * </p>
     * Later when the complete data is available, this same object would be updated with more details
     *
     * @param managerId Manager Id (EmployeeId) that needs to be checked for availability and if not to be created
     *
     * @return A complete employee object if available or an incomplete object with just employeeId would be returned.
     */
    private BigCompanyEmployee getManagerOrCreateIfDoesNotExists(Integer managerId) {
        BigCompanyEmployee manager;
        if  (bigCompanyEmployeeMap.containsKey(managerId)) {
            manager = bigCompanyEmployeeMap.get(managerId);
        } else {
            manager = new BigCompanyEmployee(managerId);
            bigCompanyEmployeeMap.put(managerId, manager);
            BigCompanyLogger.getLogger().log(Level.WARNING,"Out of order records. Manager created with Id:{0}",
                    managerId);
        }
        return manager;
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
     * @throws NumberFormatException Exception when input doesn't contain an Integer
     */
    private static Integer convertStringToInteger(String str) throws NumberFormatException{
        if (str == null || str.isEmpty()) {
            str = "0";
        }
        return Integer.valueOf(str);
    }

    /**
     * Converts String form of double to Double object
     * @param str Double number as a string
     *
     * @return String converted to Double
     *
     * @throws NumberFormatException Exception when input doesn't contain a Double
     */
    private static Double convertStringToDouble(String str) throws NumberFormatException{
        if (str == null || str.isEmpty()) {
            str = "0.0";
        }
        return Double.valueOf(str);
    }
}
