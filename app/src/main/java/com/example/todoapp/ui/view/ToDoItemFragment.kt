package com.example.todoapp.ui.view

import android.os.Bundle
import android.provider.ContactsContract
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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID


class ToDoItemFragment : Fragment() {
    private lateinit var binding : FragmentToDoItemBinding
    private lateinit var datePicker : MaterialDatePicker<Long>
    private val itemViewModel: ToDoItemViewModel by lazy {
        (requireActivity() as MainActivity).itemViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentToDoItemBinding.inflate(layoutInflater, container, false)

        setDatePicker()

        setData()
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
            }
            else {
                binding.text.error = getString(R.string.error_input_task_text)
            }
        }


        binding.btnDelete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                itemViewModel.getSelectedItem().collect { item->
                    if (item != null) {
                        itemViewModel.deleteItem(item)
                        closeFragment()
                    }
                }
            }
        }


        return binding.root
    }



    private fun setData() {
        viewLifecycleOwner.lifecycleScope.launch {
            itemViewModel.getSelectedItem().collect { item ->
                if (item != null) {
                    binding.text.setText(item.text)
                    binding.selectedImportance.setText(
                        when (item.importance) {
                            Importance.LOW -> getString(R.string.low_text)
                            Importance.COMMON -> getString(R.string.common_text)
                            Importance.HIGH -> getString(R.string.high_text)
                        }
                    )
                    if (item.deadline != null) binding.date.visibility = View.VISIBLE
                    else binding.date.visibility = View.GONE
                    binding.btnSwitcher.isChecked = item.deadline != null
                    binding.date.text = item.deadline?.let { convertLongToStringDate(it) }
                    binding.btnDelete.isEnabled = true
                }
                else {
                    binding.btnDelete.isEnabled = false
                }
                setImportanceAdapter()
            }
        }
    }

    private fun convertLongToStringDate(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        return format.format(date)
    }

    private fun updateDatePicker(isChecked : Boolean) {

        if (isChecked) {
            datePicker.show(parentFragmentManager, datePicker.toString())
        }
        else {
            binding.date.text = ""
        }

    }


    private fun saveData() {
        val text = binding.text.editableText.toString()
        val importance = when (binding.selectedImportance.text.toString()) {
            getString(R.string.low_text) -> Importance.LOW
            getString(R.string.common_text) -> Importance.COMMON
            getString(R.string.high_text) -> Importance.HIGH
            else -> Importance.COMMON
        }
        val deadline = if (binding.date.visibility == View.GONE) null else convertStringToLongDate(binding.date.text.toString())
        val isDone = false
        val changeDate = System.currentTimeMillis()

        viewLifecycleOwner.lifecycleScope.launch {
            itemViewModel.getSelectedItem().collect { item ->
                val creationDate = System.currentTimeMillis()
                if (item == null) {
                    val id = UUID.randomUUID().toString()
                    val addingItem = ToDoItem(
                        id, text, importance,
                        deadline, isDone, null, creationDate, creationDate, getDeviceId())
                    itemViewModel.addItem(addingItem)
                }
                else {
                    val id = item.id
                    val savingItem = ToDoItem(
                        id, text, importance,
                        deadline, isDone, null, item.createdAt, changeDate, getDeviceId())
                    itemViewModel.updateItem(savingItem)
                }
            }
        }
    }

    private fun getDeviceId() : String {
        return Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
    }

    private fun convertStringToLongDate(dateString: String): Long {
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        val date = format.parse(dateString)
        return date?.time ?: 0L
    }


    private fun setDatePicker() {
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date_text))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())
                    .build()
            )
            .build()

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
            when (importance) {
                Importance.LOW -> getString(R.string.low_text)
                Importance.COMMON -> getString(R.string.common_text)
                Importance.HIGH -> getString(R.string.high_text)
            }
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.importance.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun closeFragment() {
        parentFragmentManager.popBackStack()
    }


}