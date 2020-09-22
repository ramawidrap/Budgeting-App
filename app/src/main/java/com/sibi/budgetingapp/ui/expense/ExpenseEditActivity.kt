package com.sibi.budgetingapp.ui.expense

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.Expense
import com.sibi.budgetingapp.source.viewmodel.MainActivityViewModel
import com.sibi.budgetingapp.utils.setCalendar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_expense_edit.*
import javax.inject.Inject


class ExpenseEditActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var expenseViewModel: MainActivityViewModel

    private var selectedType = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_edit)

        expenseViewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)
        val expense = intent.getParcelableExtra<Expense>("expense")
        bindToView(expense)

    }

    private fun saveData(expense: Expense?) {
        val expenseType = resources.getStringArray(R.array.ExpenseType)

        if (et_title_expense.text.isNullOrEmpty()) {
            et_title_expense.error = "Title is required"
        }
        if (et_amount_expense.text.isNullOrEmpty()) {
            et_amount_expense.error = "Amount is required"
        }

        if (et_datePicker_expense.text.isNullOrEmpty()) {
            et_datePicker_expense.error = "Date is required"
        }

        if (!et_title_expense.text.isNullOrEmpty() && !et_amount_expense.text.isNullOrEmpty() && !et_datePicker_expense.text.isNullOrEmpty()) {
            val title = et_title_expense.text.toString()
            val amount = et_amount_expense.text.toString().toInt()
            val date = et_datePicker_expense.text.toString()
            val deskripsi = et_deskripsi_expense.text.toString()
            val _selectedExpense = expenseType[selectedType]

            if (expense == null) {
                expenseViewModel.insertDataExpense(
                    Expense(
                        0,
                        title,
                        amount,
                        _selectedExpense,
                        deskripsi,
                        date
                    )
                )
            } else {
                expense.id = expense.id
                expense.title = title
                expense.date = date
                expense.deskripsi = deskripsi
                expense.type = expenseType[selectedType]
                expense.amount = amount
                expenseViewModel.updateDataExpense(expense)
            }
            finish()
        }
    }



    private fun bindToView(expense: Expense?) {
        if (expense != null) {
            et_title_expense.setText(expense.title)
            et_amount_expense.setText(expense.amount.toString())
            et_datePicker_expense.setText(expense.date)
            et_deskripsi_expense.setText(expense.deskripsi)
            spinner_type.setSelection(selectedType)
        }

        val expenseType = resources.getStringArray(R.array.ExpenseType)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, expenseType)
        spinner_type.adapter = adapter
        spinner_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedType = position
            }

        }
        btn_simpan_expense.setOnClickListener {
            saveData(expense)
        }

        et_datePicker_expense.setOnClickListener {
            setCalendar(et_datePicker_expense,this)
        }


    }


}
