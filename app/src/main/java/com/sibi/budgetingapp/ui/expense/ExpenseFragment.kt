package com.sibi.budgetingapp.ui.expense

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.source.viewmodel.MainActivityViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_expense.*
import javax.inject.Inject


class ExpenseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var expenseViewModel: MainActivityViewModel

    private var isInitUI = true;

    private lateinit var expenseAdapter: ExpenseExpandableAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expenseViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        expenseViewModel.getDataExpense().observe(this.viewLifecycleOwner, Observer { data ->
            Log.i("IncomeFragment", "$data");

            if (isInitUI) {
                expenseAdapter =
                    ExpenseExpandableAdapter(
                        this.requireContext(),
                        rv_expense,
                        expenseViewModel,
                        data,
                        data.keys.toList()
                    )
                rv_expense.setAdapter(expenseAdapter)

            } else {
                expenseAdapter.hashMap = data
                expenseAdapter.headerList = data.keys.toList()
                expenseAdapter.notifyDataSetChanged()
            }
            isInitUI = false


        })

        fab_expense.setOnClickListener {
            val intent = Intent(this.context, ExpenseEditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        expenseViewModel.dispose()
    }



}
