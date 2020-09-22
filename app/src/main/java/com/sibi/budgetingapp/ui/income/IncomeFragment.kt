package com.sibi.budgetingapp.ui.income

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.source.viewmodel.IncomeViewModel
import com.sibi.budgetingapp.source.viewmodel.MainActivityViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_income.*
import javax.inject.Inject

class IncomeFragment : DaggerFragment() {

    @Inject
    lateinit var vieweModelFactory: ViewModelProvider.Factory

    private lateinit var incomeViewModel: MainActivityViewModel

    private lateinit var incomeAdapter: IncomeExpandableAdapter

    private var isInitUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        incomeViewModel =
            ViewModelProvider(this, vieweModelFactory).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        incomeViewModel.getTotalIncome().observe(this.viewLifecycleOwner, Observer {
            println("HAHAHAHAHAH KE UBAH")
        })
        incomeViewModel.getDataIncome().observe(this.viewLifecycleOwner, Observer { data ->
            if (isInitUI) {
                incomeAdapter =
                    IncomeExpandableAdapter(
                        this.requireContext(),
                        rv_income,
                        incomeViewModel,
                        data,
                        data.keys.toList()
                    )
                rv_income.setAdapter(incomeAdapter)

            } else {
                incomeAdapter.hashMap = data
                incomeAdapter.headerList = data.keys.toList()
                incomeAdapter.notifyDataSetChanged()
            }
            isInitUI = false
        })

        fab_income.setOnClickListener {
            val intent = Intent(
                this.context,
                IncomeEditActivity::class.java
            )
            startActivity(intent);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        incomeViewModel.dispose()
    }


}
