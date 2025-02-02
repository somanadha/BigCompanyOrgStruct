package com.big.company;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BigCompanyEmployeeFromMockObject implements BigCompanyEmployeeProvider{
    private final Map<Integer, BigCompanyEmployee> bigCompanyEmployeeMap = new HashMap<>();

    @Override
    public int loadEmployeeData() {
        try {
            BigCompanyEmployee employee0 = new BigCompanyEmployee(0, "", "", 0.00, null);
            bigCompanyEmployeeMap.put(0, employee0);

            BigCompanyEmployee employee1 = new BigCompanyEmployee(1, "Smith", "John", 495000.00, employee0);
            bigCompanyEmployeeMap.put(1, employee1);

            BigCompanyEmployee employee2 = new BigCompanyEmployee(2, "Johnson", "Mary", 480000.00, employee1);
            bigCompanyEmployeeMap.put(2, employee2);

            BigCompanyEmployee employee3 = new BigCompanyEmployee(3, "Williams", "James", 470000.00, employee1);
            bigCompanyEmployeeMap.put(3, employee3);

            BigCompanyEmployee employee4 = new BigCompanyEmployee(4, "Brown", "Patricia", 460000.00, employee1);
            bigCompanyEmployeeMap.put(4, employee4);

            BigCompanyEmployee employee5 = new BigCompanyEmployee(5, "Jones", "Robert", 450000.00, employee2);
            bigCompanyEmployeeMap.put(5, employee5);

            BigCompanyEmployee employee6 = new BigCompanyEmployee(6, "Garcia", "Linda", 440000.00, employee2);
            bigCompanyEmployeeMap.put(6, employee6);

            BigCompanyEmployee employee7 = new BigCompanyEmployee(7, "Miller", "Michael", 430000.00, employee2);
            bigCompanyEmployeeMap.put(7, employee7);

            BigCompanyEmployee employee8 = new BigCompanyEmployee(8, "Davis", "Barbara", 420000.00, employee3);
            bigCompanyEmployeeMap.put(8, employee8);

            BigCompanyEmployee employee9 = new BigCompanyEmployee(9, "Rodriguez", "Richard", 410000.00, employee3);
            bigCompanyEmployeeMap.put(9, employee9);

            BigCompanyEmployee employee10 = new BigCompanyEmployee(10, "Martinez", "Elizabeth", 400000.00, employee3);
            bigCompanyEmployeeMap.put(10, employee10);

            BigCompanyEmployee employee11 = new BigCompanyEmployee(11, "Hernandez", "Thomas", 390000.00, employee4);
            bigCompanyEmployeeMap.put(11, employee11);

            BigCompanyEmployee employee12 = new BigCompanyEmployee(12, "Lopez", "Christopher", 380000.00, employee4);
            bigCompanyEmployeeMap.put(12, employee12);

            BigCompanyEmployee employee13 = new BigCompanyEmployee(13, "Gonzalez", "Daniel", 370000.00, employee4);
            bigCompanyEmployeeMap.put(13, employee13);

            BigCompanyEmployee employee14 = new BigCompanyEmployee(14, "Wilson", "Paul", 360000.00, employee5);
            bigCompanyEmployeeMap.put(14, employee14);

            BigCompanyEmployee employee15 = new BigCompanyEmployee(15, "Anderson", "Mark", 350000.00, employee5);
            bigCompanyEmployeeMap.put(15, employee15);

            BigCompanyEmployee employee16 = new BigCompanyEmployee(16, "Thomas", "Donald", 340000.00, employee5);
            bigCompanyEmployeeMap.put(16, employee16);

            BigCompanyEmployee employee17 = new BigCompanyEmployee(17, "Taylor", "Steven", 330000.00, employee5);
            bigCompanyEmployeeMap.put(17, employee17);

            BigCompanyEmployee employee18 = new BigCompanyEmployee(18, "Moore", "George", 320000.00, employee6);
            bigCompanyEmployeeMap.put(18, employee18);

            BigCompanyEmployee employee19 = new BigCompanyEmployee(19, "Jackson", "Kenneth", 310000.00, employee6);
            bigCompanyEmployeeMap.put(19, employee19);

            BigCompanyEmployee employee20 = new BigCompanyEmployee(20, "Martin", "Joshua", 300000.00, employee6);
            bigCompanyEmployeeMap.put(20, employee20);
        } catch (BigCompanyException bce) {
            System.err.println(bce.getMessage());
        }

        return bigCompanyEmployeeMap.size();
    }

    @Override
    public Collection<BigCompanyEmployee> getEmployees() {
        return bigCompanyEmployeeMap.values();
    }
}
