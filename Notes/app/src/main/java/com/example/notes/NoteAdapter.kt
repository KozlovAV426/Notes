package com.example.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter: RecyclerView.Adapter<NoteViewHolder>() {
    interface Listener {
        fun onNoteClick(id: Int, holder: NoteViewHolder)
    }

    private var noteList: List<Note> = emptyList()
    private var listener: Listener? = null

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    fun setNoteList(noteList: List<Note>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
                R.layout.note_list_item,
                parent,
                false
        )
        return NoteViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position], position)

        ViewCompat.setTransitionName(holder.noteImageView,
            position.toString() + "_image")
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

}