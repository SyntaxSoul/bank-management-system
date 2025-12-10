package dto;

import entity.AccountType;
import entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class AccountInfo {
    private final String customerId;
    private final String name;
    private final String mobileNumber;
    private final String address;
    private final String email;
    private final String dob;
    private final String accountNumber;
    private final Double balance;  //Modified
    private final LocalDateTime createdOn;
    private final AccountType accountType;
    private final List<Transaction> transaction;

    public AccountInfo(String customerId, String name, String mobileNumber, String address, String email, String dob,
                       String accountNumber, Double balance, LocalDateTime createdOn, AccountType accountType,List<Transaction> transaction) {
        this.customerId = customerId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.email = email;
        this.dob = dob;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdOn = createdOn;
        this.accountType = accountType;
        this.transaction=transaction;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransaction() {
        
        return transaction;
    }
}
