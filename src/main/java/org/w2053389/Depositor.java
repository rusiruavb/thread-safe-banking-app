package org.w2053389;

public class Depositor implements Runnable {
    private BankAccount account;
    private double amount;

    public Depositor(BankAccount account, double amount) {
        super();
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            account.deposit(amount);
        }catch(Exception e) {
            System.out.println("Error in deposit: "+e.getMessage());
        }
    }
}
