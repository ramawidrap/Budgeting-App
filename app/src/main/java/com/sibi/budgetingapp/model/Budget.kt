package com.sibi.budgetingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Budget(var shopping : Int,var entertainment : Int,var transportation : Int,var foods: Int, var other : Int) : Parcelable {

    fun getBudget(type:String) : Int {
        return when(type) {
            "Shopping" -> shopping
            "Entertainment" -> entertainment
            "Transportation" -> transportation
            "Foods" -> foods
            else -> other
        }
    }
}