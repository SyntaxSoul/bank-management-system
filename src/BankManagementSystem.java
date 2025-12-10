import dto.AccountInfo;
import entity.Account;
import entity.AccountType;
import entity.Customer;
import entity.Transaction;
import service.AccountServices;
import service.CustomerServices;

import java.util.Scanner;

public class BankManagementSystem {
    private Scanner scanner = new Scanner(System.in);
    private CustomerServices customerServices = new CustomerServices();
    private AccountServices accountServices = new AccountServices();

    public static void main(String[] args) {
        new BankManagementSystem().run();
    }

    public void run() {
        while (true) {
            System.out.println("1. Login: ");
            System.out.println("2. Create new account: ");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    login();
                    break;

                case 2:
                    createAccount();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try Again.");
            }
        }
    }

    public void login() {
        System.out.println("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        if (!customerServices.loginAccount(customerId, password)) {
            System.out.println("Invalid credentials! Try again.");
            return;
        }

        showMenu(customerId);
    }

    public void createAccount() {
        System.out.println("--Account Creation Form--");
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Enter you DOB(DD/MM/YYYY): ");
        String dob = scanner.nextLine();
        System.out.println("Enter your mobile number: ");
        String mobileNumber = scanner.nextLine();
        System.out.println("Enter E-mail: ");
        String email = scanner.nextLine();
        System.out.println("Enter your address: ");
        String address = scanner.nextLine();
        boolean matchPassword = false;
        String password = "";
        while (!matchPassword) {
            System.out.println("Enter New Password: ");
            password = scanner.nextLine();
            System.out.println("Confirm password: ");
            String confirmPassword = scanner.nextLine();
            if (password.equals(confirmPassword)) {
                matchPassword = true;
            } else {
                System.out.println("Password and Confirm password doesn't match");
            }
        }
        System.out.println("Select account type(CURRENT/SAVINGS): ");
        boolean validAccountType = false;
        AccountType accountType = null;
        while (!validAccountType) {
            try {
                accountType = AccountType.valueOf(scanner.nextLine().toUpperCase());
                validAccountType = true;
            } catch (Exception e) {
                System.out.println("Invalid account type entered! Please chose valid account type");
            }
        }
        Customer newCustomer = customerServices.createCustomer(name, dob, mobileNumber, address, email, password, accountType);
        System.out.println("Account Created your customer ID is: " + newCustomer.getCustomerId());
        System.out.println("Please note it for future use");
        System.out.println("Click ENTER to Login");
        scanner.nextLine();
    }

    public void showMenu(String customerId) {
        while (true) {
            System.out.println("1. Account Details");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit ");
            System.out.println("4. Withdraw ");
            System.out.println("5. Fund Transfer ");
            System.out.println("6. Transaction History");
            System.out.println("7. Logout");
            AccountInfo accountInfo=accountServices.getAccountInfo(customerId);
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    accountDetails(accountInfo);
                    break;
                case 2:
                    checkBalance(accountInfo);
                    break;
                case 3:
                    deposit(accountInfo);
                    break;
                case 4:
                    withdraw(accountInfo);
                    break;
                case 5:
                    fundTransfer(accountInfo);
                    break;
                case 6:
                    transactionHistory(accountInfo);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return; // This will return to BankManagementSystem
                default:
                    System.out.println("Invalid choice!!!");
            }
        }
    }

    public  void accountDetails(AccountInfo accountInfo) {
        System.out.println("Account Details");
        System.out.println("Account Type: " + accountInfo.getAccountType());
        System.out.println("Account Number: " + accountInfo.getAccountNumber());
        System.out.println("Name: " + accountInfo.getName());
        System.out.println("Customer ID: " + accountInfo.getCustomerId());
        System.out.println("DOB: " + accountInfo.getDob());
        System.out.println("Mobile No.:" + accountInfo.getMobileNumber());
        System.out.println("Email: " + accountInfo.getEmail());
        System.out.println("Address: " + accountInfo.getAddress());
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }

    public  void checkBalance(AccountInfo accountInfo) {
        System.out.println("Balance: " + accountInfo.getBalance());
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }

    public  void deposit(AccountInfo accountInfo) {
        System.out.println("Enter amount: ");
        int amount = scanner.nextInt();
        try{
            if(accountServices.deposit(accountInfo, amount)){
                System.out.println("Deposit successful.");
            }
            else {
                System.out.println("Deposit failed!");
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid amount entered");
        }

        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }

    public  void withdraw(AccountInfo accountInfo) {
        System.out.println("Enter amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        try{
            if(accountServices.withdraw(accountInfo, amount)) {
                System.out.println("Withdrawal successful.");
            }
            else {
                System.out.println("Withdrawal Failed!");
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid amount entered");
        }
        catch (IllegalStateException e){
            System.out.println("Insufficient Balance");
        }
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }

    //Need to restrict Self transfer????????
    public void fundTransfer(AccountInfo accountInfo) {
        System.out.println("Fill details to transfer funds");
        System.out.println("To account no.:");
        String toAccountNumber = scanner.nextLine();
        System.out.println("Confirm \"To account no.\": ");
        String confirmToAccountNumber = scanner.nextLine();
        if (toAccountNumber.equals(confirmToAccountNumber)) {
            System.out.println("Enter amount to transfer: ");
            int amount = scanner.nextInt();
            scanner.nextLine();
            try {
                if(accountServices.fundTransfer(accountInfo, toAccountNumber, amount)) {
                    System.out.println("Fund transfer successful");
                }
                else {
                    System.out.println("Fund transfer failed");
                }
            }
            catch (IllegalArgumentException e){
                System.out.println("Invalid amount entered");
            }
            catch (IllegalStateException e){
                System.out.println("Insufficient Balance");
            }

        } else {
            System.out.println("Account Number doesn't match");
        }
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }

    public void transactionHistory(AccountInfo accountInfo) {
        int count = 1;
        for (Transaction transaction : accountServices.getTransaction(accountInfo)) {
            System.out.println("Transaction " + count + ": \n" + transaction.toString());
            count++;
        }
        System.out.println("Click ENTER to go back to Menu");
        scanner.nextLine();
    }
}