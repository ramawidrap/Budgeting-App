package com.sibi.budgetingapp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
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

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(str:String) : String {
    val formatter = SimpleDateFormat("dd/MM/yyyy");
    val dateTime = formatter.parse(str)
    println("cek datetime $dateTime")
    return SimpleDateFormat("MMMM yyyy").format(dateTime)
}