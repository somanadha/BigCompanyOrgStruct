package com.big.company;

import java.util.Map;

public class BigCompany {

    public static void main(String[] args) {
        BigCompanyController controller = BigCompanyController.getInstance();
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
    }
}
