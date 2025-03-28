package pl.borowa5b;

import pl.borowa5b.controller.CalculatorController;
import pl.borowa5b.model.CalculatorModel;
import pl.borowa5b.view.CalculatorConsoleView;

public class CalculatorApplication {

    /**
     * Prepares required objects and starts application
     *
     * @param args application arguments - not used
     */
    public static void main(String[] args) {
        final var model = new CalculatorModel();
        final var view = new CalculatorConsoleView();
        final var controller = new CalculatorController(model, view);
        controller.start();
    }
}