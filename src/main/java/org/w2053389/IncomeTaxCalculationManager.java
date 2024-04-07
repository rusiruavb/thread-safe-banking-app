package org.w2053389;

public class IncomeTaxCalculationManager implements Runnable {
    private final Bank bank;

    public IncomeTaxCalculationManager(Bank bank) {
        super();
        this.bank = bank;
    }

    @Override
    public void run() {
        try {
            bank.deductIncomeTax();
        } catch (Exception e) {
            System.out.println("Income tax deduction error: " + e.getMessage());
        }
    }
}
