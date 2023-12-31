package com.example.todoapp.ui.view

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.FragmentItemBinding
import com.example.todoapp.ui.MainActivity
import com.example.todoapp.ui.viewModels.ItemViewModel
import com.example.todoapp.ui.viewModels.ListViewModel
import com.example.todoapp.utils.MaterialDatePickerHelper
import com.example.todoapp.utils.SnackbarHelper
import com.example.todoapp.utils.convertToImportance
import com.example.todoapp.utils.convertToLongDate
import com.example.todoapp.utils.convertToString
import com.example.todoapp.utils.convertToStringDate
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class ItemFragment : Fragment() {
    private lateinit var binding: FragmentItemBinding
    private lateinit var datePicker: MaterialDatePicker<Long>

    private val itemViewModel: ItemViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }
    private val listViewModel: ListViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity)
            .activityComponent
            .itemFragmentComponent()
            .create()
            .inject(this)
        binding = FragmentItemBinding.inflate(layoutInflater, container, false)
        setDatePicker()
        displaySnackbar()
        initData()
        setImportanceAdapter()
        binding.btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btnDelete.setOnClickListener {
            deleteItem()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            if (!binding.text.text.isNullOrEmpty()) {
                saveData()
                parentFragmentManager.popBackStack()
            } else {
                binding.text.error = getString(R.string.error_input_task_text)
            }
        }

        binding.btnSwitcher.setOnClickListener {
            updateDatePicker(binding.btnSwitcher.isChecked)
            if (!binding.btnSwitcher.isChecked) {
                binding.btnSwitcher.isChecked = false
                binding.date.text = ""
                binding.date.visibility = View.GONE
            }
        }
    }

    private fun deleteItem() {
        viewLifecycleOwner.lifecycleScope.launch {
            itemViewModel.getSelectedItem().collect { item ->
                if (item != null) {
                    itemViewModel.deleteItem(item)
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            itemViewModel.getSelectedItem().collect { item ->
                binding.apply {
                    text.setText(item?.text)
                    selectedImportance.setText(
                        item?.importance?.let {
                            item.importance.convertToString(requireContext())
                        } ?: getString(R.string.common_text)
                    )
                    date.visibility = if (item?.deadline != null) View.VISIBLE else View.GONE
                    btnSwitcher.isChecked = item?.deadline != null
                    date.text = item?.deadline?.let { item.deadline.convertToStringDate() }
                    btnDelete.isEnabled = item != null
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
        val importance =
            binding.selectedImportance.text.toString().convertToImportance(requireContext())
        val deadline = if (binding.date.visibility == View.GONE) null
        else binding.date.text.toString().convertToLongDate()
        val isDone = false
        val changeDate = System.currentTimeMillis()

        viewLifecycleOwner.lifecycleScope.launch {
            itemViewModel.getSelectedItem().collect { item ->
                val creationDate = System.currentTimeMillis()
                val id = item?.id ?: UUID.randomUUID().toString()
                val savingItem = ToDoItem(
                    id, text, importance, deadline, isDone, null,
                    item?.createdAt ?: creationDate, changeDate, getDeviceId()
                )
                if (item == null) {
                    itemViewModel.addItem(savingItem)
                } else {
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
        datePicker = MaterialDatePickerHelper.createDatePicker(
            requireContext(),
            getString(R.string.select_date_text)
        )
        datePicker.addOnPositiveButtonClickListener { selection ->
            val date = Date(selection)
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
            val formattedDate: String = dateFormat.format(date)
            binding.date.text = formattedDate
            binding.date.visibility = View.VISIBLE
        }
        datePicker.addOnCancelListener {
            binding.btnSwitcher.isChecked = false
            binding.date.text = ""
            binding.date.visibility = View.GONE
        }
        datePicker.addOnNegativeButtonClickListener {
            binding.btnSwitcher.isChecked = false
            binding.date.text = ""
            binding.date.visibility = View.GONE
        }
    }


    private fun setImportanceAdapter() {
        val items = Importance.values().map { importance ->
            importance.convertToString(requireContext())
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.importance.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

}