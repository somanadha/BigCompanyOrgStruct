package com.big.company;

public final class BigCompanyController {
    private static BigCompanyController bigCompanyController = null;
    private BigCompanyOrgData bigCompanyOrgData = new BigCompanyOrgData();

    private BigCompanyController() {
    }

    public static synchronized BigCompanyController getInstance() {
        if (bigCompanyController == null) {
            bigCompanyController = new BigCompanyController();
            bigCompanyController.bigCompanyOrgData.loadEmployeeData();
        }
        return bigCompanyController;
    }
  }
