package com.dk.newssync.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 07/12/2018.
 **/

@Parcelize
@Entity(tableName = "entries", indices = [Index(value = ["name"], unique = true)])
data class Entry(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String? = null

): Parcelable