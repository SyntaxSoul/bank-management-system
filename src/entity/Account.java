package entity;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private Float balance;
    private AccountType accountType;
    List<Transaction> transaction = new ArrayList<>();

    public Account() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    public void addTransaction(Transaction transaction) {
        this.transaction.add(transaction);
    }
}
