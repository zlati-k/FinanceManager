package com.financemanager.app.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.financemanager.app.data.local.dao.TransactionDao;
import com.financemanager.app.data.local.entity.TransactionEntity;

import java.util.Arrays;
import java.util.concurrent.Executors;

@Database(
        entities = {TransactionEntity.class},
        version = 4,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract TransactionDao transactionDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "finance_manager.db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                    seedIfEmpty(INSTANCE);
                }
            }
        }
        return INSTANCE;
    }

    private static void seedIfEmpty(AppDatabase database) {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (database.transactionDao().count() > 0) {
                return;
            }
            long now = System.currentTimeMillis();
            database.transactionDao().insertAll(Arrays.asList(
                    transaction(1800.00, "INCOME", "Месечна заплата", now - 259200000L),
                    transaction(42.50, "EXPENSE", "Магазин", now - 172800000L),
                    transaction(15.00, "EXPENSE", "Градски транспорт", now - 86400000L),
                    transaction(120.00, "INCOME", "Подарък", now)
            ));
        });
    }

    private static TransactionEntity transaction(double amount, String type, String note, long dateMillis) {
        TransactionEntity entity = new TransactionEntity();
        entity.amount = amount;
        entity.type = type;
        entity.note = note;
        entity.dateMillis = dateMillis;
        return entity;
    }
}
