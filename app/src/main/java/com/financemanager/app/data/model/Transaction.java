package com.financemanager.app.data.model;

public class Transaction {

    private final long id;
    private final double amount;
    private final TransactionType type;
    private final String note;
    private final long dateMillis;

    public Transaction(long id,
                       double amount,
                       TransactionType type,
                       String note,
                       long dateMillis) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.note = note;
        this.dateMillis = dateMillis;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

    public long getDateMillis() {
        return dateMillis;
    }
}
