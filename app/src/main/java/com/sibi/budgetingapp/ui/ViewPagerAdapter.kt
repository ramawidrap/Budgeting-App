package com.sibi.budgetingapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sibi.budgetingapp.ui.expense.ExpenseFragment
import com.sibi.budgetingapp.ui.income.IncomeFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> IncomeFragment();
            else -> ExpenseFragment();
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Income"
            else -> "Expense"
        }
    }
}