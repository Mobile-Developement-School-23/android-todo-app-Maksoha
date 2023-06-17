package com.example.todoapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.FragmentToDoItemBinding
import com.example.todoapp.ui.viewModels.ToDoItemViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
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
                binding.text.error = "Введите задачу"
            }
        }


        binding.btnDelete.setOnClickListener {
            itemViewModel.getItem().observe(viewLifecycleOwner) { item ->
                if (item != null) {
                    itemViewModel.deleteItem(item)
                    closeFragment()
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setData() {
        itemViewModel.getItem().observe(viewLifecycleOwner) { item ->
            if (item != null) {
                binding.text.setText(item.text)
                binding.selectedImportance.setText(
                    when (item.importance) {
                        Importance.LOW -> "Низкая"
                        Importance.COMMON -> "Обычная"
                        Importance.HIGH -> "Срочная"
                    }
                )
                binding.date.visibility = View.VISIBLE
                binding.btnSwitcher.isChecked = !item.deadline.isNullOrEmpty()
                binding.date.text = item.deadline
            }
            else {
                binding.btnDelete.isEnabled = false
            }
            setImportanceAdapter();

        }

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
        val importance =  when (binding.selectedImportance.text.toString()) {
            "Низкая" -> Importance.LOW
            "Обычная" -> Importance.COMMON
            "Срочная" -> Importance.HIGH
            else -> Importance.COMMON
        }
        val deadline = if (binding.date.visibility == View.GONE) null else binding.date.text
        val isDone = false
        val changeDate = getCurrentDate()

        itemViewModel.getItem().observe(viewLifecycleOwner) {item ->
            val creationDate = getCurrentDate()
            if (item == null) {
                val addingItem = ToDoItem(
                    id, text, importance,
                    deadline as String?, isDone, creationDate, null)
                itemViewModel.addItem(addingItem)
            }
            else {
                val savingItem = ToDoItem(
                    id, text, importance,
                    deadline as String?, isDone, item.creationDate, changeDate)
                itemViewModel.updateItem(item, savingItem)
            }
        }

    }

    private fun setDatePicker() {
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
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

    private fun getCurrentDate() : String {
        val currentDate = Calendar.getInstance().time

        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))

        return dateFormat.format(currentDate)
    }
    private fun resetDate() {
        binding.btnSwitcher.isChecked = false
        binding.date.text = ""
        binding.date.visibility = View.GONE
    }

    private fun setImportanceAdapter() {
        val items = Importance.values().map { importance ->
            when (importance) {
                Importance.LOW -> "Низкая"
                Importance.COMMON -> "Обычная"
                Importance.HIGH -> "Срочная"
            }
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.importance.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun closeFragment() {
        parentFragmentManager.popBackStack()
    }


}