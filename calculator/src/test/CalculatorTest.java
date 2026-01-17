import main.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Test Math Operations in Calculator class")
class CalculatorTest {

    Calculator calculator;

    @BeforeAll
    static void setup() {
        System.out.println("Executing @BeforeAll methods");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Executing @AfterAll method");
    }

    @BeforeEach
    void beforeEachTestMethod() {
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach methods");
    }

    @AfterEach
    void afterEachTestMethod() {
        System.out.println("Executing @BeforeEach method");
    }

    @DisplayName("Test 4/2 = 2")
    @Test
    void testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo() {
        System.out.println("Running Test 4 / 2 = 2");
        //Arrange //Given
        int dividend = 4;
        int divisor = 2;
        int expectedResult = 2;
        //Act  //When
        int actualResult = calculator.integerDivision(
                dividend,
                divisor
        );
        //Assert //Then
        Assertions.assertEquals(expectedResult, actualResult,
                "4/2 should have returned 2");
    }

    @DisplayName("Divided By Zero")
    @Test
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrewArithmeticException() {
        System.out.println("Running Division By zero");
        //Arrange
        int dividend = 4;
        int divisor = 0;
        String expectedExceptionMessage = "/ by zero";
        //Act
        ArithmeticException arithmeticException = Assertions.assertThrows(
                ArithmeticException.class, () -> {
                    calculator.integerDivision(dividend, divisor);
                }, "Division by zero should have thrown an arithmetic exception");

        Assertions.assertEquals(expectedExceptionMessage, arithmeticException.getMessage(),
                "UnExpected Exception message");
    }

    @DisplayName("Test integerSubtraction")
    @ParameterizedTest
    @MethodSource("integerSubtraction")
    void integerSubtraction(int minuend, int subtrahend, int expectedResult) {
        System.out.println("Running Test " + minuend + " - " + subtrahend + " = " + expectedResult);
        int actualResult = calculator.integerSubtraction(minuend, subtrahend);
        Assertions.assertEquals(expectedResult, actualResult,
                () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }

    private static Stream<Arguments> integerSubtraction() {
        return Stream.of(
                Arguments.of(33, 1, 32),
                Arguments.of(54, 1, 53),
                Arguments.of(24, 1, 23)
        );
    }

    @DisplayName("Test integerSubtraction1")
    @ParameterizedTest
    @CsvSource(
            {"33,1,32",
                    "54,1,53",
                    "24,1,23"}
    )
    void integerSubtraction1(int minuend, int subtrahend, int expectedResult) {
        System.out.println("Running Test " + minuend + " - " + subtrahend + " = " + expectedResult);
        int actualResult = calculator.integerSubtraction(minuend, subtrahend);
        Assertions.assertEquals(expectedResult, actualResult,
                () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }

    @DisplayName("Test integerSubtraction2")
    @ParameterizedTest
    @CsvFileSource(resources = "resources/integerSubtraction.csv")
    void integerSubtraction2(int minuend, int subtrahend, int expectedResult) {
        System.out.println("Running Test " + minuend + " - " + subtrahend + " = " + expectedResult);
        int actualResult = calculator.integerSubtraction(minuend, subtrahend);
        Assertions.assertEquals(expectedResult, actualResult,
                () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }

    @DisplayName("squareRoot() should throw IllegalArgumentException for negative inputs")
    @Test
    void squareRoot_WhenNumberIsLessThenZero_ShouldThrowIllegalArgumentException() {
        //Arrange
        double number = -1.0;
        //Act
        IllegalArgumentException actualException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    calculator.squareRoot(number);
                }, "Expected IllegalArgumentException for negative input: " + number
        );
        //Assert
        //Prefer checking content rather than exact string to avoid brittle tests
        Assertions.assertTrue(
                actualException.getMessage().toLowerCase().contains("negative"),
                "Unexpected exception message: " + actualException.getMessage()
        );
    }

    @DisplayName("squareRoot(0) should return 0")
    @Test
    void squareRoot_WhenNumberIsZero_ShouldReturnZero() {
        //Arrange
        double number = 25.0;
        double expectedResult = 5.0;
        //Act
        double actualResult = calculator.squareRoot(number);
        //Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}