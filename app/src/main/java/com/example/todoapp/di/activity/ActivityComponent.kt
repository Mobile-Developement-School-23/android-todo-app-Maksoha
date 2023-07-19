package com.example.todoapp.di.activity

import com.example.todoapp.di.fragments.SettingFragmentComponent
import com.example.todoapp.di.fragments.TaskEditFragmentComponent
import com.example.todoapp.di.fragments.TasksListFragmentComponent
import com.example.todoapp.ui.MainActivity
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class ActivityScope

@Subcomponent
@ActivityScope
interface ActivityComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }

    fun inject(activity: MainActivity)

    fun taskListFragmentComponent(): TasksListFragmentComponent.Factory

    fun taskEditFragmentComponent(): TaskEditFragmentComponent.Factory

    fun settingFragmentComponent(): SettingFragmentComponent.Factory


}
