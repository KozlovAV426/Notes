package com.example.notes.db

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.notes.App
import com.example.notes.Note
import com.example.notes.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NoteRepository(private val databaseHolder: DatabaseHolder) {
    fun create(note: Note): Long {
        var id: Long = 0
        databaseHolder.useDatabase { database ->
            val contentValues = ContentValues()
            contentValues.put(NoteContract.NoteEntry.NOTE_DESCRIPTION, note.text)
            contentValues.put(NoteContract.NoteEntry.IMG_SOURCE, note.imgUri)
            contentValues.put(NoteContract.NoteEntry.NOTE_DATE, note.date)
            id = database.insert(NoteContract.NoteEntry.TABLE_NAME, null, contentValues)
        }
        return id
    }

    suspend fun getNoteById(id: Long): Note? {
        return withContext(Dispatchers.IO) {
            var note: Note? = null
                databaseHolder.useDatabase { database ->
                    var cursor: Cursor? = null
                    try {
                        cursor = database.query(
                            NoteContract.NoteEntry.TABLE_NAME, arrayOf(
                                NoteContract.NoteEntry._ID,
                                NoteContract.NoteEntry.NOTE_DESCRIPTION,
                                NoteContract.NoteEntry.IMG_SOURCE,
                                NoteContract.NoteEntry.NOTE_DATE
                            ), "_ID = ?", arrayOf(id.toString()),
                            null, null, null
                        )

                        if (cursor != null && cursor.moveToNext()) {
                            note =  Note(
                                cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.NOTE_DATE)),
                                cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.NOTE_DESCRIPTION)),
                                cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.IMG_SOURCE)),
                                cursor.getLong(cursor.getColumnIndex(NoteContract.NoteEntry._ID)),
                            )
                        }
                    }
                    catch (e: SQLException) {
                    } finally {
                        cursor?.close()
                    }
                }
            return@withContext note
        }
    }


    suspend fun getAllNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            val noteList: MutableList<Note> = ArrayList()
            databaseHolder.useDatabase { database ->
                var cursor: Cursor? = null
                try {
                    cursor = database.query(
                        NoteContract.NoteEntry.TABLE_NAME,
                        arrayOf(
                            NoteContract.NoteEntry._ID,
                            NoteContract.NoteEntry.NOTE_DESCRIPTION,
                            NoteContract.NoteEntry.IMG_SOURCE,
                            NoteContract.NoteEntry.NOTE_DATE
                        ),
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                    while (cursor.moveToNext()) {
                        val note = Note(
                            cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.NOTE_DATE)),
                            cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.NOTE_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.IMG_SOURCE)),
                            cursor.getLong(cursor.getColumnIndex(NoteContract.NoteEntry._ID)),
                        )
                        noteList.add(note)
                    }
                } catch (e: SQLException) {
                } finally {
                    cursor?.close()
                }
            }
            return@withContext noteList
        }
    }
}