import org.junit.Test;
import org.junit.Before;
import org.w2053389.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BankAccountTest {

    private BankAccount account;
    private List<Customer> customers;

    @Before
    public void setUp() {
        customers = new ArrayList<>();
        customers.add(new Customer("C001", "Amila", "Thushara", "0771234567", "12 Peradeniya Road, Kandy", "931234567V"));
        account = new BankAccount("123456", customers, 1000.0, AccountType.REGULAR, true, 500.0, 0.05);
    }

    @Test
    public void testAddCustomer() {
        Customer newCustomer = new Customer("C002", "Bimali", "Perera", "0762345678", "34 Galle Road, Mount Lavinia", "922345678V");
        account.addCustomers(newCustomer);
        assertTrue(account.getCustomers().contains(newCustomer));
    }

    @Test
    public void testRemoveCustomerSuccess() {
        assertFalse(account.removeCustomer("C001"));
        assertFalse(account.getCustomers().contains(new Customer("C001", "Amila", "Thushara", "0771234567", "12 Peradeniya Road, Kandy", "931234567V")));
    }

    @Test
    public void testRemoveCustomerFailure() {
        assertTrue(account.removeCustomer("003")); // ID does not exist
    }

    @Test
    public void testWithdrawSuccess() {
        account.withdraw(500.0);
        assertEquals(500.0, account.getBalance(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawFailInsufficientFunds() {
        account.withdraw(1600.0); // More than balance + overdraft
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNegativeAmount() {
        account.withdraw(-100.0);
    }

    @Test
    public void testDeposit() {
        account.deposit(200.0);
        assertEquals(1200.0, account.getBalance(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositNegativeAmount() {
        account.deposit(-200.0);
    }

    @Test
    public void testAddInterest() {
        account.addInterest(100.0);
        assertEquals(1100.0, account.getBalance(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInterestNegativeAmount() {
        account.addInterest(-50.0);
    }

    @Test
    public void testDeductIncomeTax() {
        account.deductIncomeTax(100.0);
        assertEquals(900.0, account.getBalance(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeductIncomeTaxNegativeAmount() {
        account.deductIncomeTax(-100.0);
    }
}
