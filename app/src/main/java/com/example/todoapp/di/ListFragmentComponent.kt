package com.example.todoapp.di

import dagger.Subcomponent
import javax.inject.Scope


@Scope
annotation class ListFragmentScope

@Subcomponent
@ListFragmentScope
class ListFragmentComponent {
}