package pl.borowa5b.view;

import java.util.Scanner;

public class CalculatorConsoleView implements CalculatorView {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to enter an expression.
     */
    public String getExpression() {
        System.out.print("Enter expression (or type 'exit' to quit): ");
        return scanner.nextLine();
    }

    /**
     * Displays the result of the calculation.
     *
     * @param result the computed result
     */
    public void showResult(final double result) {
        System.out.println("Result: " + result);
    }

    /**
     * Displays an arithmetic error message.
     *
     * @param errorMessage the error message to display
     */
    public void showArithmeticError(final String errorMessage) {
        System.out.println("Arithmetic error occurred: " + errorMessage);
    }

    /**
     * Displays an input format error message.
     *
     * @param errorMessage the error message to display
     */
    public void showFormatError(final String errorMessage) {
        System.out.println("Expression format error occurred: " + errorMessage);
    }

    /**
     * Displays an unexpected error message.
     *
     * @param errorMessage the error message to display
     */
    public void showUnexpectedError(final String errorMessage) {
        System.out.println("Unexpected error occurred: " + errorMessage);
    }
}
