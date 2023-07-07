package com.example.todoapp.di.fragments

import com.example.todoapp.di.ListFragmentScope
import com.example.todoapp.ui.view.ListFragment
import dagger.Subcomponent

@Subcomponent
@ListFragmentScope
interface ListFragmentComponent {
    fun inject(listFragment: ListFragment)

}