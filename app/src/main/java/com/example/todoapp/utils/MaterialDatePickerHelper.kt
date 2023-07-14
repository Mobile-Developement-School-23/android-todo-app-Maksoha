package com.example.todoapp.utils

import android.content.Context
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker

class MaterialDatePickerHelper {
    companion object {
        fun createDatePicker(context: Context, title: String): MaterialDatePicker<Long> {
            val builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now())
                        .build()
                )
            return builder.build()
        }
    }
}
