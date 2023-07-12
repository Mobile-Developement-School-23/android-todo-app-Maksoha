package com.example.todoapp.di.activity

import android.content.Context
import com.example.todoapp.di.ActivityScope
import com.example.todoapp.di.fragments.TaskEditFragmentComponent
import com.example.todoapp.di.fragments.TasksListFragmentComponent
import com.example.todoapp.ui.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [RepositoryModule::class])
@ActivityScope
interface ActivityComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ActivityComponent
    }

    fun inject(activity: MainActivity)

    fun listFragmentComponent(): TasksListFragmentComponent.Factory

}