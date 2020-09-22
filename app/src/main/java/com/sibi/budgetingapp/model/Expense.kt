package com.sibi.budgetingapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo
    var title: String,
    @ColumnInfo
     var amount: Int,
    @ColumnInfo
    var type: String,
     var deskripsi: String,
    @ColumnInfo
    var date: String
) : Parcelable