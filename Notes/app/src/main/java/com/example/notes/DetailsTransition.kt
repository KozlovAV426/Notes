package com.example.notes

import android.transition.TransitionSet
import android.transition.ChangeBounds
import android.transition.ChangeTransform;
import android.transition.ChangeImageTransform;


class DetailsTransition: TransitionSet() {
    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
    }
}