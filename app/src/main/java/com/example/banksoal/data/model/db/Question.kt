package com.example.banksoal.data.model.db

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = Course::class,
        parentColumns = ["id"],
        childColumns = ["course_id"]
    )],
    indices = [
        Index(value = ["id", "course_id"])
    ]
)
class Question {
    @Expose
    @PrimaryKey(autoGenerate = true)
//    @Index(value = ["id", "course_id"],  unique = true, name = "course_id")
    var id: Long = 0

    @Expose
    @SerializedName("img_url")
    @ColumnInfo(name = "img_url")
    var imgUrl: String? = null

    @Expose
    @SerializedName("text")
    @ColumnInfo(name = "text")
    @NonNull
    lateinit var text: String

    @Expose
    @SerializedName("course_id")
    @ColumnInfo(name = "course_id", index = true)
    var courseId: Long = 0
}