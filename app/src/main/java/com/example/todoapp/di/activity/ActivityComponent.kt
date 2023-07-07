package com.example.todoapp.di.activity

import android.app.Activity
import com.example.todoapp.ui.MainActivity
import com.example.todoapp.di.ActivityScope
import com.example.todoapp.di.fragments.ItemFragmentComponent
import com.example.todoapp.di.fragments.ListFragmentComponent
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent (modules = [RepositoryModule::class])
@ActivityScope
interface ActivityComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create (@BindsInstance activity: Activity) : ActivityComponent
    }
    fun inject(activity: MainActivity)

    fun listFragmentComponent() : ListFragmentComponent.Factory

    fun itemFragmentComponent() : ItemFragmentComponent.Factory
}