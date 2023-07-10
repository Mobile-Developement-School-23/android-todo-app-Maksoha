package com.example.todoapp.di.fragments

import com.example.todoapp.di.ItemFragmentScope
import com.example.todoapp.ui.view.ItemFragment
import dagger.Subcomponent


@Subcomponent(modules = [ListViewModelModule::class])
@ItemFragmentScope
interface ItemFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ItemFragmentComponent
    }

    fun inject(itemFragment: ItemFragment)
}