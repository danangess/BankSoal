package com.example.banksoal.data.model.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(
    tableName = "options",
    foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = ["id"],
        childColumns = ["question_id"]
    )]
)
class Option {
    @Expose
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Expose
    @SerializedName("is_correct")
    @ColumnInfo(name = "is_correct")
    var isCorrect: Boolean = false

    @Expose
    @SerializedName("text")
    @ColumnInfo(name = "text")
    @NonNull
    lateinit var text: String

    @Expose
    @SerializedName("question_id")
    @ColumnInfo(name = "question_id", index = true)
    var questionId: Long = 0
}