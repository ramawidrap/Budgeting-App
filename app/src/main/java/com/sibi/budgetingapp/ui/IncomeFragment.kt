package com.sibi.budgetingapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.sibi.budgetingapp.R
import com.sibi.budgetingapp.model.Income
import com.sibi.budgetingapp.source.IncomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_income.*
import javax.inject.Inject
import kotlin.math.log

class IncomeFragment : DaggerFragment() {

    @Inject
    lateinit var vieweModelFactory: ViewModelProvider.Factory

    private lateinit var incomeViewModel : IncomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        incomeViewModel = ViewModelProvider(this,vieweModelFactory).get(IncomeViewModel::class.java);
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
        incomeViewModel.insertData(Income(0,"Buat Makan",5000,"Makan nasi goreng","18/10/2020"));
        incomeViewModel.getData().observe(this.viewLifecycleOwner, Observer { data ->
            Log.i("IncomeFragment","$data");
            val header = data.keys.toList()
            val incomeAdapter = ExpandableAdapter(this.requireContext(),incomeViewModel,data,header)
            rv_income.setAdapter(incomeAdapter)
        })

        fab_income.setOnClickListener {
            val intent = Intent(this.context,EditActivity::class.java)
            startActivity(intent);
        }
    }


}
