package com.big.company;

import lombok.Getter;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger class
 *
 * @author Somanadha Satyadev Bulusu
 */
public class BigCompanyLogger {
    @Getter
    private static final Logger logger = Logger.getLogger(BigCompanyLogger.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("bigcompany.log", false); // Overwrite mode
            fileHandler.setFormatter(new SimpleFormatter()); // Default format
            logger.addHandler(fileHandler);
            logger.setLevel(Level.WARNING);
            logger.setUseParentHandlers(false);// Don't write to console
        } catch (IOException e) {
            System.err.println("Failed to set up file logging");
        }
    }
}
