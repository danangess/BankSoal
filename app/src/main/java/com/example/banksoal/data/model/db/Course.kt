package com.example.banksoal.data.model.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "courses")
class Course {
    @Expose
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "name")
    lateinit var name: String

    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description: String? = null

    @Expose
    @SerializedName("competences")
    @ColumnInfo(name = "competences")
    lateinit var competences: String
}