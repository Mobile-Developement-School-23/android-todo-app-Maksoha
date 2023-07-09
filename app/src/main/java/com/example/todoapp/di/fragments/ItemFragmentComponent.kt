package com.example.todoapp.di.fragments

import android.content.Context
import com.example.todoapp.di.ItemFragmentScope
import com.example.todoapp.ui.view.ItemFragment
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent
@ItemFragmentScope
interface ItemFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : ItemFragmentComponent
    }
    fun inject(itemFragment: ItemFragment)
}