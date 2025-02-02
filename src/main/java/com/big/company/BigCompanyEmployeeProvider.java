package com.big.company;

import java.util.Collection;

/**
 * Interface for loading and providing BigCompanyEmployee data objects from a specific source
 */
public interface BigCompanyEmployeeProvider {
    /**
     * Loads employees data from a specific source
     * </p>
     *
     * @return Employee count that is loaded
     */
    int loadEmployeeData();

    /**
     * Return the collection of <b>BigCompanyEmployee</b> objects that are available
     *
     * @return Collection of BigCompanyEmployee objects
     */
    Collection<BigCompanyEmployee> getEmployees();
}
