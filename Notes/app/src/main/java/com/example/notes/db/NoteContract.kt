package com.example.notes.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


object NoteContract {
    object NoteEntry : BaseColumns {
        const val TABLE_NAME = "note_table"
        const val NOTE_DESCRIPTION = "note_description"
        const val IMG_SOURCE = "img_source"
        const val NOTE_DATE = "note_date"
        const val _ID = BaseColumns._ID
    }

    private const val DB_CREATE = "CREATE TABLE ${NoteEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${NoteEntry.NOTE_DESCRIPTION} TEXT NOT NULL," +
            "${NoteEntry.IMG_SOURCE} TEXT NOT NULL, " +
            "${NoteEntry.NOTE_DATE} INTEGER NOT NULL)"

    private const val DB_DROP = "DROP TABLE IF EXISTS ${NoteEntry.TABLE_NAME}"


    fun createTable(db: SQLiteDatabase) = db.execSQL(DB_CREATE)
    fun deleteTable(db: SQLiteDatabase) = db.execSQL(DB_DROP)


}