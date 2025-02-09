package com.big.company;

import java.util.Objects;

import lombok.Getter;

import java.util.Vector;

/**
 * Class that represents an employee (Could be CEO or a Manager or an Individual Contributor) object
 *
 * @author Somanadha Satyadev Bulusu
 */
public final class BigCompanyEmployee {
    /**
     * Following are the details of an employee that are loaded from the backend (database) system
     */
    @Getter
    private Integer employeeId;
    @Getter
    private String lastName;
    @Getter
    private String firstName;
    @Getter
    private Double salary;
    @Getter
    private Integer managerId;

    /**
     * This field indicates whether an object of this class is created with all the above details from backend or not
     * There is scenario where an employee object is created first, but by that time manager object for that employee
     * may not present.
     * </p>
     * In that context a manager object is created with just the employeeId filed for that particular employee object .
     * That employeeId is nothing but the managerId for the particular employee object.
     * <p>
     * So following filed when set to true means that this object has all the employee fields. This field is set to
     * false when this object is created just with employeeId filed
     *
     * @see #updateEmployee(Integer, String, String, Double, BigCompanyEmployee)
     *
     */
    private boolean isCompletelyInitialized;

    /**
     *  Following fields are applicable only when the employee is a manager. When a subordinated is added to a manager's
     *  reporting chain, then this field isSubordinateAverageSalaryRecalculationNeeded is set to true. As a subordinate
     *  average salary calculation process is a looping process, once all the subordinates are added to manager's chain,
     *  this isSubordinateAverageSalaryRecalculationNeeded field is set to false and average is calculated and stored in
     *  subordinatesAverageSalary filed.
     *
     * @see #addSubordinate(BigCompanyEmployee)
     * @see #getSubordinatesAverageSalary()
     * </p>
     *  From that point onwards, no looping process happens, just subordinatesAverageSalary filed would be returned
     */
    private boolean isSubordinateAverageSalaryRecalculationNeeded;
    private double subordinatesAverageSalary;


    /**
     *   For an employee finding the reporting hierarchy level starting with CEO requires traversal up the chain.
     *   However once that traversal is done there is no need to do it repeatedly to get the reporting hierarchy level.
     *   </p>
     *   So, the isLevelInHierarchyCalculated field which is initially set to false would enable the calculation of
     *   reporting hierarchy level of the employee. Once it is calculated and stored in the  levelInHierarchy filed,
     *   that point on, no traversal up the chain happens to get the employees level in the hierarchy
     * @see #getEmployeeLevelInHierarchy()
     */
    private boolean isLevelInHierarchyCalculated;
    private int levelInHierarchy;

    /**
     * Manager object for this employee. Could be null for CEO
     */
    private BigCompanyEmployee manager;

    /**
     * Collection of subordinates reporting to this employee. Non-null values for this field indicates that this
     * employee is a manager
     *
     * @see #addSubordinate(BigCompanyEmployee)
     */
    private Vector<BigCompanyEmployee> subordinateEmployees = null;

    /**
     * Creates an object of this class with just employeeId. This constructor is called in the case where, an employee
     * object is created but the manager (who is also an employee) object of that employee is not created yet. Inorder
     * to supply a manager object to that employee following constructor is used to create the manager object from the'
     * employee object's managerId field.
     *
     * @param employeeId Employee Id of the manager object whose data other than employeeId is not available yet
     */
    public BigCompanyEmployee(Integer employeeId) {
        this.employeeId = employeeId;
        this.salary = 0.0; // This is required as manager for CEO employeeId is 0 and Salary is O
        isCompletelyInitialized = false;
    }

    /**
     * Creates a full-fledged employee object with all fields
     *
     * @param employeeId EmployeeId
     * @param lastName Last Name of the employee
     * @param firstName First Name of the employee
     * @param salary Salary of the employee
     * @param manager Manager object for the employee
     */
    public BigCompanyEmployee(Integer employeeId, String lastName, String firstName, Double salary,
                              BigCompanyEmployee manager) throws BigCompanyException {
        updateEmployee(employeeId, lastName, firstName, salary, manager);
    }

    /**
     * Checks whether this employee object is a manager or not based on the availability of subordinates
     *
     * @see #isSubordinateAverageSalaryRecalculationNeeded
     * @see #subordinatesAverageSalary
     *
     * @return true : if this employee is a manager; false : if not
     */
    public boolean isManager() {
        return subordinateEmployees != null;
    }

    /**
     * In case of a manager object, returns the average salary of all the subordinates
     *
     * @return Average salary of all the subordinates of this manager object
     */
    public Double getSubordinatesAverageSalary() {
        if (isSubordinateAverageSalaryRecalculationNeeded && subordinateEmployees != null) {
            subordinatesAverageSalary = subordinateEmployees.stream()
                    .mapToDouble(subordinate -> subordinate.salary)
                    .average().getAsDouble();
        }
        return subordinatesAverageSalary;
    }

    /**
     * CEO is at level 1. All other employees either directly reports to CEO or indirectly reports in hierarchical way.
     * This method return the level of the employee in the reporting chain of CEO.
     *
     * @return Reporting Level in the hierarchical chain of employees.
     */
    public int getEmployeeLevelInHierarchy() {
        if (!isLevelInHierarchyCalculated) {
            levelInHierarchy = 1;
            if (manager != null && managerId != 0) {
                levelInHierarchy += manager.getEmployeeLevelInHierarchy();
            }
            isLevelInHierarchyCalculated = true;
        }
        return levelInHierarchy;
    }

    /**
     * This method updates BigCompanyEmployee object field values either for the first time or  when the object is
     * created earlier just with employeeId. In other cases calling method would indicate a duplicate record.
     *
     * @param employeeId EmployeeId
     * @param lastName Last Name of the employee
     * @param firstName First Name of the employee
     * @param salary Salary of the employee
     * @param manager Manager object for the employee
     */
    public void updateEmployee(Integer employeeId, String lastName, String firstName, Double salary,
                               BigCompanyEmployee manager) throws BigCompanyException{
 
        ValidateEmployeeForInfiniteManagerLoop(employeeId, manager);
        if (!isCompletelyInitialized) {
            this.employeeId = employeeId;
            this.lastName = lastName;
            this.firstName = firstName;
            this.salary = salary;
            this.manager = manager;
            if (manager != null) {
                this.managerId = manager.employeeId;
            }
            isCompletelyInitialized = true;
            
        } else {
            throw new BigCompanyException("Duplicate Data. Employee already exists with id "+ employeeId);
        }
    }

    private void ValidateEmployeeForInfiniteManagerLoop(Integer employeeId, 
        BigCompanyEmployee manager) throws BigCompanyException {

        while(manager != null && !Objects.equals(manager.employeeId, employeeId)){
            manager = manager.manager;
        }
        
        if (manager != null && Objects.equals(manager.employeeId, employeeId)) {
            if (Objects.equals(employeeId, manager.employeeId)) {
                throw new BigCompanyException("Employee with id "+ employeeId + 
                    " has a manager infinite loop. Please fix that");
            }
        }
    }

    /**
     * Adding a subordinate to a BigCompanyEmployee object makes that object a manager object. This method would add
     * a BigCompanyEmployee subordinate object to an BigCompanyEmployee object making that object a manager object
     *
     * @param employee Subordinate BigCompanyEmployee object to be added
     */
    public void addSubordinate(BigCompanyEmployee employee) throws BigCompanyException{
        if (employee != null) {
            if (subordinateEmployees == null) {
                subordinateEmployees = new Vector<>();
            }
            subordinateEmployees.add(employee);
            isSubordinateAverageSalaryRecalculationNeeded = true;
        }
    }

    /**
     * To string method that returns all the details about the BigCompanyEmployee object except its subordinate details
     *
     * @return String representation of BigCompanyEmployee object
     */
    @Override
    public String toString() {
        return "{" +
                "employeeId: " + employeeId +
                ", lastName: " + lastName +
                ", firstName: " + firstName +
                ", salary: " + salary +
                ", managerId: " + managerId +
                ", levelInHierarchy: " + getEmployeeLevelInHierarchy() +
                "}";
    }

    /**
     * Returns only few important details of the BigCompanyEmployee object as a String
     * @return Few important details as a String
     */
    public String toShortString() {
        return "{" +
                "employeeId: " + employeeId +
                ", salary: " + salary +
                ", managerId: " + managerId +
                ", levelInHierarchy: " + getEmployeeLevelInHierarchy() +
                "}";
    }
}
