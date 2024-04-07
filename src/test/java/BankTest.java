import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w2053389.Bank;
import org.w2053389.BankAccount;
import org.w2053389.Customer;
import org.w2053389.AccountType;
import java.util.Collections;

public class BankTest {

    private Bank bank;
    private Customer customer;

    @Before
    public void setUp() {
        bank = new Bank();
        customer = new Customer("C001", "Nimal", "Perera", "0771234567", "12 Peradeniya Road, Kandy", "931234567V");
    }

    @Test
    public void testCreateAccount() {
        BankAccount account = bank.createAccount("123456", Collections.singletonList(customer), AccountType.REGULAR, true, 1000.0, 0.05);
        assertNotNull(account);
        assertEquals("123456", account.getAccountNumber());
    }

    @Test
    public void testRemoveAccountSuccess() {
        bank.createAccount("123456", Collections.singletonList(customer), AccountType.REGULAR, true, 1000.0, 0.05);
        assertTrue(bank.removeAccount("123456"));
    }

    @Test
    public void testRemoveAccountFailure() {
        assertFalse(bank.removeAccount("nonexistent"));
    }

    @Test
    public void testGetAccountFound() {
        bank.createAccount("123456", Collections.singletonList(customer), AccountType.VIP, true, 1000.0, 0.05);
        assertNotNull(bank.getAccount("123456"));
    }

    @Test
    public void testGetAccountNotFound() {
        assertNull(bank.getAccount("nonexistent"));
    }

    @Test
    public void testAddInterestToPositiveBalance() {
        BankAccount account = bank.createAccount("123456", Collections.singletonList(customer), AccountType.REGULAR, true, 1000.0, 0.05);
        account.deposit(1000);
        bank.addInterest();
        assertTrue(account.getBalance() > 1000);
    }

    @Test
    public void testAddInterestToZeroBalance() {
        BankAccount account = bank.createAccount("123456", Collections.singletonList(customer), AccountType.REGULAR, true, 0.0, 0.05);
        bank.addInterest();
        assertEquals(0.0, account.getBalance(), 0.0);
    }

    @Test
    public void testAnnualChargesForRegularAccount() {
        BankAccount account = bank.createAccount("123456", Collections.singletonList(customer), AccountType.REGULAR, true, 1000.0, 0.05);
        account.deposit(10000);
        bank.annualCharges();
        assertTrue(account.getBalance() < 12000);
    }

    @Test
    public void testAnnualChargesForNonRegularAccount() {
        BankAccount account = bank.createAccount("123456", Collections.singletonList(customer), AccountType.VIP, true, 1000.0, 0.05);
        account.deposit(10000);
        double initialBalance = account.getBalance();
        bank.annualCharges();
        assertEquals(initialBalance, account.getBalance(), 0.0);
    }

    @Test
    public void testOverdraftChargesApplied() {
        BankAccount account = bank.createAccount("123456", Collections.singletonList(customer), AccountType.REGULAR, true, 1000.0, 0.05);
        account.withdraw(2000); // creates overdraft
        bank.overdraftCharges();
        assertTrue(account.getBalance() < -1000); // because overdraft charge should be deducted
    }

    @Test
    public void testNoOverdraftChargesWhenBalancePositive() {
        BankAccount account = bank.createAccount("123456", Collections.singletonList(customer), AccountType.REGULAR, true, 1000.0, 0.05);
        account.deposit(500);
        bank.overdraftCharges();
        assertEquals(1500, account.getBalance(), 0.0);
    }
}

