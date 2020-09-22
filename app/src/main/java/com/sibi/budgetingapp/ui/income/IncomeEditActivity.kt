package com.sibi.budgetingapp.ui.income

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.viewmodel.IncomeViewModel
import com.sibi.budgetingapp.source.viewmodel.MainActivityViewModel
import com.sibi.budgetingapp.utils.setCalendar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_income_edit.*
import java.util.*
import javax.inject.Inject


class IncomeEditActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var incomeViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_edit)
        val income = intent.getParcelableExtra<Income>("income")
        incomeViewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        bindToView(income)




    }


    private fun bindToView(income: Income?) {
        if (income != null) {
            et_title.setText(income.title)
            et_amount.setText(income.amount.toString())
            et_datePicker.setText(income.date)
            et_deskripsi.setText(income.deskripsi)
        }

        et_datePicker.setOnClickListener {
            setCalendar(et_datePicker,this)
        }
        btn_simpan.setOnClickListener {
            saveData(income)
        }

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
                incomeViewModel.insertDataIncome(Income(0,title, amount, deskripsi, date))
            } else {
                income.id = income.id
                income.title = title
                income.date = date
                income.deskripsi = deskripsi
                income.amount = amount
                incomeViewModel.updateDataIncome(income)
            }
            finish()
        }
    }

}
