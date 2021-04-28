package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import androidx.appcompat.app.AppCompatActivity

import kotlinx.coroutines.*



class MainActivity : AppCompatActivity(), NoteAdapter.Listener {

    companion object {
        const val myRequestCode = 1
    }

    val coroutineScope = MainScope()

    override fun onNoteClick(id: Int, holder: NoteViewHolder) {
        showDetailFragment(id, holder)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_old)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_container, ListFragment.newInstance(), ListFragment.TAG)
                .addToBackStack(null)
                .commit();
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == myRequestCode) {
            if (resultCode == RESULT_OK) {
                data?.let { data ->
                    val id = data.getIntExtra("id", -1)
                    if (id != -1) {
                        val noteDetail = NoteDescriptionFragment.newInstance(id)
                        supportFragmentManager
                                .beginTransaction()
                                .replace(
                                        R.id.activity_main_container, noteDetail,
                                        NoteDescriptionFragment.TAG
                                )
                                .addToBackStack(null)
                                .commitAllowingStateLoss();
                    }
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showDetailFragment(id: Int, holder: NoteViewHolder) {
        if (supportFragmentManager.findFragmentByTag(NoteDescriptionFragment.TAG) != null) {
            supportFragmentManager.popBackStack();
        }

        val noteDetail = NoteDescriptionFragment.newInstance(id )
        noteDetail.sharedElementEnterTransition = DetailsTransition()
        noteDetail.enterTransition = Fade()
        noteDetail.exitTransition = Fade()
        noteDetail.sharedElementReturnTransition = DetailsTransition()
        val imageView = holder.noteImageView

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.activity_main_container, noteDetail,
                NoteDescriptionFragment.TAG
            )
            .addToBackStack(null)
            .addSharedElement(imageView, "descriptionImg")
            .commit();
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            (supportFragmentManager.findFragmentByTag(NoteDescriptionFragment.TAG)
                    as NoteDescriptionFragment).coroutineScope.cancel()
            supportFragmentManager.popBackStack();
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}