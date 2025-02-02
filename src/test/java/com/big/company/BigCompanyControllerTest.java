package com.big.company;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BigCompanyControllerTest {

    static BigCompanyController controller = BigCompanyController.getInstance(new BigCompanyEmployeeFromMockObject());

    @Test
    void getUnderpaidManagers() {
        Map<BigCompanyEmployee, Double> underpaidManagers =  controller.getUnderpaidManagers(20);
        for ( Map.Entry<BigCompanyEmployee, Double> entrySet : underpaidManagers.entrySet()) {
            System.out.println(entrySet.getKey()+" : "+ entrySet.getValue());
        }
    }

    @Test
    void getOverpaidManagers() {
    }

    @Test
    void getTooDeepInHierarchyEmployees() {
    }
}