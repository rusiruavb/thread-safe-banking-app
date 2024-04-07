import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w2053389.*;

import java.util.ArrayList;
import java.util.List;

public class AnnualChargesCalculationManagerTest {
    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
    }

    @Test
    public void testAnnualChargesCalculationWithConcurrency() throws InterruptedException {
        int numThreads = 10;

        for (int i = 0; i < numThreads; i++) {
            BankAccount account = new BankAccount("AccId" + i, new ArrayList<>(), 1000 + i * 100, AccountType.REGULAR, true, 200, 0.15);
            bank.createAccount(account.getAccountNumber(), account.getCustomers(), account.getAccountType(), account.isOverdraftAvailable(), account.getOverdraftLimit(), account.getInterestRate());
        }

        AnnualChargesCalculationManager[] managers = new AnnualChargesCalculationManager[numThreads];
        for (int i = 0; i < numThreads; i++) {
            managers[i] = new AnnualChargesCalculationManager(bank);
        }

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(managers[i]);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        for (int i = 0; i < numThreads; i++) {
            BankAccount account = bank.getAccount("AccId" + i);
            assertNotNull(account);
            double expectedBalance = account.getBalance() - (account.getBalance() / 10000);
            assertEquals(expectedBalance, account.getBalance(), 0.03);
        }
    }
}
