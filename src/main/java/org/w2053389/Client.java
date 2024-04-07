package org.w2053389;

import java.util.List;
import java.util.ArrayList;

// simulation
public class Client {

    public static void main(String[] args) {
        Bank bank = new Bank();

        Customer customer = new Customer("C001", "Guhanathan", "Poravi", "077", "Colombo 06", "XXX");
        List<Customer> customers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        customers.add(customer);
        BankAccount account1 = bank.createAccount("201864747", customers, AccountType.REGULAR, false, 0, 0);

        // multiple account and multiple customer with different account types must be created
        Customer customer1 = new Customer("C001", "Amila", "Thushara", "0771234567", "12 Peradeniya Road, Kandy", "931234567V");
        Customer customer2 = new Customer("C002", "Bimali", "Perera", "0762345678", "34 Galle Road, Mount Lavinia", "922345678V");
        Customer customer3 = new Customer("C003", "Chamara", "Kapugedera", "0753456789", "45 Kynsey Road, Colombo 8", "913456789V");
        Customer customer4 = new Customer("C004", "Dinesh", "Chandimal", "0744567890", "78 Highlevel Road, Nugegoda", "904567890V");
        Customer customer5 = new Customer("C005", "Erandi", "Fernando", "0735678901", "23 Main Street, Jaffna", "895678901V");
        Customer customer6 = new Customer("C006", "Fathima", "Rizvi", "0726789012", "67 Templers Road, Matara", "886789012V");
        Customer customer7 = new Customer("C007", "Geeth", "Dilshan", "0717890123", "89 Horton Place, Colombo 7", "877890123V");
        Customer customer8 = new Customer("C008", "Harsha", "Silva", "0708901234", "109 Barnes Place, Colombo 7", "868901234V");
        Customer customer9 = new Customer("C009", "Ishan", "Bandara", "0699012345", "32 Beach Road, Negombo", "859012345V");
        Customer customer10 = new Customer("C010", "Janaki", "Kumari", "0680123456", "200 Main Road, Batticaloa", "841234567V");
        Customer customer11 = new Customer("C011", "Rusiru", "Bandara", "0680123456", "14 Road, Horana", "841234235V");


        //Joint accounts
        List<Customer> customers2 = new ArrayList<>();
        customers2.add(customer2);
        customers2.add(customer3);
        BankAccount account2 = bank.createAccount("202012345", customers2, AccountType.VIP, true, 5000, 2.5);

        List<Customer> customers3 = new ArrayList<>();
        customers3.add(customer4);
        customers3.add(customer5);
        BankAccount account3 = bank.createAccount("202134567", customers3, AccountType.REGULAR, false, 0, 3.0);

        List<Customer> customers4 = new ArrayList<>();
        customers4.add(customer6);
        customers4.add(customer7);
        customers4.add(customer8);
        BankAccount account4 = bank.createAccount("202245678", customers4, AccountType.REGULAR, true, 1500, 3.0);

        //single-user accounts
        List<Customer> customers5 = new ArrayList<>();
        customers5.add(customer9);
        BankAccount account5 = bank.createAccount("202356789", customers5, AccountType.REGULAR, true, 1000, 2.0);

        List<Customer> customers6 = new ArrayList<>();
        customers6.add(customer10);
        BankAccount account6 = bank.createAccount("202467890", customers6, AccountType.REGULAR, false, 0, 2.0);

        List<Customer> customers7 = new ArrayList<>();
        customers7.add(customer11);
        BankAccount account7 = bank.createAccount("202578901", customers7, AccountType.VIP, true, 2000, 3.5);

        ThreadGroup bankManager = new ThreadGroup("BankManagers");
        bankManager.setMaxPriority(8);
        Thread annualChargesCalculationManagerThread = new Thread(bankManager, new AnnualChargesCalculationManager(bank), "Annual Charges Calculation Manager");
        Thread incomeTaxCalculationManagerThread = new Thread(bankManager, new IncomeTaxCalculationManager(bank), "Income Tax Calculation Manager");
        Thread interestCalculationManagerThread = new Thread(bankManager, new InterestCalculationManager(bank), "Interest Calculation Manager");

        ThreadGroup vipCustomer = new ThreadGroup("VIP Customer");
        ThreadGroup regularCustomer = new ThreadGroup("Regular Customer");
        vipCustomer.setMaxPriority(10);
        regularCustomer.setMaxPriority(5);

        Thread depositorThread1 = new Thread(regularCustomer, new Depositor(account1, 10000), "Guhanathan P");
        Thread depositorThread2 = new Thread(regularCustomer, new Depositor(account1, 10000), "Guhanathan P");
        Thread depositorThread3 = new Thread(regularCustomer, new Depositor(account1, 10000), "Guhanathan P");
        Thread depositorThread4 = new Thread(vipCustomer, new Depositor(account2,500000), "Alice S");
        Thread depositorThread5 = new Thread(vipCustomer, new Depositor(account2,500000), "Michael J");
        Thread depositorThread6 = new Thread(regularCustomer, new Depositor(account3,8000), "Emily B");
        Thread depositorThread7 = new Thread(regularCustomer, new Depositor(account3,1000), "David M");
        Thread depositorThread8 = new Thread(regularCustomer, new Depositor(account4,350000), "Sophia G");
        Thread depositorThread9 = new Thread(regularCustomer, new Depositor(account5,7000), "Oliver A");
        Thread depositorThread10 = new Thread(regularCustomer, new Depositor(account6,9000), "Ava H");
        Thread depositorThread11 = new Thread(vipCustomer, new Depositor(account7,50000), "William L");

        Thread receiverThread1 = new Thread(regularCustomer, new Receiver(customer, account1, 5000), "Guhanathan P");
        Thread receiverThread2 = new Thread(regularCustomer, new Receiver(customer, account1, 15000), "Guhanathan P");
        Thread receiverThread3 = new Thread(regularCustomer, new Receiver(customer, account1, 25000), "Guhanathan P");
        Thread receiverThread4 = new Thread(vipCustomer, new Receiver(customer3,account2,10000), "Michael J");
        Thread receiverThread5 = new Thread(regularCustomer, new Receiver(customer4,account3,100), "Emily B");
        Thread receiverThread6 = new Thread(regularCustomer, new Receiver(customer8,account4,10000), "Emma T");
        Thread receiverThread7 = new Thread(regularCustomer, new Receiver(customer9,account5,10000), "Oliver A");
        Thread receiverThread8 = new Thread(regularCustomer, new Receiver(customer10,account6,700), "Ava H");
        Thread receiverThread9 = new Thread(vipCustomer, new Receiver(customer11,account7,10000), "William L");

        threads.add(depositorThread1);
        threads.add(depositorThread2);
        threads.add(depositorThread3);

        threads.add(depositorThread4);
        threads.add(depositorThread5);
        threads.add(depositorThread6);
        threads.add(depositorThread7);
        threads.add(depositorThread8);
        threads.add(depositorThread9);
        threads.add(depositorThread10);
        threads.add(depositorThread11);

        threads.add(interestCalculationManagerThread);
        threads.add(incomeTaxCalculationManagerThread);
        threads.add(annualChargesCalculationManagerThread);

        threads.add(receiverThread1);
        threads.add(receiverThread2);
        threads.add(receiverThread3);
        threads.add(receiverThread4);

        threads.add(receiverThread5);
        threads.add(receiverThread6);
        threads.add(receiverThread7);
        threads.add(receiverThread8);
        threads.add(receiverThread9);

        for (Thread thread: threads) {
            thread.start();
        }
    }
}