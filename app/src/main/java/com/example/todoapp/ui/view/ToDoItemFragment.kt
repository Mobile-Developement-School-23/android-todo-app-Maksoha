package com.example.todoapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoListModel
import com.example.todoapp.databinding.FragmentToDoItemBinding
import com.example.todoapp.ui.viewModels.ToDoItemViewModel
import com.example.todoapp.ui.viewModels.ToDoListViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class ToDoItemFragment : Fragment() {
    private var cur_item : ToDoListModel? = null
    private lateinit var binding : FragmentToDoItemBinding
    private lateinit var datePicker : MaterialDatePicker<Long>
    private val viewModel: ToDoItemViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentToDoItemBinding.inflate(layoutInflater, container, false)
        subscribe()
        setImportanceAdapter();
        setDatePicker()
        binding.btnClose.setOnClickListener {
            closeFragment()
        }
        binding.btnSwitcher.setOnCheckedChangeListener { _, isChecked ->
            updateDatePicker(isChecked)

        }


        binding.btnSave.setOnClickListener {
            saveData()
            findNavController().navigate(R.id.action_addToDoItemFragment_to_myToDoListFragment)
            findNavController().popBackStack(findNavController().graph.startDestinationId, false);

        }

        binding.btnDelete.setOnClickListener {
            if (cur_item != null) {
                viewModel.remove(cur_item!!)
            }
            closeFragment()
        }

        setData()

        return binding.root
    }

    private fun subscribe() {
        viewModel.getItem().observe(viewLifecycleOwner) {item ->
            cur_item = item
        }
    }

    private fun setData() {
        if (cur_item != null) {
            binding.text.setText(cur_item!!.text)
            binding.selectedImportance.setText(cur_item!!.importance)
            binding.btnSwitcher.isChecked = true
            binding.date.visibility = View.VISIBLE
            binding.date.text = cur_item!!.deadline
        }
        else {
            binding.btnDelete.isEnabled = false
        }
    }

    private fun updateDatePicker(isChecked : Boolean) {

        if (isChecked) {
            if (cur_item == null) {
                datePicker.show(parentFragmentManager, datePicker.toString())
            }
        }
        else {
            binding.date.text = ""
        }

    }


    private fun saveData() {
        val id = "item_${viewModel.getItemsSize() + 1}"
        val text = binding.text.editableText.toString()
        val importance = binding.selectedImportance.text.toString()
        val deadline = if (binding.date.visibility == View.GONE) null else binding.date.text
        val isDone = false
        val creationDate = getCurrentDate()
        val changeDate = getCurrentDate()
        val addingItem = ToDoListModel(
            id, text, importance,
            deadline as String?, isDone, creationDate, changeDate)
        if (cur_item == null) {
            viewModel.addItem(addingItem)
        }
        else {
            viewModel.updateItem(cur_item!!, addingItem)
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
            val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale("ru"))
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

        val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())

        return dateFormat.format(currentDate)
    }
    private fun resetDate() {
        binding.btnSwitcher.isChecked = false
        binding.date.text = ""
        binding.date.visibility = View.GONE
    }

    private fun setImportanceAdapter() {
        val items = listOf("Низкая", "Обычная", "Срочная")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.importance.editText as? AutoCompleteTextView)?.setAdapter(adapter)    }

    private fun closeFragment() {
        parentFragmentManager.popBackStack()
    }


}