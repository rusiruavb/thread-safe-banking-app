import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w2053389.AccountType;
import org.w2053389.BankAccount;
import org.w2053389.Customer;
import org.w2053389.Receiver;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;

public class ReceiverTest {

    private BankAccount account;
    private Customer customer;
    private double initialBalance;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() {
        initialBalance = 1000.0;
        customer = new Customer("123", "John", "Doe", "0771234567", "123 Elm Street", "980123456V");
        account = new BankAccount("acc123", Collections.singletonList(customer), initialBalance, AccountType.REGULAR, true, 200, 0.15);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void whenCustomerIsAssociated_withdrawSucceeds() throws InterruptedException {
        Receiver receiver = new Receiver(customer, account, 200.0);
        Thread receiverThread = new Thread(receiver);
        receiverThread.start();
        receiverThread.join();

        assertEquals(initialBalance - 200.0, account.getBalance(),
                "The balance should be decremented by the amount withdrawn.");
    }

    @Test
    public void whenCustomerIsNotAssociated_printsErrorMessage() throws InterruptedException {
        Customer nonAssociatedCustomer = new Customer("456", "Jane", "Smith", "0777654321", "321 Oak Street", "981234567V");
        Receiver receiver = new Receiver(nonAssociatedCustomer, account, 200.0);
        Thread receiverThread = new Thread(receiver);
        receiverThread.start();
        receiverThread.join();

        String output = outContent.toString();
        assertTrue(output.contains("Error: Customer Jane Smith is not associated with the account acc123"),
                "An error message should be printed if the customer is not associated with the account.");

        assertEquals(initialBalance, account.getBalance(),
                "The balance should remain unchanged if the withdrawal is not allowed.");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }
}
