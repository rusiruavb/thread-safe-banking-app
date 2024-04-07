import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w2053389.AccountType;
import org.w2053389.BankAccount;
import org.w2053389.Depositor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class DepositorTest {

    private BankAccount account;
    private double initialBalance;

    @BeforeEach
    public void setUp() {
        initialBalance = 1000.0;
        account = new BankAccount("123", new ArrayList<>(), initialBalance, AccountType.REGULAR, true, 200, 0.15);
    }

    @Test
    public void whenDepositIsSuccessful_balanceIncreases() {
        double depositAmount = 500.0;
        Depositor depositor = new Depositor(account, depositAmount);

        Thread depositThread = new Thread(depositor);
        depositThread.start();

        // Wait for the deposit operation to complete
        try {
            depositThread.join();
        } catch (InterruptedException e) {
            fail("Deposit thread was interrupted.");
        }

        assertEquals(initialBalance + depositAmount, account.getBalance(),
                "The balance after deposit should be equal to the initial balance plus the deposit amount.");
    }

    @Test
    public void whenDepositAmountIsNegative_printsErrorMessage() {
        double depositAmount = -500.0;
        Depositor depositor = new Depositor(account, depositAmount);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Thread depositThread = new Thread(depositor);
        depositThread.start();

        try {
            depositThread.join();
        } catch (InterruptedException e) {
            fail("Deposit thread was interrupted.");
        }

        String output = outContent.toString();
        assertTrue(output.contains("Error in deposit"),
                "An error message should be printed if the deposit amount is negative.");

        System.setOut(System.out);
    }
}
