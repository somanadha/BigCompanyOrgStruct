package com.big.company;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public class BigCompanyOrgStruct {

    public static void main(String[] args) {
        System.out.println("Hello World");
        BigCompanyController controller = BigCompanyController.getInstance();
        System.out.println("Under Paid Employees");
        System.out.println();
        for (Map.Entry<BigCompanyEmployee, Double> entrySet : controller.getUnderpaidManagers(20).entrySet()) {
            System.out.println(entrySet.getKey() + " - "+ entrySet.getValue());
        }
    }
}
