package com.big.company;

import java.util.Map;

/**
 * This is the main class that acts as a client and get reports from BigCompanyController object and prints them
 *
 * @author Somanadha Satyadev Bulusu
 */
public class BigCompany {

    public static void main(String[] args) {
        final BigCompanyEmployeeProvider provider = new BigCompanyEmployeeFromCsvFile();
        BigCompanyController controller = BigCompanyController.getInstance(provider);

        System.out.println("Underpaid Employees");
        System.out.println("===================");
        for (Map.Entry<BigCompanyEmployee, Double> entrySet : controller.getUnderpaidManagers(20).entrySet()) {
            System.out.println(entrySet.getKey().toShortString() + " Underpaid by: "+ entrySet.getValue());
        }

        System.out.println();
        System.out.println("Overpaid Employees");
        System.out.println("===================");
        for (Map.Entry<BigCompanyEmployee, Double> entrySet : controller.getOverpaidManagers(50).entrySet()) {
            System.out.println(entrySet.getKey().toShortString() + " Overpaid by: "+ entrySet.getValue());
        }

        System.out.println();
        System.out.println("Too Deep In Hierarchy Employees");
        System.out.println("===============================");
        for (Map.Entry<BigCompanyEmployee, Integer> entrySet : controller.getTooDeepInHierarchyEmployees(3).entrySet()) {
            System.out.println(entrySet.getKey().toShortString() + " Reduce hierarchy by: "+ entrySet.getValue());
        }
        System.out.println("Testing for Jenkins With NGROK. Ninth Time");
    }
}
