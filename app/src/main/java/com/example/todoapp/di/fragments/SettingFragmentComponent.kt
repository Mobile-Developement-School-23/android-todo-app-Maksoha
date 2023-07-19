package com.example.todoapp.di.fragments

import com.example.todoapp.ui.screens.setting_screen.SettingsFragment
import com.example.todoapp.ui.view.TasksListFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class SettingFragmentScope

@SettingFragmentScope
@Subcomponent
interface SettingFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingFragmentComponent
    }

    fun inject(fragment: SettingsFragment)

}