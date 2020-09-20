package com.sibi.budgetingapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo
    override var title: String,
    @ColumnInfo
    override var amount: String,
    @ColumnInfo
    override var deskripsi: String,
    @ColumnInfo
    override var date: String
) : BaseModel