package com.example.notes

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NoteViewHolder(
        itemView: View,
        listener: NoteAdapter.Listener?
) : RecyclerView.ViewHolder(itemView) {
    val noteImageView: ImageView = itemView.findViewById(R.id.note_img)
    private val noteTextView: TextView = itemView.findViewById(R.id.note_text)
    private val noteDate: TextView = itemView.findViewById(R.id.note_date)
    private val listener = listener;

    fun bind(note: Note, id: Int) {

        noteTextView.text = note.text
        noteDate.text = note.date

        Glide
            .with(noteImageView)
            .load(note.imgUri)
            .centerInside()
            .into(noteImageView)

        noteTextView.setOnClickListener {listener?.onNoteClick(id, this)}
        noteImageView.setOnClickListener {listener?.onNoteClick(id, this)}
    }

}