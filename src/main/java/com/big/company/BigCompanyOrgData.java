package com.big.company;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BigCompanyOrgData {
    private final Map<Integer, BigCompanyEmployeeImpl> bigCompanyEmployeeMap = new HashMap<>();

    public int loadEmployeeData() {
        final String CSV_FILE_PATH = "data.csv";
        int employeeCount = -1;

        try (Reader reader = new FileReader(CSV_FILE_PATH);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            BigCompanyEmployeeImpl employee;
            BigCompanyEmployeeImpl manager;
            for (CSVRecord csvRecord : csvParser) {
                // Ignore the 1st header row
                if (employeeCount == -1) {
                    employeeCount ++;
                    continue;
                }

                // Accessing Employee Data Values by Column Index
                Integer employeeId = convertStringToInteger(csvRecord.get(0));
                String lastName = csvRecord.get(1);
                String firstName = csvRecord.get(2);
                Double salary = convertDoubleToInteger(csvRecord.get(3));
                Integer managerId = convertStringToInteger(csvRecord.get(4));

                manager = getManagerOrCreateIfDoesNotExists(managerId);
                employee = getEmployeeIfAlreadyExists(employeeId);

                if (employee != null) {
                    employee.updateEmployee(employeeId,lastName, firstName, salary, manager);
                } else {
                    employee = new BigCompanyEmployeeImpl(employeeId,lastName, firstName, salary, manager);
                }
                manager.addSubordinate(employee);
                bigCompanyEmployeeMap.put(employeeId, employee);

                employeeCount ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeCount;
    }

    public Collection<BigCompanyEmployeeImpl> getEmployees() {
        return bigCompanyEmployeeMap.values();
    }

    private BigCompanyEmployeeImpl getManagerOrCreateIfDoesNotExists(Integer managerId) {
        BigCompanyEmployeeImpl manager = null;
        if  (bigCompanyEmployeeMap.containsKey(managerId)) {
            manager = bigCompanyEmployeeMap.get(managerId);
        } else {
           manager = new BigCompanyEmployeeImpl(managerId);
        }
        return manager;
    }

    private BigCompanyEmployeeImpl getEmployeeIfAlreadyExists(Integer employeeId)  {
        BigCompanyEmployeeImpl employee = null;
        if  (bigCompanyEmployeeMap.containsKey(employeeId)) {
            employee = bigCompanyEmployeeMap.get(employeeId);
        }
        return employee;
    }

    private static Integer convertStringToInteger(String str) throws NumberFormatException{
        if (str == null || str.isEmpty()) {
            str = "0";
        }
        return Integer.parseInt(str);
    }

    private static Double convertDoubleToInteger(String str) throws NumberFormatException{
        if (str == null || str.isEmpty()) {
            str = "0.0";
        }
        return Double.parseDouble(str);
    }
}
