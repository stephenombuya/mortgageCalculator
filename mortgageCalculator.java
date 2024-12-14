// Main package structure
package com.mortgagecalculator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;

/**
 * Advanced Mortgage Calculator with comprehensive loan analysis features.
 */
public class MortgageCalculator {
    // Constants
    private static final byte MONTHS_IN_YEAR = 12;
    private static final byte PERCENT = 100;
    private static final int MIN_PRINCIPAL = 1000;
    private static final int MAX_PRINCIPAL = 1_000_000;
    private static final float MIN_ANNUAL_RATE = 1;
    private static final float MAX_ANNUAL_RATE = 30;
    private static final byte MIN_PERIOD = 1;
    private static final byte MAX_PERIOD = 30;

    // Loan parameters
    private int principal;
    private float annualInterestRate;
    private int loanTermYears;

    /**
     * Constructor for Mortgage Calculator
     * @param principal Loan principal amount
     * @param annualInterestRate Annual interest rate
     * @param loanTermYears Loan term in years
     */
    public MortgageCalculator(int principal, float annualInterestRate, int loanTermYears) {
        this.principal = principal;
        this.annualInterestRate = annualInterestRate;
        this.loanTermYears = loanTermYears;
    }

    /**
     * Calculate monthly mortgage payment
     * @return Monthly mortgage payment amount
     */
    public double calculateMortgage() {
        float monthlyInterest = annualInterestRate / PERCENT / MONTHS_IN_YEAR;
        int numOfPayments = loanTermYears * MONTHS_IN_YEAR;

        return principal 
               * (monthlyInterest * Math.pow(1 + monthlyInterest, numOfPayments))
               / (Math.pow(1 + monthlyInterest, numOfPayments) - 1);
    }

    /**
     * Generate detailed amortization schedule
     * @return List of monthly payment details
     */
    public List<AmortizationEntry> generateAmortizationSchedule() {
        List<AmortizationEntry> schedule = new ArrayList<>();
        double monthlyPayment = calculateMortgage();
        float monthlyInterest = annualInterestRate / PERCENT / MONTHS_IN_YEAR;
        int numOfPayments = loanTermYears * MONTHS_IN_YEAR;
        
        double remainingBalance = principal;
        
        for (int month = 1; month <= numOfPayments; month++) {
            double interestPayment = remainingBalance * monthlyInterest;
            double principalPayment = monthlyPayment - interestPayment;
            remainingBalance -= principalPayment;

            schedule.add(new AmortizationEntry(
                month, 
                monthlyPayment, 
                principalPayment, 
                interestPayment, 
                Math.max(0, remainingBalance)
            ));
        }
        
        return schedule;
    }

    /**
     * Compare multiple loan scenarios
     * @param loanScenarios List of loan scenarios to compare
     * @return Comparison results
     */
    public static String compareLoanScenarios(List<MortgageCalculator> loanScenarios) {
        StringBuilder comparison = new StringBuilder("Loan Scenario Comparison:\n");
        
        for (int i = 0; i < loanScenarios.size(); i++) {
            MortgageCalculator calc = loanScenarios.get(i);
            comparison.append(String.format(
                "Scenario %d: Principal: $%d, Rate: %.2f%%, Term: %d years\n" +
                "   Monthly Payment: %s\n" +
                "   Total Interest Paid: %s\n\n", 
                i + 1, 
                calc.principal, 
                calc.annualInterestRate,
                calc.loanTermYears,
                formatCurrency(calc.calculateMortgage()),
                formatCurrency(calc.calculateTotalInterest())
            ));
        }
        
        return comparison.toString();
    }

    /**
     * Calculate total interest paid over loan term
     * @return Total interest paid
     */
    public double calculateTotalInterest() {
        double monthlyPayment = calculateMortgage();
        int totalPayments = loanTermYears * MONTHS_IN_YEAR;
        return (monthlyPayment * totalPayments) - principal;
    }

    /**
     * Format currency using default locale
     * @param amount Amount to format
     * @return Formatted currency string
     */
    private static String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance().format(amount);
    }

    /**
     * Interactive CLI for mortgage calculations
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Principal input
            int principal = getValidInput(
                scanner, 
                "Principal", 
                MIN_PRINCIPAL, 
                MAX_PRINCIPAL
            );

            // Interest rate input
            float annualInterest = getValidFloatInput(
                scanner, 
                "Annual Interest Rate", 
                MIN_ANNUAL_RATE, 
                MAX_ANNUAL_RATE
            );

            // Loan term input
            int period = getValidInput(
                scanner, 
                "Period (Years)", 
                MIN_PERIOD, 
                MAX_PERIOD
            );

            // Create mortgage calculator
            MortgageCalculator calculator = new MortgageCalculator(
                principal, annualInterest, period
            );

            // Display results
            System.out.println("\n--- Mortgage Calculation Results ---");
            System.out.println("Monthly Payment: " + 
                formatCurrency(calculator.calculateMortgage()));
            System.out.println("Total Interest Paid: " + 
                formatCurrency(calculator.calculateTotalInterest()));

            // Optional: Generate amortization schedule
            System.out.print("\nGenerate Amortization Schedule? (y/n): ");
            if (scanner.next().toLowerCase().startsWith("y")) {
                List<AmortizationEntry> schedule = calculator.generateAmortizationSchedule();
                System.out.println("\nFirst 5 Months Amortization Schedule:");
                for (int i = 0; i < Math.min(5, schedule.size()); i++) {
                    System.out.println(schedule.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    /**
     * Validate and get integer input within specified range
     */
    private static int getValidInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                int value = scanner.nextInt();
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Enter a value between %d and %d\n", min, max);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Validate and get float input within specified range
     */
    private static float getValidFloatInput(Scanner scanner, String prompt, float min, float max) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                float value = scanner.nextFloat();
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Enter a value between %.2f and %.2f\n", min, max);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}

/**
 * Represents a single entry in the amortization schedule
 */
class AmortizationEntry {
    private int monthNumber;
    private double monthlyPayment;
    private double principalPayment;
    private double interestPayment;
    private double remainingBalance;

    public AmortizationEntry(int monthNumber, double monthlyPayment, 
                              double principalPayment, double interestPayment, 
                              double remainingBalance) {
        this.monthNumber = monthNumber;
        this.monthlyPayment = monthlyPayment;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.remainingBalance = remainingBalance;
    }

    @Override
    public String toString() {
        return String.format(
            "Month %d: Payment: $%.2f, Principal: $%.2f, Interest: $%.2f, Remaining: $%.2f", 
            monthNumber, monthlyPayment, principalPayment, 
            interestPayment, remainingBalance
        );
    }
}
