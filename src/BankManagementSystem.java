import entity.AccountType;
import entity.Customer;
import service.CustomerServices;

import java.util.Scanner;

public class BankManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static CustomerServices customerServices = new CustomerServices();

    public static void main(String[] args) {

        while (true) {
            System.out.println("1. Login: ");
            System.out.println("2. Create new account: ");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: {
                    System.out.println("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    if (!customerServices.loginAccount(customerId, password)) {
                        System.out.println("Invalid credentials! Try again.");
                    }
                }
                break;

                case 2:
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
                            accountType = AccountType.valueOf(scanner.nextLine());
                            validAccountType = true;
                        } catch (Exception e) {
                            System.out.println("Invalid account type entered! Please chose valid account type");
                        }
                    }
                    Customer newCustomer = new CustomerServices().createCustomer(name, dob, mobileNumber, address, email, password, accountType);
                    System.out.println("Account Created your customer ID is: " + newCustomer.getCustomerId());
                    System.out.println("Please note it for future use");
                    System.out.println("Click ENTER to Login");
                    scanner.nextLine();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try Again.");
            }
        }
    }
}
