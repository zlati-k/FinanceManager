package com.financemanager.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class TransactionEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public double amount;

    public String type;

    public String note;

    public long dateMillis;
}
