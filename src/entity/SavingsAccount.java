package entity;

import java.time.LocalDateTime;
import java.util.List;

public class SavingsAccount extends Account {
    public SavingsAccount(String customerId, String accountNumber, Double initialBalance) {
        super(customerId,accountNumber,initialBalance,AccountType.SAVINGS);
    }

    //Modified
    public SavingsAccount(String customerId, String accountNumber, Double balance, LocalDateTime createdOn, List<Transaction> transaction) {
        super(customerId,accountNumber,balance,createdOn,AccountType.SAVINGS ,transaction);
    }

    private final double charges=0.01;

    protected double calculateCharges(double amount){
        return amount*charges;
    }
}
