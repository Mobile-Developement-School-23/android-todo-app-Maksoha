package com.example.todoapp.ui.view

import android.os.Bundle
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
import java.util.Calendar
import java.util.Date
import java.util.Locale


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
            lifecycleScope.launch {
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
        lifecycleScope.launch {
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
                    binding.date.visibility = View.VISIBLE
                    binding.btnSwitcher.isChecked = item.deadline != null
                    binding.date.text = item.deadline?.let { convertDateToString(it) }
                }
                else {
                    binding.btnDelete.isEnabled = false
                }
                setImportanceAdapter()
            }
        }
    }

    private fun convertDateToString(date: Date): String {
        val sdf = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
        return sdf.format(date)
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
        val id = "item_${itemViewModel.getItemsSize() + 1}"
        val text = binding.text.editableText.toString()
        val importance = when (binding.selectedImportance.text.toString()) {
            getString(R.string.low_text) -> Importance.LOW
            getString(R.string.common_text) -> Importance.COMMON
            getString(R.string.high_text) -> Importance.HIGH
            else -> Importance.COMMON
        }
        val deadline = if (binding.date.visibility == View.GONE) null else convertStringToDate(binding.date.text.toString())
        val isDone = false
        val changeDate = getCurrentDate()

        lifecycleScope.launch {
            itemViewModel.getSelectedItem().collect {item ->
                val creationDate = getCurrentDate()
                if (item == null) {
                    val addingItem = ToDoItem(
                        id, text, importance,
                        deadline, isDone, creationDate, null)
                    itemViewModel.addItem(-1, addingItem)
                }
                else {
                    val savingItem = ToDoItem(
                        id, text, importance,
                        deadline, isDone, item.creationDate, changeDate)
                    itemViewModel.updateItem(item, savingItem)
                }
            }
        }
    }

    private fun convertStringToDate(date : String) : Date? {
        val sdf = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
        return sdf.parse(date)
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

    private fun getCurrentDate(): Date {
        return Calendar.getInstance().time
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