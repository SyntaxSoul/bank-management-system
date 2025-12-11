package service;

import entity.AccountType;
import entity.Customer;
import repository.CustomerFileRepository;
import repository.CustomerRepository;
import utility.IdProvider;

import java.util.Scanner;

public class CustomerServices {

    Scanner scanner = new Scanner(System.in);
    AccountServices accountServices = new AccountServices();
    CustomerRepository customerRepository = new CustomerFileRepository();

    public Customer createCustomer(String name, String dob, String mobileNumber, String address, String email, String password, AccountType accountType) {
        String customerId = IdProvider.generateCustomerId();
        Customer newCustomer = new Customer(customerId, name, dob, mobileNumber, address, email, password);
        accountServices.createAccount(newCustomer, accountType);
        customerRepository.saveCustomer(newCustomer);
        return newCustomer;
    }

    public boolean loginAccount(String customerId, String password) {
        Customer customer = customerRepository.getCustomerByCustomerId(customerId);
        return customer != null && customer.getPassword().equals(password);
    }
}
