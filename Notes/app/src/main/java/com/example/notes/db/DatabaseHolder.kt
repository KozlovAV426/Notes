package com.example.notes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import java.util.concurrent.locks.ReentrantLock


class DatabaseHolder(context: Context) {
    private val appSqliteOpenHelper = AppSqliteOpenHelper(context)
    private var sqLiteDatabase: SQLiteDatabase? = null
    private var databaseOpenCloseBalance = 0
    private var reentrantLock = ReentrantLock()

    fun open(): SQLiteDatabase {
        try {
            reentrantLock.lock()
            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase = appSqliteOpenHelper.writableDatabase
            }

            ++databaseOpenCloseBalance

            return sqLiteDatabase!!
        } finally {
            reentrantLock.unlock();
        }
    }

    fun close() {
        try {
            reentrantLock.lock()
            --databaseOpenCloseBalance;

            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase?.close()
                sqLiteDatabase = null
            }
        } finally {
            reentrantLock.unlock()
        }
    }
}

fun DatabaseHolder.useDatabase(action: (SQLiteDatabase) -> Unit) {
    try {
        val db = this.open()
        action(db)
    } catch (e: SQLiteException) {}
    finally {
        this.close()
    }
}