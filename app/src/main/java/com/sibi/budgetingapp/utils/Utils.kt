package com.sibi.budgetingapp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.util.*

fun setCalendar(v: EditText,context: Context) {
    val cldr: Calendar = Calendar.getInstance()
    val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
    val month: Int = cldr.get(Calendar.MONTH)
    val year: Int = cldr.get(Calendar.YEAR)
    // date picker dialog
    // date picker dialog
    val picker = DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            v.setText(
                dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
            )
        },
        year,
        month,
        day
    )
    picker.show()
}