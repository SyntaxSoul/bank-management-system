package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    //Modified
    private final String customerId;
    private final String accountNumber;
    private Double balance;  //Modified
    private final LocalDateTime createdOn;
    private final AccountType accountType;
    private List<Transaction> transaction = new ArrayList<>();

    //Modified
    protected Account(String customerId, String accountNumber, Double initialBalance, AccountType accountType) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.createdOn = LocalDateTime.now();
        this.accountType = accountType;
    }

    //Modified
    protected Account(String customerId, String accountNumber, Double balance, LocalDateTime createdOn, AccountType accountType, List<Transaction> transaction) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.createdOn = createdOn;
        this.transaction = transaction;
    }

    //Abstract Methods
    abstract double calculateCharges(double amount);


    //Transaction Methods
    public Transaction createTransaction(double amount, Transaction.TransactionType transactionType, double balanceAfter) {
        Transaction transaction = new Transaction(amount, transactionType, balanceAfter);
        return transaction;
    }

    public Transaction createTransaction(double amount, Transaction.TransactionType transactionType, double balanceAfter, String comment) {
        Transaction transaction = new Transaction(amount, transactionType, balanceAfter, comment);
        return transaction;
    }


    //Account Methods
    public void addTransaction(Transaction transaction) {
        this.transaction.add(transaction);
    }

    public List<Transaction> deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount entered!");
        List<Transaction> trList = new ArrayList<>();
        balance += amount;
        Transaction transaction = createTransaction(amount, Transaction.TransactionType.CREDIT, getBalance());
        addTransaction(transaction);
        trList.add(transaction);
        return trList;
    }

    public List<Transaction> withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount entered!");
        List<Transaction> trList = new ArrayList<>();
        double charges = calculateCharges(amount);
        double total = amount + charges;
        if (balance < total) throw new IllegalStateException("Insufficient Amount");
        balance -= amount;
        trList.add(createTransaction(amount, Transaction.TransactionType.DEBIT, getBalance()));
        balance -= charges;
        trList.add(createTransaction(charges, Transaction.TransactionType.DEBIT, getBalance(), "Transaction Charges"));
        return trList;
    }

    public List<Transaction> transferIn(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount entered!");
        List<Transaction> trList = new ArrayList<>();
        balance += amount;
        trList.add(createTransaction(amount, Transaction.TransactionType.DEBIT, getBalance()));
        return trList;
    }

    public List<Transaction> transferOut(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount entered!");
        List<Transaction> trList = new ArrayList<>();
        double charges = calculateCharges(amount);
        double total = amount + charges;
        if (balance < total) throw new IllegalStateException("Insufficient Amount");
        balance -= amount;
        trList.add(createTransaction(amount, Transaction.TransactionType.DEBIT, getBalance()));
        balance -= amount;
        trList.add(createTransaction(charges, Transaction.TransactionType.DEBIT, getBalance(), "Transaction Charges"));
        return trList;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }
}


//Changes made at "Modified" comment.
//Add -> String Customer;
//Modify -> Float balance - Double Balance
//Add -> LocalDateTime createdOn;
//Modify -> empty constructor with no arg - constructor with args and assign values
//Modify ->rearranged constructor args
