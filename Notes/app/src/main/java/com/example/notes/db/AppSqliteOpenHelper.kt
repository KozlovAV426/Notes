package com.example.notes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSqliteOpenHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, VERSION
) {

    companion object {
        private const val DATABASE_NAME = "NOTE_DATABASE"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        NoteContract.createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

}