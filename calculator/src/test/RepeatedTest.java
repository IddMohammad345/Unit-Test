import main.Calculator;

public class RepeatedTest {

    Calculator calculator;

    void beforeEachTestMethod(){
        calculator=new Calculator();
        System.out.println("Executing @BeforeEach Method");
    }
}
