import main.Calculator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyRepeatedTest {

    Calculator calculator;

    @BeforeEach
    void beforeEachTestMethod() {
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach Method");
    }

    @DisplayName("Divided By Zero")
    @RepeatedTest(3)
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrewArithmeticException(
            RepetitionInfo repetitionInfo
    ) {
        System.out.println("Running Division by zero");
        System.out.println("Repetition #"+repetitionInfo.getCurrentRepetition()+" of "+
                repetitionInfo.getTotalRepetitions());
        //Arrange
        int dividend=4;
        int divisor=0;
        String expectedExceptionMessage="/ by zero";
        //Act
        ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> {
            calculator.integerDivision(dividend, divisor);
        }, "Division by zero should have thrown an arithmetic exception.");
        //Assert
        assertEquals(expectedExceptionMessage,actualException.getMessage(),
                "Unexpected Exception Message");
    }
}
