package com.davdjdickinson.jdnd.hotelreservation;

import com.davidjdickinson.jdnd.hotelreservation.model.Customer;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import java.io.PrintWriter;


/**
 * Driver class to test api.
 *
 * Only the minimal testing was implemented in this class as defined by the assignment from Udacity
 * Please see HotelReservationsTests (JUnit) for the full suite of tests for this api.
 *
 * The JUnit tests are run if this Driver is executed.  Also see JUnitDriver.
 * Thanks to Baeldung.com for the article "Running JUnit Tests Programmatically, from a Java Application"
 * https://www.baeldung.com/junit-tests-run-programmatically-from-java
 */
public class Driver {

    public static void main(String [] args) {
        testCustomerToString();
        testCustomerEmailValidation();
        runJUnitTests();

    }

    private static void testCustomerToString(){
        Customer customer = new Customer("first", "second", "j@domain.com");
        System.out.println(customer);
    }

    private static void testCustomerEmailValidation() {
        try {
            Customer customer = new Customer("first", "second", "email");
            System.out.println("Email validation failed!");
        } catch (IllegalArgumentException ex){
                System.out.println("Email validation successful!");
            }
        }

    /**
     * Credit: Baeldung.com
     * https://www.baeldung.com/junit-tests-run-programmatically-from-java
     */
    private static void runJUnitTests() {
        JUnitDriver driver = new JUnitDriver();
        driver.runTests();

        TestExecutionSummary summary = driver.listener.getSummary();
        summary.printTo(new PrintWriter(System.out));
    }

}
