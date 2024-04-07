package org.w2053389;

import java.util.List;
import java.util.ArrayList;

public class Bank {
    private final List<BankAccount> accounts = new ArrayList<>();

    public BankAccount createAccount(String accountNumber, List<Customer> customer, AccountType accountType, boolean overdraftFacilities, double overdraftLimit, double interestRate) {

        BankAccount account = new BankAccount(accountNumber, customer, overdraftLimit, accountType, overdraftFacilities, overdraftLimit, interestRate);
        accounts.add(account);
        return account;

    }

    public boolean removeAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                this.accounts.remove(account);
                return true;
            }
        }

        return false;
    }

    public BankAccount getAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void addInterest() {
        for (BankAccount account : accounts) {
            if (account.getBalance() > 0) {
                double interest = account.getBalance() * account.getInterestRate() / 100 / 12;
                account.addInterest(interest);
            }
        }
    }

    public void deductIncomeTax() {
        for (BankAccount account : accounts) {
            if (account.getBalance() > 0) {
                double interest = account.getBalance() * account.getInterestRate() / 100 / 12;
                double tax = interest * .36;
                account.deductIncomeTax(tax);
            }
        }

    }

    public void annualCharges() {
        // write the logic to apply annual charges only for Regular Account
        for (BankAccount account : accounts) {
            if (account.getAccountType() == AccountType.REGULAR) {
                double annualCharges = account.getBalance() /10000;
                account.deductAnnualCharges(annualCharges);
            } else {
                System.out.println("Annual charges applied");
            }
        }
    }

    public void overdraftCharges() {
        // write the logic if overdrafted charge for both account
        for (BankAccount account : accounts) {
            if (account.getBalance() < 0) {
                if(account.isOverdraftAvailable()) {
                    double overdraftCharge = account.getOverdraftLimit();
                    account.deductOverdraftCharge(overdraftCharge);
                }
            }
        }
    }
}

