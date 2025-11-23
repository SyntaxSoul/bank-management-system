package repository;

import entity.Account;
import entity.Customer;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private static Map<Customer, Account> accountByCustomer = new HashMap<>();
    private static Map<String, Account> accountByAccountNumber = new HashMap<>();

    public void saveAccount(Customer customer, Account account) {
        AccountRepository.accountByCustomer.put(customer, account);
        AccountRepository.accountByAccountNumber.put(account.getAccountNumber(), account);
    }

    public Account getAccountByCustomer(Customer customer) {
        return accountByCustomer.get(customer);
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountByAccountNumber.get(accountNumber);
    }
}
