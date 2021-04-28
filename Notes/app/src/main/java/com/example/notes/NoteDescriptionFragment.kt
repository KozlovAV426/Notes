package com.example.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class NoteDescriptionFragment: Fragment() {

    val coroutineScope = MainScope()

    companion object {
        private const val ID_KEY = "ID_KEY"

        fun newInstance(id: Int): Fragment {
            val fragment = NoteDescriptionFragment()
            val bundle = Bundle()

            bundle.putInt(ID_KEY, id)
            fragment.arguments = bundle
            return fragment
        }

        const val TAG = "NoteDescriptionFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_note_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // since records in the database are indexed from 1
        val id = (arguments?.getInt(ID_KEY) ?: 0) + 1

        val imageView = view.findViewById<ImageView>(R.id.note_description_img)
        val textView = view.findViewById<TextView>(R.id.note_description_text)

        coroutineScope.launch {
            val note = App.getNoteRepository().getNoteById(id.toLong())
            textView.text = note?.text
            val imgUri = note?.imgUri
            if (imgUri != null) {
                Glide
                    .with(imageView)
                    .load(imgUri)
                    .centerInside()
                    .into(imageView)
            } else {
                activity?.supportFragmentManager?.popBackStack();
            }
        }
        imageView.transitionName = "descriptionImg"
    }



}