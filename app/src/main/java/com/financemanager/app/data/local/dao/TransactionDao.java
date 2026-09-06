package com.financemanager.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.financemanager.app.data.local.entity.TransactionEntity;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    long insert(TransactionEntity transaction);

    @Insert
    void insertAll(List<TransactionEntity> transactions);

    @Query("SELECT * FROM transactions ORDER BY dateMillis DESC")
    LiveData<List<TransactionEntity>> observeAll();

    @Query("SELECT COUNT(*) FROM transactions")
    int count();
}
