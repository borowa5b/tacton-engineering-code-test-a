package pl.borowa5b.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorModel {

    /**
     * Calculates the value of the given arithmetic expression.
     * Expression must be space separated e.g. "2 + 2 - 2 * 2 / 2".
     * Supports negative numbers (e.g. "3 * -2 + 1") and respects
     * operator precedence (multiplication and division are done first).
     *
     * @param expression the arithmetic expression
     * @return the computed result
     * @throws Exception if calculation is not possible
     */
    public double calculate(final String expression) throws Exception {
        final var tokenList = getTokens(expression);
        if (tokenList.isEmpty()) {
            throw new IllegalArgumentException("Expression should not be empty");
        }
        handleMultiplicationAndDivision(tokenList);
        return makeAdditionsAndSubtractions(tokenList);
    }

    /**
     * Handles the multiplications and divisions operations before adding and subtracting
     *
     * @param tokenList the list of tokens
     */
    private void handleMultiplicationAndDivision(final List<String> tokenList) throws Exception {
        for (var tokenIndex = 0; tokenIndex < tokenList.size(); tokenIndex++) {
            final var token = tokenList.get(tokenIndex);
            if (token.equals("*") || token.equals("/")) {
                double result = makeMultiplicationOrDivision(tokenList, tokenIndex, token);
                // Replace used tokens (left, right operand and operation) with result
                tokenList.set(tokenIndex - 1, String.valueOf(result));
                tokenList.remove(tokenIndex);
                tokenList.remove(tokenIndex);
                // Adjust index after removal (e.g. current operator index = 1,
                // after replacing tokens with result, next operator index will be also 1,
                // so we need to decrease index here for next loop to detect next operator if existing
                // otherwise it would ignore the next operator and go on to the next + 1 operator)
                tokenIndex--;
            }
        }
    }

    /**
     * Makes multiplication or division on given tokens
     *
     * @param tokenList     the list of tokens
     * @param operatorIndex the operator index
     * @param operator      the operator
     * @return the result of multiplication or division
     */
    private static double makeMultiplicationOrDivision(final List<String> tokenList, final int operatorIndex, final String operator) throws Exception {
        final var leftOperand = Double.parseDouble(tokenList.get(operatorIndex - 1));
        final var rightOperand = Double.parseDouble(tokenList.get(operatorIndex + 1));
        double result;
        if (operator.equals("*")) {
            result = leftOperand * rightOperand;
        } else if (rightOperand == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        } else {
            result = leftOperand / rightOperand;
        }
        return result;
    }

    /**
     * Makes additions and subtractions after multiplications and divisions
     *
     * @param tokenList the list of tokens
     * @return the result of adding and subtracting operands
     */
    private double makeAdditionsAndSubtractions(final List<String> tokenList) {
        double result = Double.parseDouble(tokenList.getFirst());
        for (var tokenIndex = 1; tokenIndex < tokenList.size(); tokenIndex += 2) {
            final var operator = tokenList.get(tokenIndex);
            final var operand = Double.parseDouble(tokenList.get(tokenIndex + 1));
            if (operator.equals("+")) {
                result += operand;
            } else if (operator.equals("-")) {
                result -= operand;
            }
        }
        return result;
    }

    /**
     * Gets tokens extracted from expression
     *
     * @param expression the given expression
     * @return the token list extracted from expression
     */
    private List<String> getTokens(final String expression) {
        return new ArrayList<>(Arrays.stream(expression.split("\\s+")).toList());
    }
}
