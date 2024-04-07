import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w2053389.Bank;
import org.w2053389.IncomeTaxCalculationManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class IncomeTaxCalculationManagerTest {

    private Bank mockBank;
    private IncomeTaxCalculationManager taxManager;

    @BeforeEach
    public void setUp() {
        mockBank = mock(Bank.class);
        taxManager = new IncomeTaxCalculationManager(mockBank);
    }

    @Test
    public void testCallsDeductIncomeTax() throws InterruptedException {
        Thread taxManagerThread = new Thread(taxManager);
        taxManagerThread.start();
        taxManagerThread.join();

        verify(mockBank, times(1)).deductIncomeTax();
    }

    @Test
    public void testHandlesExceptionGracefully() throws InterruptedException {
        doThrow(new RuntimeException("Simulated Exception")).when(mockBank).deductIncomeTax();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Thread taxManagerThread = new Thread(taxManager);
        taxManagerThread.start();
        taxManagerThread.join();

        assertTrue(outContent.toString().contains("Income tax deduction error: Simulated Exception"),
                "Expected exception message to be printed");

        System.setOut(System.out);
    }
}
