package com.big.company;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BigCompanyControllerTest {

    static BigCompanyController controller = BigCompanyController.getInstance(new BigCompanyEmployeeFromMockObject());

    @Test
    void getUnderpaidManagers() {
        Map<BigCompanyEmployee, Double> underpaidManagers =  controller.getUnderpaidManagers(20);

        assertEquals(6, underpaidManagers.size());
        assertEquals(0, underpaidManagers.keySet().stream().findFirst().get().getEmployeeId());
        assertEquals(594000, underpaidManagers.values().stream().findFirst().get());
    }

    @Test
    void getOverpaidManagers() {
        Map<BigCompanyEmployee, Double> overpaidManagers =  controller.getOverpaidManagers(50);
        assertEquals(1, overpaidManagers.size());
        assertEquals(3, overpaidManagers.keySet().stream().findFirst().get().getEmployeeId());
        assertEquals(35000, overpaidManagers.values().stream().findFirst().get());
    }

    @Test
    void getTooDeepInHierarchyEmployees() {
        Map<BigCompanyEmployee, Integer> tooDeepInHierarchyEmployees =  controller.getTooDeepInHierarchyEmployees(4);
        assertEquals(1, tooDeepInHierarchyEmployees.size());
        assertEquals(20, tooDeepInHierarchyEmployees.keySet().stream().findFirst().get().getEmployeeId());
    }
}