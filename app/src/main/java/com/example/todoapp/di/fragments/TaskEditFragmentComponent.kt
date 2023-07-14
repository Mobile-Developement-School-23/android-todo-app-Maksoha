package com.example.todoapp.di.fragments

import com.example.todoapp.ui.screens.taskEdit_screen.TaskEditFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class TaskItemFragmentScope

@Subcomponent
@TaskItemFragmentScope
interface TaskEditFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TaskEditFragmentComponent
    }

    fun inject(fragment: TaskEditFragment)

}