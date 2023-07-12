package com.example.todoapp.di.fragments

import com.example.todoapp.di.ItemFragmentScope
import dagger.Subcomponent


@Subcomponent
@ItemFragmentScope
interface TaskEditFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TaskEditFragmentComponent
    }
}