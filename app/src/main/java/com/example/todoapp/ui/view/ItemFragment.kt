package com.example.todoapp.ui.view

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.FragmentToDoItemBinding
import com.example.todoapp.ui.viewModels.ToDoItemViewModel
import com.example.todoapp.ui.viewModels.ToDoListViewModel
import com.example.todoapp.utils.Converters
import com.example.todoapp.utils.MaterialDatePickerHelper
import com.example.todoapp.utils.SnackbarHelper
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID


class ItemFragment : Fragment() {
    private val converters = Converters()
    private lateinit var binding: FragmentToDoItemBinding
    private lateinit var datePicker: MaterialDatePicker<Long>
    private val itemViewModel: ToDoItemViewModel by lazy {
        (requireActivity() as MainActivity).itemViewModel
    }
    private val listViewModel: ToDoListViewModel by lazy {
        (requireActivity() as MainActivity).listViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoItemBinding.inflate(layoutInflater, container, false)
        setDatePicker()
        displaySnackbar()
        initData()
        binding.btnClose.setOnClickListener {
            closeFragment()
        }
        binding.btnSwitcher.setOnClickListener {
            updateDatePicker(binding.btnSwitcher.isChecked)
            if (!binding.btnSwitcher.isChecked) {
                resetDate()
            }
        }
        binding.btnSave.setOnClickListener {
            if (!binding.text.text.isNullOrEmpty()) {
                saveData()
                closeFragment()
            } else {
                binding.text.error = getString(R.string.error_input_task_text)
            }
        }
        binding.btnDelete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                itemViewModel.getSelectedItem().collect { item ->
                    if (item != null) {
                        itemViewModel.deleteItem(item)
                        closeFragment()
                    }
                }
            }
        }
        return binding.root
    }


    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            itemViewModel.getSelectedItem().collect { item ->
                binding.apply {
                    text.setText(item?.text)
                    selectedImportance.setText(
                        item?.importance?.let {
                            converters.convertImportanceToString(it, requireContext())
                        } ?: getString(R.string.common_text)
                    )
                    date.visibility = if (item?.deadline != null) View.VISIBLE else View.GONE
                    btnSwitcher.isChecked = item?.deadline != null
                    date.text = item?.deadline?.let { converters.convertLongToStringDate(it) }
                    btnDelete.isEnabled = item != null
                    setImportanceAdapter()
                }
            }
        }

    }

    private fun displaySnackbar() {
        viewLifecycleOwner.lifecycleScope.launch {
            itemViewModel.getErrorState().collect { errorState ->
                SnackbarHelper(
                    requireActivity().findViewById(R.id.activityMain),
                    errorState,
                    listViewModel.refreshData()
                ).showSnackbarWithAction()
            }
        }
    }

    private fun updateDatePicker(isChecked: Boolean) {
        if (isChecked) {
            datePicker.show(parentFragmentManager, datePicker.toString())
        } else {
            binding.date.text = ""
        }
    }

    private fun saveData() {
        val text = binding.text.editableText.toString()
        val importance = converters.convertStringToImportance(
            binding.selectedImportance.text.toString(),
            requireContext()
        )
        val deadline = if (binding.date.visibility == View.GONE) null
        else converters.convertStringToLongDate(binding.date.text.toString())
        val isDone = false
        val changeDate = System.currentTimeMillis()

        viewLifecycleOwner.lifecycleScope.launch {
            itemViewModel.getSelectedItem().collect { item ->
                val creationDate = System.currentTimeMillis()
                if (item == null) {
                    val id = UUID.randomUUID().toString()
                    val addingItem = ToDoItem(
                        id, text, importance,
                        deadline, isDone, null, creationDate, creationDate, getDeviceId()
                    )
                    itemViewModel.addItem(addingItem)
                } else {
                    val id = item.id
                    val savingItem = ToDoItem(
                        id, text, importance,
                        deadline, isDone, null, item.createdAt, changeDate, getDeviceId()
                    )
                    itemViewModel.updateItem(savingItem)
                }
            }
        }
    }

    private fun getDeviceId(): String {
        return Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    private fun setDatePicker() {
        datePicker = MaterialDatePickerHelper.createDatePicker(requireContext(), getString(R.string.select_date_text) )
        datePicker.addOnPositiveButtonClickListener { selection ->
            val date = Date(selection)
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
            val formattedDate: String = dateFormat.format(date)
            binding.date.text = formattedDate
            binding.date.visibility = View.VISIBLE
        }
        datePicker.addOnCancelListener {
            resetDate()
        }
        datePicker.addOnNegativeButtonClickListener {
            resetDate()
        }
    }

    private fun resetDate() {
        binding.btnSwitcher.isChecked = false
        binding.date.text = ""
        binding.date.visibility = View.GONE
    }

    private fun setImportanceAdapter() {
        val items = Importance.values().map { importance ->
            converters.convertImportanceToString(importance, requireContext())
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.importance.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun closeFragment() {
        parentFragmentManager.popBackStack()
    }
}