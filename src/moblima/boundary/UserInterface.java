package moblima.boundary;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * This class represents a general user interface which implements the common functionalities
 */
public abstract class UserInterface {
    /**
     * A Java Scanner shared by its subclasses
     */
    public Scanner sc = new Scanner(System.in);
    /**
     * An abstract method to print the menu
     */
    public abstract void print();
    /**
     * An abstract method to interact with the user
     */
    public abstract void interact();
    /**
     * A thin wrapper for System.out.println()
     * @param message The message to be displayed
     */
    public void displayLine(String message) {
        System.out.println(message);
    }
    /**
     * A thin wrapper for System.out.print()
     * @param message The message to be displayed
     */
    public void display(String message) {
        System.out.print(message);
    }
    /**
     * Scans an integer, has error checking against the input
     * @param message The message to be displayed when scanning for input
     * @return The scanned integer
     */
    public int scanInteger(String message) {
        int result;

        while (true) {
            System.out.print(message);
            try {
                result = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please try again");
            }
            sc.nextLine();
        }
        sc.nextLine();

        return result;
    }
    /**
     * Scans a double, has error checking against the input
     * @param message The message to be displayed when scanning for input
     * @return The scanned double
     */
    public double scanDouble(String message) {
        double result;

        while (true) {
            System.out.print(message);
            try {
                result = sc.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please try again");
            }
            sc.nextLine();
        }
        sc.nextLine();

        return result;
    }
    /**
     * Scans a string, has error checking against the input
     * @param message The message to be displayed when scanning for input
     * @return The scanned string
     */
    public String scanString(String message) {
        String result;

        System.out.print(message);
        result = sc.next();
        sc.nextLine();

        return result;
    }
    /**
     * Scans a line of string, has error checking against the input
     * @param message The message to be displayed when scanning for input
     * @return The scanned line of string
     */
    public String scanLine(String message) {
        String result;

        System.out.print(message);
        result = sc.nextLine();

        return result;
    }
    /**
     * Scans a boolean, has error checking against the input
     * @param message The message to be displayed when scanning for input
     * @return The scanned boolean
     */
    public boolean scanBoolean(String message) {
        boolean result;

        while (true) {
            System.out.print(message);
            try {
                result = sc.nextBoolean();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please try again");
            }
            sc.nextLine();
        }
        sc.nextLine();

        return result;
    }
}
