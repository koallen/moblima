package moblima.boundary;

import java.util.Scanner;
import java.util.InputMismatchException;

public abstract class UserInterface {
    public Scanner sc = new Scanner(System.in);

    public abstract void print();

    public abstract void interact();

    public void displayLine(String message) {
        System.out.println(message);
    }

    public void display(String message) {
        System.out.print(message);
    }

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

    public String scanString(String message) {
        String result;

        System.out.print(message);
        result = sc.next();
        sc.nextLine();

        return result;
    }

    public String scanLine(String message) {
        String result;

        System.out.print(message);
        result = sc.nextLine();

        return result;
    }

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
