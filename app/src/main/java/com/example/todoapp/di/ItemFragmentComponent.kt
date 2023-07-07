package com.example.todoapp.di

import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class ItemFragmentScope


@Subcomponent
@ItemFragmentScope
class ItemFragmentComponent {
}