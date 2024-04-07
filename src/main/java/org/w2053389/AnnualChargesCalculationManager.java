package org.w2053389;

public class AnnualChargesCalculationManager implements Runnable {
    private Bank bank;

    public AnnualChargesCalculationManager(Bank bank) {
        super();
        this.bank = bank;
    }

    @Override
    public void run() {
        try {
            bank.annualCharges();
        } catch (Exception e) {
            System.out.println("Annual charges calculation error: " + e.getMessage());
        }
    }
}

