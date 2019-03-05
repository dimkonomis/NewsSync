package com.dk.newssync.presentation.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.dk.newssync.data.entity.Story

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 12/11/2018.
 **/

class SearchDiffCallback : DiffUtil.ItemCallback<Story>() {

    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.favorite == newItem.favorite
    }
}