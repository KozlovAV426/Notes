package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.db.NoteRepository
import kotlinx.coroutines.*


class ListFragment: Fragment() {
    companion object {
        fun newInstance(): Fragment {
            return ListFragment()
        }

        const val TAG = "ListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.list_fragment, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.noteRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.setMaxRecycledViews(0, 5)

        val adapter = NoteAdapter()
        recyclerView.adapter = adapter

        MainScope().launch {
            val noteList = App.getNoteRepository().getAllNotes()
            adapter.setNoteList(noteList)
        }

        if (activity is NoteAdapter.Listener) {
            adapter.setListener(activity as NoteAdapter.Listener)
        }

        val fab: View = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(activity, CameraActivity::class.java)
            activity?.startActivityForResult(intent, MainActivity.myRequestCode)
        }

        return view
    }

}