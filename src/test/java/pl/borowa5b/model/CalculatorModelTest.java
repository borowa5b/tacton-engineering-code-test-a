package pl.borowa5b.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CalculatorModelTest {

    @ParameterizedTest
    @CsvSource({
            "2 + 3, 5.0",
            "5 - 2, 3.0",
            "3 * 4, 12.0",
            "10 / 2, 5",
            "3 * -2 + 1, -5.0",
            "2 + 3 * 4 - 6 / 2, 11",
            "3.5 * 2.5 - 1.2 / 0.3, 4.75"
    })
    void shouldCorrectlyCalculateExpressions(final String expression, final double expectedResult) throws Exception {
        // given
        final var calculator = new CalculatorModel();

        // when
        final var result = calculator.calculate(expression);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenDividingByZero() {
        // given
        final var calculator = new CalculatorModel();

        // when
        final var result = catchThrowable(() -> calculator.calculate("5 / 0"));

        // then
        assertThat(result)
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Division by zero is not allowed.");
    }


    @Test
    void shouldThrowExceptionForInvalidInput() {
        // given
        final var calculatorModel = new CalculatorModel();
        final var invalidExpression = "2 + abc - 3";

        // when
        final var result = catchThrowable(() -> calculatorModel.calculate(invalidExpression));

        // then
        assertThat(result)
                .isInstanceOf(NumberFormatException.class)
                .hasMessageContaining("For input string: \"abc\"");
    }
}