package com.dk.newssync.presentation.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dk.newssync.R
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.mapper.timeAgo
import com.dk.newssync.presentation.common.ItemClickListener
import com.dk.newssync.presentation.common.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search.*

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 12/11/2018.
 **/

class SearchAdapter(private val itemClickListener: ItemClickListener<Story>) : ListAdapter<Story, SearchAdapter.ViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(story: Story) {
            coverImageView.loadImage(story.image)
            headLineText.text = story.title
            dateText.text = story.timeAgo()
            favoriteButton.isChecked = story.favorite
            favoriteButton.setOnClickListener { v -> itemClickListener.onItemClick(v, story) }
            itemView.setOnClickListener { v -> itemClickListener.onItemClick(v, story) }
        }

    }
}