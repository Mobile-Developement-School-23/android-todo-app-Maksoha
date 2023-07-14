package com.example.todoapp.ui.screens.setting_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.compose.AppTheme
import com.example.todoapp.databinding.FragmentSettingsBinding
import com.example.todoapp.ui.MainActivity
import com.example.todoapp.ui.model.SettingAction
import com.example.todoapp.ui.viewModels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SettingViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity)
            .activityComponent
            .settingFragmentComponent()
            .create()
            .inject(this)
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.root.apply {
            binding.root.setContent {
                setContent {
                    AppTheme {
                        SettingScreen(viewModel)
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
                    SettingAction.Navigate -> parentFragmentManager.popBackStack()
                    else -> {}
                }
            }
        }

    }

}