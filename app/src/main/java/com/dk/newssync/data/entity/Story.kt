package com.dk.newssync.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Parcelize
@Entity(
    tableName = "stories",
    foreignKeys = [ForeignKey(
        entity = Entry::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("entryId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["entryId"]), Index(value = ["title"], unique = true)]
)
data class Story(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val entryId: Long = 0,
    val source: String? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val image: String? = null,
    val favorite: Boolean = false,
    val timestamp: Long? = null
) : Parcelable