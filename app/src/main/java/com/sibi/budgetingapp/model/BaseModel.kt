package com.sibi.budgetingapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
interface BaseModel {
    var title:String
    var amount: Int
    var deskripsi: String
    var date: String
}