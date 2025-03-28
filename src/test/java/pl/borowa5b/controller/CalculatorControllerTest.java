package pl.borowa5b.controller;

import org.junit.jupiter.api.Test;
import pl.borowa5b.model.CalculatorModel;
import pl.borowa5b.view.CalculatorView;

import static org.mockito.Mockito.*;

class CalculatorControllerTest {

    @Test
    void shouldExitCalculatorWhenExitCommandEntered() throws Exception {
        // given
        final var mockModel = mock(CalculatorModel.class);
        final var mockView = mock(CalculatorView.class);
        final var controller = new CalculatorController(mockModel, mockView);

        // when
        when(mockView.getExpression()).thenReturn("exit");
        controller.start();

        // then
        verify(mockView).getExpression();
        verify(mockModel, never()).calculate(anyString());
        verify(mockView, never()).showResult(anyDouble());
    }

    @Test
    void shouldExitCalculatorWhenExitCommandEnteredCaseInsensitive() throws Exception {
        // given
        final var mockModel = mock(CalculatorModel.class);
        final var mockView = mock(CalculatorView.class);
        final var controller = new CalculatorController(mockModel, mockView);

        // when
        when(mockView.getExpression()).thenReturn("ExIt");
        controller.start();

        // then
        verify(mockView).getExpression();
        verify(mockModel, never()).calculate(anyString());
        verify(mockView, never()).showResult(anyDouble());
        verify(mockView, never()).showArithmeticError(anyString());
        verify(mockView, never()).showFormatError(anyString());
        verify(mockView, never()).showUnexpectedError(anyString());
    }

    @Test
    void shouldHandleAndDisplayArithmeticErrorCorrectly() throws Exception {
        // given
        final var mockModel = mock(CalculatorModel.class);
        final var mockView = mock(CalculatorView.class);
        final var controller = new CalculatorController(mockModel, mockView);
        final var errorMessage = "Division by zero";

        // when
        when(mockView.getExpression()).thenReturn("5/0", "exit");
        when(mockModel.calculate("5/0")).thenThrow(new ArithmeticException(errorMessage));
        controller.start();

        // then
        verify(mockView, times(2)).getExpression();
        verify(mockModel).calculate("5/0");
        verify(mockView).showArithmeticError(errorMessage);
        verify(mockView, never()).showResult(anyDouble());
        verify(mockView, never()).showFormatError(anyString());
        verify(mockView, never()).showUnexpectedError(anyString());
    }

    @Test
    void shouldHandleAndDisplayNumberFormatErrorCorrectly() throws Exception {
        // given
        final var mockModel = mock(CalculatorModel.class);
        final var mockView = mock(CalculatorView.class);
        final var controller = new CalculatorController(mockModel, mockView);
        final var errorMessage = "Invalid number format";

        // when
        when(mockView.getExpression()).thenReturn("1 + a", "exit");
        when(mockModel.calculate("1 + a")).thenThrow(new NumberFormatException(errorMessage));
        controller.start();

        // then
        verify(mockView, times(2)).getExpression();
        verify(mockModel).calculate("1 + a");
        verify(mockView).showFormatError(errorMessage);
        verify(mockView, never()).showResult(anyDouble());
        verify(mockView, never()).showArithmeticError(anyString());
        verify(mockView, never()).showUnexpectedError(anyString());
    }

    @Test
    void shouldHandleAndDisplayUnexpectedErrorCorrectly() throws Exception {
        // given
        final var mockModel = mock(CalculatorModel.class);
        final var mockView = mock(CalculatorView.class);
        final var controller = new CalculatorController(mockModel, mockView);
        final var errorMessage = "Unexpected error occurred";

        // when
        when(mockView.getExpression()).thenReturn("1 + 1", "exit");
        when(mockModel.calculate("1 + 1")).thenThrow(new RuntimeException(errorMessage));
        controller.start();

        // then
        verify(mockView, times(2)).getExpression();
        verify(mockModel).calculate("1 + 1");
        verify(mockView).showUnexpectedError(contains(errorMessage));
        verify(mockView, never()).showResult(anyDouble());
        verify(mockView, never()).showArithmeticError(anyString());
        verify(mockView, never()).showFormatError(anyString());
    }


    @Test
    void shouldContinueExecutionAfterDisplayingError() throws Exception {
        // given
        final var mockModel = mock(CalculatorModel.class);
        final var mockView = mock(CalculatorView.class);
        final var controller = new CalculatorController(mockModel, mockView);
        final var errorMessage = "Division by zero";

        // when
        when(mockView.getExpression()).thenReturn("5/0", "2+2", "exit");
        when(mockModel.calculate("5/0")).thenThrow(new ArithmeticException(errorMessage));
        when(mockModel.calculate("2+2")).thenReturn(4.0);
        controller.start();

        // then
        verify(mockView, times(3)).getExpression();
        verify(mockModel).calculate("5/0");
        verify(mockModel).calculate("2+2");
        verify(mockView).showArithmeticError(errorMessage);
        verify(mockView).showResult(4.0);
    }

    @Test
    void shouldHandleMultipleConsecutiveCalculationsWithoutExiting() throws Exception {
        // given
        final var mockModel = mock(CalculatorModel.class);
        final var mockView = mock(CalculatorView.class);
        final var controller = new CalculatorController(mockModel, mockView);

        // when
        when(mockView.getExpression()).thenReturn("2 + 2", "3 * 4", "10 - 5", "exit");
        when(mockModel.calculate("2 + 2")).thenReturn(4.0);
        when(mockModel.calculate("3 * 4")).thenReturn(12.0);
        when(mockModel.calculate("10 - 5")).thenReturn(5.0);
        controller.start();

        // then
        verify(mockView, times(4)).getExpression();
        verify(mockModel).calculate("2 + 2");
        verify(mockModel).calculate("3 * 4");
        verify(mockModel).calculate("10 - 5");
        verify(mockView).showResult(4.0);
        verify(mockView).showResult(12.0);
        verify(mockView).showResult(5.0);
        verify(mockView, never()).showArithmeticError(anyString());
        verify(mockView, never()).showFormatError(anyString());
        verify(mockView, never()).showUnexpectedError(anyString());
    }

    @Test
    void shouldNotThrowExceptionsForEdgeCaseInputs() throws Exception {
        // given
        final var mockModel = mock(CalculatorModel.class);
        final var mockView = mock(CalculatorView.class);
        final var controller = new CalculatorController(mockModel, mockView);
        final var largeNumber = "9999999999999999";
        final var complexExpression = "2 + 3 * (4 - 1) / 2 ^ 3";

        // when
        when(mockView.getExpression()).thenReturn(largeNumber, complexExpression, "exit");
        when(mockModel.calculate(largeNumber)).thenReturn(9999999999999999.0);
        when(mockModel.calculate(complexExpression)).thenReturn(3.375);
        controller.start();

        // then
        verify(mockView, times(3)).getExpression();
        verify(mockModel).calculate(largeNumber);
        verify(mockModel).calculate(complexExpression);
        verify(mockView).showResult(9999999999999999.0);
        verify(mockView).showResult(3.375);
        verify(mockView, never()).showArithmeticError(anyString());
        verify(mockView, never()).showFormatError(anyString());
        verify(mockView, never()).showUnexpectedError(anyString());
    }

}