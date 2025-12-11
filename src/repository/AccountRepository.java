package repository;

import entity.Account;
import entity.Customer;
import entity.Transaction;

import java.util.List;

public interface AccountRepository {
    void saveAccount(Account account);

    void updateAccount(Account account);

    Account getAccountByCustomer(Customer customer);

    Account getAccountByAccountNumber(String accountNumber);

    void addTransaction(Account account, List<Transaction> transactions);
}
