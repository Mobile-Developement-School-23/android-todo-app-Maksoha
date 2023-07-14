package com.example.todoapp.di.fragments

import com.example.todoapp.ui.view.TasksListFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class TaskListFragmentScope

@Subcomponent
@TaskListFragmentScope
interface TasksListFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TasksListFragmentComponent
    }

    fun inject(fragment: TasksListFragment)

}