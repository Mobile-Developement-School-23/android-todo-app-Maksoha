package com.example.todoapp.ui.screens.taskEdit_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.compose.AppTheme
import com.example.todoapp.databinding.FragmentItemBinding
import com.example.todoapp.ui.MainActivity
import com.example.todoapp.ui.model.SettingAction
import com.example.todoapp.ui.model.TaskEditAction
import com.example.todoapp.ui.viewModels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskEditFragment : Fragment() {
    private lateinit var binding: FragmentItemBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: TaskEditViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as MainActivity)
            .activityComponent
            .taskEditFragmentComponent()
            .create()
            .inject(this)
        binding = FragmentItemBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            binding.composeView.setContent {
                setContent {
                    AppTheme() {
                        TaskEditScreen(viewModel)
                    }
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.action.collect { event ->
                when (event) {
                    TaskEditAction.Navigate -> parentFragmentManager.popBackStack()
                    else -> {}
                }
            }
        }
    }
}