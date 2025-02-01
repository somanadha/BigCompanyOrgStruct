package com.big.company;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public final class BigCompany {
    private static BigCompany bigCompany = null;
    private Map<Integer, BigCompanyEmployee> bigCompanyEmployeeMap = new HashMap();

    private BigCompany() {
    }

    public static synchronized BigCompany getInstance() {
        if (bigCompany == null) {
            bigCompany = new BigCompany();
            int employeeCount = bigCompany.loadEmployeeData();
            System.out.println(employeeCount);
        }
        return bigCompany;
    }

    private int loadEmployeeData() {

        final String CSV_FILE_PATH = "data.csv";
        int employeeCount = 0;

        try (Reader reader = new FileReader(CSV_FILE_PATH);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                if (employeeCount == 0) {
                    employeeCount ++;
                    continue;
                }

                // Accessing Values by Column Index
                Integer employeeId = convertStringToInteger(csvRecord.get(0));
                String lastName = csvRecord.get(1);
                String firstName = csvRecord.get(2);
                Double salary = convertDoubleToInteger(csvRecord.get(3));
                Integer managerId = convertStringToInteger(csvRecord.get(4));


                // Process the data
                System.out.println("employeeId: " + employeeId + ", lastName: " + lastName + ", firstName: " + firstName
                        + ", salary: " + salary + ", managerId: " + managerId);
                employeeCount ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeCount - 1;
    }

    private void checkForDuplicateEmployee()  {
        bigCompanyEmployeeMap.containsKey(employeeId)
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
