package repository;

import entity.Account;
import entity.Customer;
import entity.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountInMemoryRepository implements AccountRepository {
    //Modified
    private static final Map<String, Account> accountByCustomer = new HashMap<>();
    private static final Map<String, Account> accountByAccountNumber = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        AccountInMemoryRepository.accountByCustomer.put(account.getCustomerId(), account);
        AccountInMemoryRepository.accountByAccountNumber.put(account.getAccountNumber(), account);
    }

    @Override
    public void updateAccount(Account account){
    }

    @Override
    public Account getAccountByCustomer(Customer customer) {
        return accountByCustomer.get(customer.getCustomerId());
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        return accountByAccountNumber.get(accountNumber);
    }

    @Override
    public void addTransaction(Account account, List<Transaction> transactions){
        for (Transaction transaction:transactions) {
            account.addTransaction(transaction);
        }
    }
}

//Changes made at "Modified" comment
//Modify -> Map<Customer,Account> -> Map<String,Account> @ accountByCustomer
//Add -> @Override annotation