package service;

import entity.*;
import repository.AccountRepository;
import repository.CustomerRepository;

import java.util.Scanner;

public class CustomerServices {

    Scanner scanner = new Scanner(System.in);
    AccountServices accountServices = new AccountServices();
    CustomerRepository customerRepository = new CustomerRepository();
    AccountRepository accountRepository = new AccountRepository();

    public void showMenu(Customer customer) {
        while (true) {
            System.out.println("1. Account Details");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit ");
            System.out.println("4. Withdraw ");
            System.out.println("5. Fund Transfer ");
            System.out.println("6. Transaction History");
            System.out.println("7. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
            Account account = new AccountRepository().getAccountByCustomer(customer);
            switch (choice) {

                case 1:
                    accountDetails(customer, account);
                    break;
                case 2:
                    checkBalance(account);
                    break;
                case 3:
                    deposit(account);
                    break;
                case 4:
                    withdraw(account);
                    break;
                case 5:
                    fundTransfer(account);
                    break;
                case 6:
                    transactionHistory(account);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return; // This will return to BankManagementSystem
                default:
                    System.out.println("Invalid choice!!!");
            }
        }
    }

    private void accountDetails(Customer customer, Account account) {
        System.out.println("Account Details");
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Name: " + customer.getName());
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("DOB: " + customer.getDob());
        System.out.println("Mobile No.:" + customer.getMobileNumber());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }

    private void checkBalance(Account account) {
        System.out.println("Balance: " + accountServices.balance(account));
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }

    private void deposit(Account account) {
        System.out.println("Enter amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        if (amount > 0) {
            accountServices.deposit(account, amount);
            System.out.println("Deposit successful");
            System.out.println("Click ENTER to go back to Menu");
            scanner.nextLine();
        } else {
            System.out.println("Invalid amount entered");
            System.out.println("Click ENTER to go back to Menu");
            scanner.nextLine();
        }
    }


    private void withdraw(Account account) {
        System.out.println("Enter amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        if (amount > 0) {
            if (accountServices.withdraw(account, amount)) {
                System.out.println("Withdrawal successful");
                System.out.println("Click ENTER to go back to Menu");
                scanner.nextLine();
            } else {
                System.out.println("Insufficient balance");
                System.out.println("Click ENTER to go back to Menu");
                scanner.nextLine();
            }
        } else {
            System.out.println("Invalid amount entered");
            System.out.println("Click ENTER to go back to Menu");
            scanner.nextLine();
        }
    }

    //Need to restrict Self transfer????????
    private void fundTransfer(Account account) {
        System.out.println("Fill details to transfer funds");
        System.out.println("To account no.:");
        String toAccountNumber = scanner.nextLine();
        System.out.println("Confirm \"To account no.\": ");
        String confirmToAccountNumber = scanner.nextLine();
        if (toAccountNumber.equals(confirmToAccountNumber)) {
            Account toAccount = accountRepository.getAccountByAccountNumber(toAccountNumber);
            if(account.equals(toAccount)){
                System.out.println("Self transfer not allowed!!!");
                return;
            }
            if (toAccount != null) {
                System.out.println("Enter amount to transfer: ");
                int amount = scanner.nextInt();
                scanner.nextLine();
                if (amount > 0) {
                    if (accountServices.transfer(account, toAccount, amount)) {
                        System.out.println("Fund Transfer successful");
                    } else {
                        System.out.println("Insufficient balance!!!");
                    }
                } else {
                    System.out.println("Invalid amount entered");
                }
            } else {
                System.out.println("Account no: " + toAccountNumber + ". Does not exists.");
            }
        } else {
            System.out.println("Account Number doesn't match");
        }
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }


    private void transactionHistory(Account account) {
        int count = 1;
        for (Transaction transaction : account.getTransaction()) {
            System.out.println("Transaction " + count + ": \n" + transaction.toString());
            count++;
        }
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }

    public Customer createCustomer(String name, String dob, String mobileNumber, String email, String address, String password, AccountType accountType) {
        String customerId = IdProvider.generateCustomerId();
        Customer newCustomer = new Customer(customerId, name, dob, mobileNumber, email, address, password);
        accountServices.createAccount(newCustomer, accountType);
        customerRepository.saveCustomer(newCustomer);
        return newCustomer;
    }

    public boolean loginAccount(String customerId, String password) {
        Customer customer = customerRepository.getCustomerByCustomerId(customerId);
        if (customer != null && customer.getPassword().equals(password)) {
            this.showMenu(customer);
            return true;
        }
        return false;
    }
}
