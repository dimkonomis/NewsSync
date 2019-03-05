package com.dk.newssync.presentation.ui.entries

import androidx.recyclerview.widget.DiffUtil
import com.dk.newssync.data.entity.Entry

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

class EntriesDiffCallback : DiffUtil.ItemCallback<Entry>() {

    override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem.name == newItem.name
    }
}