package com.example.banksoal.data.model.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "users")
class User {
    @Expose
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Expose
    @SerializedName("username")
    @ColumnInfo(name = "username")
    lateinit var username: String

    @Expose
    @ColumnInfo(name = "password")
    lateinit var password: String

    @Expose
    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    @NonNull
    lateinit var firstName: String

    @Expose
    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    @NonNull
    lateinit var lastName: String
}