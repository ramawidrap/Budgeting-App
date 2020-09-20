package com.sibi.budgetingapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "income")
data class Income(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo
    override var title:String,
    @ColumnInfo
    override var amount: Int,
    @ColumnInfo
    override var deskripsi: String,
    @ColumnInfo
    override var date: String
) : BaseModel, Parcelable