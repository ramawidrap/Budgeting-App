package com.sibi.budgetingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sibi.budgetingapp.ui.ViewPagerAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
}
