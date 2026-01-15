import main.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test Math Operations in Calculator class")
class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void beforeEachTestMethod(){
        calculator=new Calculator();
        System.out.println("Executing @BeforeEach methods");
    }

    @Test
    void testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo(){
        System.out.println("Running Test 4 / 2 = 2");
        //Arrange //Given
        int dividend=4;
        int divisor = 2;
        int expectedResult=2;
        //Act  //When
        int actualResult = calculator.integerDivision(
                dividend,
                divisor
        );
        //Assert //Then
        Assertions.assertEquals(expectedResult,actualResult,
                "4/2 should have returned 2");
    }
}