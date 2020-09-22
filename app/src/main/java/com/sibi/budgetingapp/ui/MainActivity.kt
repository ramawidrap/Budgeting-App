package com.sibi.budgetingapp.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.Budget
import com.sibi.budgetingapp.source.viewmodel.MainActivityViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private lateinit var mainActivityViewModel : MainActivityViewModel

    private var totalIncome = 0

    private var totalExpense = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = getSharedPreferences("BUDGET", Context.MODE_PRIVATE)
        mainActivityViewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)
//        mainActivityViewModel.setData(this)
        mainActivityViewModel.getTotalExpense().observe(this, Observer { total ->
            totalExpense = total
            tv_valueBalance.setText((totalIncome - totalExpense).toString())
            tv_totalExpense.setText(total.toString())
        })
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)


        mainActivityViewModel.getUpdatedData().observe(this, Observer { updatedData ->
            println("doing update")
            val keyNotif = "notif${updatedData.first}"
            val valueNotif = sharedPref.getInt(keyNotif,-1)
            mainActivityViewModel.getAllType().observe(this, Observer {map ->
                println("update data all type $map")
                val key ="notif${updatedData.first}"
                val budget = sharedPref.getInt(key,0)
                val total = map[updatedData.first] ?: 0

                //add
                if(budget!= 0) {
                    if(total % budget >= 0 && total.div(budget) != 0) {
                        sharedPref.edit().putInt(key, 100 - (total%budget)%100).apply()
                        showAlert(updatedData.first)
                    }
                    else {
                        sharedPref.edit().putInt(key, budget - updatedData.second).apply()
                    }
                }


                })

//                println("get alltype tiap update bro")
//                Log.d(com.sibi.budgetingapp.source.repository.TAG, "success updated data ${updatedData}")
//
//
//                Log.d(com.sibi.budgetingapp.source.repository.TAG, "value notif $valueNotif")
//                if(valueNotif != -1) {
//                    if(valueNotif - updatedData.second <=0) {
//                        println("budget abis $valueNotif")
//                        sharedPref.edit().putInt(keyNotif, 100 - (updatedData.second-valueNotif)%100).apply()
//                        showAlert(updatedData.first)
//                    }
//                    else {
//                        val budgetStr = sharedPref.getString("budget", "")
//                        println("budget blom abis")
//                        val budget = Gson().fromJson(budgetStr,Budget::class.java)
//                        if(valueNotif - updatedData.second <= budget.getBudget(updatedData.first)) {
//                            sharedPref.edit().putInt(keyNotif,valueNotif - updatedData.second).apply()
//                        }
//                        else {
//                            sharedPref.edit().putInt(keyNotif,budget.getBudget(updatedData.first)).apply()
//                        }
//
//                    }
//                }
//            })

        })



        mainActivityViewModel.getTotalIncome().observe(this, Observer {total->
            totalIncome = total
            tv_valueBalance.setText((totalIncome - totalExpense).toString())
            tv_totalIncome.setText(total.toString())
        })

        button_budget.setOnClickListener {
            val intent = Intent(this,DetailBudgetActivity::class.java)
            startActivity(intent)
        }
    }

    fun showAlert(type:String) {
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setMessage("Budget $type anda sudah melewati batas")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }
}
