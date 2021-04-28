package com.example.notes

import android.app.Application
import android.content.Context
import com.example.notes.db.DatabaseHolder
import com.example.notes.db.NoteRepository

class App: Application() {
    companion object {
        private lateinit var databaseHolder: DatabaseHolder
        private lateinit var context: Context
        private lateinit var noteRepository: NoteRepository

        fun getContext(): Context {
            return context
        }

        fun getDatabaseHolder(): DatabaseHolder {
            return databaseHolder
        }

        fun getNoteRepository(): NoteRepository {
            return noteRepository
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        context.filesDir
        databaseHolder = DatabaseHolder(context)
        noteRepository = NoteRepository(databaseHolder)
    }

}