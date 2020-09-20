package com.sibi.budgetingapp.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.IncomeViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*
import javax.inject.Inject


class EditActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var incomeViewModel: IncomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val income = intent.getParcelableExtra<Income>("income")
        bindToView(income)
        incomeViewModel = ViewModelProvider(this, viewModelFactory).get(IncomeViewModel::class.java)


        et_datePicker.setOnClickListener {
            setCalendar(et_datePicker)
        }
        btn_simpan.setOnClickListener {
            saveData(income)
        }
    }


    private fun bindToView(income: Income?) {
        if (income != null) {
            et_title.setText(income.title)
            et_amount.setText(income.amount)
            et_datePicker.setText(income.date)
            et_deskripsi.setText(income.deskripsi)
        }

    }

    private fun setCalendar(v: EditText) {
        val cldr: Calendar = Calendar.getInstance()
        val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
        val month: Int = cldr.get(Calendar.MONTH)
        val year: Int = cldr.get(Calendar.YEAR)
        // date picker dialog
        // date picker dialog
        val picker = DatePickerDialog(
            this,
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
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

    private fun saveData(income: Income?) {
        if (et_title.text.isNullOrEmpty()) {
            et_title.error = "Title is required"
        }
        if (et_amount.text.isNullOrEmpty()) {
            et_amount.error = "Amount is required"
        }

        if (et_datePicker.text.isNullOrEmpty()) {
            et_datePicker.error = "Date is required"
        }

        if (!et_title.text.isNullOrEmpty() && !et_amount.text.isNullOrEmpty() && !et_datePicker.text.isNullOrEmpty()) {
            val title = et_title.text.toString()
            val amount = et_amount.text.toString().toInt()
            val date = et_datePicker.text.toString()
            val deskripsi = et_deskripsi.text.toString()
            if (income == null) {
                incomeViewModel.insertData(Income(0,title, amount, deskripsi, date))
            } else {
                income.id = income.id
                income.title = title
                income.date = date
                income.deskripsi = deskripsi
                income.amount = amount
                incomeViewModel.updateData(income)
            }
            finish()
        }
    }

}
