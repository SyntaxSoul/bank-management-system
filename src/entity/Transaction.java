package entity;

import utility.IdProvider;

import java.time.LocalDateTime;

public class Transaction {
    //Modified
    private final  String id;
    private final LocalDateTime date;
    private final TransactionType transactionType;
    private final double amount;
    private final double balanceAfter;
    private final String comment;



    public enum TransactionType {
        //Modified
        CREDIT,
        DEBIT
    }

    // The enum can be created inside a class like nested class

    //Modified
    public Transaction(double amount, TransactionType transactionType,double balanceAfter) {
        this.id = IdProvider.generateTransactionId();
        this.date=LocalDateTime.now();
        this.amount = amount;
        this.balanceAfter=balanceAfter;
        this.transactionType = transactionType;
        this.comment="No comment added";
    }

    //Modified
    public Transaction(double amount, TransactionType transactionType, double balanceAfter, String comment) {
        this.id = IdProvider.generateTransactionId();
        this.date=LocalDateTime.now();
        this.amount = amount;
        this.balanceAfter=balanceAfter;
        this.transactionType = transactionType;
        this.comment = comment;
    }

    //Modified
    public Transaction(String transactionId,LocalDateTime date,TransactionType type, double amount, double balanceAfter, String comment){
        this.id=transactionId;
        this.date=date;
        this.transactionType=type;
        this.amount=amount;
        this.balanceAfter=balanceAfter;
        this.comment=comment;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getComment() {
        return comment;
    }

    //Modified
    @Override
    public String toString() {
        return "{\n" + this.id + "\n" + this.date + "\n" + this.amount + "\n" + this.transactionType + "\n" + this.comment + "\n}";
    }
}


//Changes made at "Modified" comment
//Modify -> made all class var as final
//Modify -> added date,comment and balanceAfter to existing constructor
//Add -> Create new constructor for file
//Modify -> int amount - double amount
// Remove -> String date,time - Add LocalDateTime date
//Remove -> setter and getter for date and time
//Add -> double balanceAfter;
//Modify -> enum TransactionType Cr,Dr - CREDIT , DEBIT
//Modify -> toString() return(date,time) - return(date)