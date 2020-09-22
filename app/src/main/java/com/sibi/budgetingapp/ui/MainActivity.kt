package com.sibi.budgetingapp.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.source.viewmodel.MainActivityViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private var totalIncome = 0

    private var totalExpense = 0

    private var incomeThisMonth = 0
    private var expenseThisMonth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.mainColor)))
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        val sharedPref = getSharedPreferences("BUDGET", Context.MODE_PRIVATE)

        mainActivityViewModel.getTotalExpense().observe(this, Observer { total ->
            totalExpense = total
            tv_valueBalance.setText("Rp${(totalIncome - totalExpense)}")
            tv_totalExpense.setText("Total Expense:\n Rp${totalExpense}")
        })
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)

        button_budget.setOnClickListener {
            val intent = Intent(this, DetailBudgetActivity::class.java)
            startActivity(intent)
        }

        mainActivityViewModel.getTotalIncome().observe(this, Observer { total ->
            totalIncome = total
            tv_valueBalance.setText("Rp${(totalIncome - totalExpense)}")
            tv_totalIncome.setText("Total Income:\n Rp${totalIncome}")
        })

        mainActivityViewModel.getDataIncomeThisMonth().observe(this, Observer { income ->
            println("income this month ${income.second}")
            incomeThisMonth = income.second
            updateSaving()

        })

        mainActivityViewModel.getDataExpenseThisMonth().observe(this, Observer { expense ->
            println("expense this month ${expense.second}")

            expenseThisMonth = expense.second
            updateSaving()
        })

        mainActivityViewModel.getUpdatedDataExpense().removeObservers(this)
        mainActivityViewModel.getUpdatedDataExpense().observe(this, Observer { updatedData ->
            mainActivityViewModel.getAllTypeExpense().removeObservers(this)
            mainActivityViewModel.getAllTypeExpense().observe(this, Observer { map ->
                val key = "notif${updatedData.category}"
                val budget = sharedPref.getInt(key, 0)
                val total = map[updatedData.category] ?: 0

                //add
                if (updatedData.type == 0) {
                    if (budget != 0) {
                        if (total >= budget) {
                            sharedPref.edit().putInt(key, budget + 100).apply()
                            showAlert(updatedData.category)

                        }
                    }
                }

            })

        })

    }


    private fun updateSaving() {
        if (incomeThisMonth == 0) {
            tv_totalSaving.setText("0%")
        } else {
            val result = (((incomeThisMonth - expenseThisMonth).toDouble() / incomeThisMonth) * 100)
            val rounded = String.format("%.1f", result).toDouble().toString()
            tv_totalSaving.setText("$rounded%")
        }
    }

    fun showAlert(type: String) {
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setMessage("Budget $type anda sudah melewati batas")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityViewModel.dispose()
    }

    override fun onStop() {
        super.onStop()
    }
}
