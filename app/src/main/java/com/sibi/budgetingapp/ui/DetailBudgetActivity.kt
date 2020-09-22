package com.sibi.budgetingapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.Budget
import com.sibi.budgetingapp.source.viewmodel.DetailBudgetViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail_buget.*
import javax.inject.Inject

class DetailBudgetActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var detailBudgetViewModel: DetailBudgetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_buget)
        detailBudgetViewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailBudgetViewModel::class.java)

        val sharedPref = getSharedPreferences("BUDGET",Context.MODE_PRIVATE)
        val budgetStr = sharedPref.getString("budget", "")
        println(budgetStr)
        btn_goToBudgetActivity.setOnClickListener {
            val intent = Intent(this,BudgetActivity::class.java)
            startActivity(intent)
        }
        if (budgetStr!= "") {
            val budget = Gson().fromJson(budgetStr, Budget::class.java)
            bindToView(budget)

        }

    }

    fun bindToView(budget: Budget) {
        pb_shopping.max = budget.shopping
        pb_entertainment.max = budget.entertainment
        pb_transportation.max = budget.transportation
        pb_foods.max = budget.foods
        pb_others.max = budget.other

        detailBudgetViewModel.getExpenseAllType().observe(this, Observer {
            pb_shopping.progress = it.get("Shopping") ?: 0
            pb_entertainment.progress = it.get("Entertainment") ?: 0
            pb_transportation.progress = it.get("Transportation") ?: 0
            pb_foods.progress = it.get("Foods") ?: 0
            pb_others.progress = it.get("Others") ?: 0
        })


    }
}
