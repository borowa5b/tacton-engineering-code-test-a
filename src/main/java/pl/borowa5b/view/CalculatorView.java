package pl.borowa5b.view;

public interface CalculatorView {

    String getExpression();

    void showResult(final double result);

    void showArithmeticError(final String errorMessage);

    void showFormatError(final String message);

    void showUnexpectedError(final String errorMessage);
}
