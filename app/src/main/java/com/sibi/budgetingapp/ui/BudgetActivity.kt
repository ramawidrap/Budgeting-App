package com.sibi.budgetingapp.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.Budget
import kotlinx.android.synthetic.main.activity_budget.*


class BudgetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        val sharedPref = getSharedPreferences("BUDGET", Context.MODE_PRIVATE)

        btn_setBudget.setOnClickListener {
            val notif = HashMap<String, Int>()
            var budgetShopping = et_shopping.text.toString()
            var budgetEntertainment = et_entertainment.text.toString()
            var budgetTransportation = et_transportation.text.toString()
            var budgetFoods = et_foods.text.toString()
            var budgetOthers = et_others.text.toString()

            if (budgetShopping == "") {
                budgetShopping = "0"
            } else {
                sharedPref.edit().putInt("notifShopping", budgetShopping.toInt()).apply()
            }
            if (budgetEntertainment == "") {
                budgetEntertainment = "0"
            } else {
                sharedPref.edit().putInt("notifEntertainment", budgetEntertainment.toInt()).apply()
            }
            if (budgetTransportation == "") {
                budgetTransportation = "0"
            } else {
                sharedPref.edit().putInt("notifTransportation", budgetTransportation.toInt())
                    .apply()

            }
            if (budgetFoods == "") {
                budgetFoods = "0"
            } else {
                sharedPref.edit().putInt("notifFoods", budgetFoods.toInt()).apply()
            }
            if (budgetOthers == "") {
                budgetOthers = "0"
            } else {
                sharedPref.edit().putInt("notifOthers", budgetOthers.toInt()).apply()
            }


            val budget = Budget(
                budgetShopping.toInt(),
                budgetEntertainment.toInt(),
                budgetTransportation.toInt(),
                budgetFoods.toInt(),
                budgetOthers.toInt()
            )


            val jsonBudget = Gson().toJson(budget)
            sharedPref.edit().putString("budget", jsonBudget).apply()
            setResult(Activity.RESULT_OK)
            finish()

        }

    }
}