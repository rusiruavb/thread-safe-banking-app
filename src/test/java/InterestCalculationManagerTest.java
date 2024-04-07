import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w2053389.Bank;
import org.w2053389.InterestCalculationManager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InterestCalculationManagerTest {

    private Bank mockBank;
    private InterestCalculationManager interestManager;

    @BeforeEach
    public void setUp() {
        mockBank = mock(Bank.class);
        interestManager = new InterestCalculationManager(mockBank);
    }

    @Test
    public void testCallsAddInterest() throws InterruptedException {
        Thread interestManagerThread = new Thread(interestManager);
        interestManagerThread.start();
        interestManagerThread.join();

        verify(mockBank, times(1)).addInterest();
    }

    @Test
    public void testHandlesExceptionGracefully() throws InterruptedException {
        doThrow(new RuntimeException("Simulated Exception")).when(mockBank).addInterest();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Thread interestManagerThread = new Thread(interestManager);
        interestManagerThread.start();
        interestManagerThread.join();

        assertTrue(outContent.toString().contains("Interest calculation error: Simulated Exception"),
                "Expected exception message to be printed");

        System.setOut(System.out);
    }
}
