package com.example.todoapp.di.activity

import com.example.todoapp.ui.MainActivity
import com.example.todoapp.di.ActivityScope
import com.example.todoapp.di.fragments.ItemFragmentComponent
import com.example.todoapp.di.fragments.ListFragmentComponent
import dagger.Subcomponent

@Subcomponent (modules = [RepositoryModule::class])
@ActivityScope
interface ActivityComponent {
    fun inject(activity: MainActivity)

    fun listFragmentComponent() : ListFragmentComponent

    fun itemFragmentComponent() : ItemFragmentComponent
}