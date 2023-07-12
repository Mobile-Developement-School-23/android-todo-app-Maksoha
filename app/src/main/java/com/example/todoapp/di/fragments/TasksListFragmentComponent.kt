package com.example.todoapp.di.fragments

import com.example.todoapp.di.ListFragmentScope
import dagger.Subcomponent

@Subcomponent
@ListFragmentScope
interface TasksListFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TasksListFragmentComponent
    }

}