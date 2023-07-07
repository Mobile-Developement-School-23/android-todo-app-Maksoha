package com.example.todoapp.di.fragments

import com.example.todoapp.di.ItemFragmentScope
import com.example.todoapp.ui.view.ItemFragment
import dagger.Subcomponent


@Subcomponent
@ItemFragmentScope
interface ItemFragmentComponent {
    fun inject(itemFragment: ItemFragment)
}