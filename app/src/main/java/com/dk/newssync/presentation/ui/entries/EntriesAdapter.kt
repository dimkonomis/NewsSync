package com.dk.newssync.presentation.ui.entries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dk.newssync.R
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.presentation.common.ItemClickListener
import com.dk.newssync.presentation.common.color
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_entry.*

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

class EntriesAdapter(private val itemClickListener: ItemClickListener<Entry>) : ListAdapter<Entry, EntriesAdapter.ViewHolder>(EntriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_entry, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(entry: Entry, position: Int) {
            entryText.setBackgroundColor(entryText.context.color(if(position == 0) R.color.colorPrimary else R.color.white))
            entryText.setTextColor(entryText.context.color(if(position == 0) R.color.white else android.R.color.black))
            entryText.setOnClickListener { v -> itemClickListener.onItemClick(v, getItem(adapterPosition)) }
            entryText.text = entry.name
            entryText.isEnabled = position != 0
        }

    }
}