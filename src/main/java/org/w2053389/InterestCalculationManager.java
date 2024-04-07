package org.w2053389;

public class InterestCalculationManager implements Runnable {
    private final Bank bank;

    public InterestCalculationManager(Bank bank) {
        super();
        this.bank = bank;
    }

    @Override
    public void run() {
        try {
            bank.addInterest();
        } catch (Exception e) {
            System.out.println("Interest calculation error: " + e.getMessage());
        }
    }
}

