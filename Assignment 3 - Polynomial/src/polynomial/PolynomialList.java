package polynomial;

import java.util.*;
import java.io.*;

public class PolynomialList {

    private static ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        args = new String[]{"4", "0", "5", "7", "0", "3"};

        int choice = -1;

        do {
            menu();
            System.out.print("Pick your choice : ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    createNoTerms();
                    break;
                case 2:
                    createReadFile();
                    break;
                case 3:
                    createReadCommandLine(args);
                    break;
                case 4:
                    printAllIncreasing();
                    break;
                case 5:
                    printAllDecreasing();
                    break;
                case 6:
                    addPolynomials();
                    break;
                case 7:
                    substractPolynomials();
                    break;
                case 8:
                    multiplyPolynomial();
                    break;
                case 9:
                    evaluatePolynomial();
                    break;
                case 0:
                    System.out.println("Bye bye!!");
                    break;
                default:
                    System.out.println("Wrong choice, try again");
            }

            System.out.println();
        } while (choice != 0);
    }

    public static void menu() {
        System.out.println("1. Create no terms polynomial");
        System.out.println("2. Create a polynomial by reading from file");
        System.out.println("3. Create a polynomial by reading from command line");
        System.out.println("4. Print all polynomials in Increasing Order");
        System.out.println("5. Print all polynomials in Decreasing Order");
        System.out.println("6. Add 2 Polynomials");
        System.out.println("7. Substract 2 Polynomials");
        System.out.println("8. Multiply a Polynomial with a scalar value");
        System.out.println("9. Evalute a polynomial");
        System.out.println("0. EXIT");
    }

    public static void createNoTerms() {
        Polynomial poly = new Polynomial(0);
        polynomials.add(poly);
    }

    public static void createReadFile() {
        System.out.print("Enter file to read from : ");
        String fileName = input.next();

        try {
            Scanner file = new Scanner(new File(fileName));
            String[] values = new String[100];
            int index = 0;
            while (file.hasNext()) {
                values[index] = file.next();
                index++;
            }

            int[] array = new int[index];
            for (int i = 0; i < index; i++) {
                array[i] = Integer.parseInt(values[i]);
            }

            Polynomial poly = new Polynomial(array);
            polynomials.add(poly);
        } catch (FileNotFoundException e) {
            System.out.println("Oops. Error opening file. Please try again.");
        }
    }

    public static void createReadCommandLine(String[] args) {
        if (args.length == 0) {
            System.out.println("No valid argument having a polynomial specified.");
            return;
        }
        int[] array = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            array[i] = Integer.parseInt(args[i]);
        }

        Polynomial poly = new Polynomial(array);
        polynomials.add(poly);

    }

    public static void printAllIncreasing() {
        for (int i = 0; i < polynomials.size(); i++) {
            System.out.print("Polynomial " + i + " : ");
            polynomials.get(i).outputIncreasingOrder();
        }
    }

    public static void printAllDecreasing() {
        for (int i = 0; i < polynomials.size(); i++) {
            System.out.print("Polynomial " + i + " : ");
            polynomials.get(i).outputDecreasingOrder();
        }
    }

    public static void addPolynomials() {
        printAllDecreasing();

        System.out.print("Select a polynomial: ");
        int polyNum1 = input.nextInt();
        while (polyNum1 < 0 || polyNum1 >= polynomials.size()) {
            System.out.print("Kindly select a valid polynomial number: ");
            polyNum1 = input.nextInt();
        }

        System.out.print("Select another polynomial: ");
        int polyNum2 = input.nextInt();
        while (polyNum2 < 0 || polyNum2 >= polynomials.size()) {
            System.out.print("Kindly select a valid polynomial number: ");
            polyNum2 = input.nextInt();
        }

        Polynomial res = Polynomial.sum(polynomials.get(polyNum1), polynomials.get(polyNum2));
        System.out.print("Added polynomial on the ArrayList : ");
        res.outputDecreasingOrder();
        polynomials.add(res);
    }

    public static void substractPolynomials() {
        printAllDecreasing();

        System.out.print("Select a polynomial: ");
        int polyNum1 = input.nextInt();
        while (polyNum1 < 0 || polyNum1 >= polynomials.size()) {
            System.out.print("Kindly select a valid polynomial number: ");
            polyNum1 = input.nextInt();
        }

        System.out.print("Select another polynomial: ");
        int polyNum2 = input.nextInt();
        while (polyNum2 < 0 || polyNum2 >= polynomials.size()) {
            System.out.print("Kindly select a valid polynomial number: ");
            polyNum2 = input.nextInt();
        }

        Polynomial res = Polynomial.difference(polynomials.get(polyNum1), polynomials.get(polyNum2));
        System.out.print("Added polynomial on the ArrayList : ");
        res.outputDecreasingOrder();
        polynomials.add(res);
    }

    public static void multiplyPolynomial() {
        printAllDecreasing();

        System.out.print("Select a polynomial: ");
        int polyNum = input.nextInt();
        while (polyNum < 0 || polyNum >= polynomials.size()) {
            System.out.print("Kindly select a valid polynomial number: ");
            polyNum = input.nextInt();
        }

        System.out.print("Enter a scalar value to multiply with: ");
        int val = input.nextInt();

        Polynomial res = Polynomial.multiply(polynomials.get(polyNum), val);
        System.out.print("Added polynomial on the ArrayList : ");
        res.outputDecreasingOrder();
        polynomials.add(res);
    }

    public static void evaluatePolynomial() {
        printAllDecreasing();

        System.out.print("Select a polynomial: ");
        int polyNum = input.nextInt();
        while (polyNum < 0 || polyNum >= polynomials.size()) {
            System.out.print("Kindly select a valid polynomial number: ");
            polyNum = input.nextInt();
        }

        System.out.print("Enter the value of x: ");
        int x = input.nextInt();

        double res = polynomials.get(polyNum).evaluate(x);
        System.out.println("Result = " + res);
    }
}
