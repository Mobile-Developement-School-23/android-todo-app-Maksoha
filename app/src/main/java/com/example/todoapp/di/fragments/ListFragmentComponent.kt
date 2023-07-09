package com.example.todoapp.di.fragments

import android.content.Context
import com.example.todoapp.di.ListFragmentScope
import com.example.todoapp.ui.view.ItemFragment
import com.example.todoapp.ui.view.ListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
@ListFragmentScope
interface ListFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : ListFragmentComponent
    }
    fun inject(listFragment: ListFragment)

}