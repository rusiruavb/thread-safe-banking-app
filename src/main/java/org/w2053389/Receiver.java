package org.w2053389;

public class Receiver implements Runnable {

    private final Customer customer;
    private final BankAccount account;
    private final double amount;



    public Receiver(Customer customer, BankAccount account, double amount) {
        super();
        this.customer = customer;
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        // Write the logic to verify if customer is part of the account
        // if it is true
        boolean isCustomerPartOfAccount = account.getCustomers().contains(customer);
        if (isCustomerPartOfAccount) {
            try {
                account.withdraw(amount);
            } catch (Exception e) {
                System.out.println("Withdrawal Error: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Customer "
                    + customer.getFirstName()
                    + " "
                    + customer.getLastName()
                    + " is not associated with the account "
                    + account.getAccountNumber()
            );
        }
    }

}

