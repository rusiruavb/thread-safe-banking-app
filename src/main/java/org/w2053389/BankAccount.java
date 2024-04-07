package org.w2053389;

import java.util.List;

public class BankAccount {
    private final String accountNumber;
    private final List<Customer> customers;
    private double balance; // BigDecimal is recommended to use
    private AccountType accountType;
    private boolean overdraftAvailable;
    private double overdraftLimit;
    private double interestRate;


    public BankAccount(String accountNumber, List<Customer> customers, double balance, AccountType accountType,
                       boolean overdraftAvailable, double overdraftLimit, double interestRate) {
        super();
        this.accountNumber = accountNumber;
        this.customers = customers;
        this.balance = balance;
        this.accountType = accountType;
        this.overdraftAvailable = overdraftAvailable;
        this.overdraftLimit = overdraftLimit;
        this.interestRate = interestRate;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomers(Customer customer) {
        this.customers.add(customer);
    }

    public boolean removeCustomer(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                this.customers.remove(customer);
                return false;
            }
        }
        return true;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


    public synchronized boolean isOverdraftAvailable() {
        return overdraftAvailable;
    }

    public synchronized void setOverdraftAvailable(boolean overdraftAvailable) {
        this.overdraftAvailable = overdraftAvailable;
    }

    public synchronized double getOverdraftLimit() {
        return overdraftLimit;
    }

    public synchronized void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }



    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void withdraw(double amount) {
        double amountAvailable = 0;
        if(amount > 0) {
            if (this.isOverdraftAvailable()) {
                amountAvailable = balance + overdraftLimit;
            } else  {
                amountAvailable = balance;
            }

            if (amount <= amountAvailable) {
                this.balance -= amount;
                System.out.println(Thread.currentThread().getName()+" withdrawal successfull in the account "+this.accountNumber);
                System.out.println("Amount withdrawn is "+amount+" balance after withdrawal is "+this.balance);
            } else {
                throw new IllegalArgumentException("Insufficient funds");
            }
        } else {
            throw new IllegalArgumentException("Amount you wish to withdraw cannot be 0 or below");
        }

    }

    public synchronized void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            System.out.println(Thread.currentThread().getName()+" deposit successfull in the account "+this.accountNumber);
            System.out.println("Amount deposited is "+amount+" balance after depsoit is "+this.balance);
        } else {
            throw new IllegalArgumentException("Amount you wish to withdraw cannot be 0 or below");
        }
    }

    public synchronized void addInterest(double amount) {
        if(amount > 0) {
            this.balance += amount;
            System.out.println(Thread.currentThread().getName()+" Interest is add to account "+this.accountNumber);
            System.out.println("Interest Amount  "+amount+" balance after interest is added "+this.balance);
        } else {
            throw new IllegalArgumentException("Interest Amount cannot be 0 or below");
        }
    }

    public synchronized void deductIncomeTax(double amount) {
        if(amount > 0) {
            this.balance -= amount;
            System.out.println(Thread.currentThread().getName()+" Income Tax is deducted from the account "+this.accountNumber);
            System.out.println("Income Tax Amount  "+amount+" balance after IncomeTax is deducted "+this.balance);
        } else {
            throw new IllegalArgumentException("Interest Amount cannot be 0 or below");
        }
    }

    public synchronized void deductOverdraftCharge(double amount)
    {
        if(amount >= 0) {
            this.balance -= amount;
            System.out.println(Thread.currentThread().getName()+" Overdraft charge is deducted from the account "+this.accountNumber);
            System.out.println("Overdraft Charge Amount is "+amount+" balance after IncomeTax deducted is "+this.balance);
        } else {
            throw new IllegalArgumentException("Overdraft Charge cannot be below 0");
        }
    }

    public synchronized void deductAnnualCharges(double amount) {
        if(this.accountType == AccountType.REGULAR) {
            if (amount > 0) {
                this.balance -= amount;
                System.out.println(Thread.currentThread().getName() + " Annual Charge is deducted from the account " + this.accountNumber);
                System.out.println("Annual Charge Amount is " + amount + " balance after Annual Charge deduction is " + this.balance);
            } else {
                throw new IllegalArgumentException("Annual Charge cannot be below 0");
            }
        }else {
            throw new IllegalArgumentException("VIP account! No annual charge can be deducted");
        }
    }
}
