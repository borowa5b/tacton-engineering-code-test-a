package pl.borowa5b.controller;

import lombok.RequiredArgsConstructor;
import pl.borowa5b.model.CalculatorModel;
import pl.borowa5b.view.CalculatorView;

@RequiredArgsConstructor
public class CalculatorController {

    private static final String EXIT_COMMAND = "exit";
    private final CalculatorModel model;
    private final CalculatorView view;

    /**
     * Starts the calculator application.
     */
    public void start() {
        while (true) {
            final var expression = view.getExpression();
            if (expression.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            }

            try {
                final var result = model.calculate(expression);
                view.showResult(result);
            } catch (final ArithmeticException exception) {
                view.showArithmeticError(exception.getMessage());
            } catch (final NumberFormatException exception) {
                view.showFormatError(exception.getMessage());
            } catch (final Exception exception) {
                view.showUnexpectedError(exception.toString());
            }
        }
    }
}
