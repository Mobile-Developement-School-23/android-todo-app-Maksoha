package com.example.todoapp.ui.viewModels


import android.opengl.Visibility
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoItemsRepository
import kotlinx.coroutines.launch
import java.text.FieldPosition


class ToDoListViewModel(private val repository: ToDoItemsRepository) : ViewModel() {
    private var allItems: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    private var visibility : MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        viewModelScope.launch {
            repository.getItems().collect { items ->
                    allItems.value = items
                }
        }
    }



    fun deleteItem(item: ToDoItem) {
        repository.deleteItem(item)
    }

    fun getItems(): LiveData<List<ToDoItem>> {
        return allItems
    }

    fun deleteItemByPosition(position: Int) {
        repository.deleteItemByPosition(position)
    }


    fun updateItem(selectItem: ToDoItem, newItem: ToDoItem) {
        repository.updateItem(selectItem, newItem)
    }

    fun changeStateVisibility() {
        visibility.value = !visibility.value!!
    }

    fun getStateVisibility() : LiveData<Boolean> {
        return visibility
    }

    fun hide(): List<ToDoItem> {
        return allItems.value!!.filter { !it.isDone }
    }

    fun moveItem(fromIndex : Int, toIndex : Int) {
        repository.moveItem(fromIndex, toIndex)
    }


}
class ToDoListViewModelFactory(private val repository: ToDoItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
