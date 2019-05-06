package com.dk.newssync.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dk.newssync.R
import com.dk.newssync.data.entity.Story
import com.dk.newssync.presentation.common.ItemClickListener
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.ui.base.BaseFragmentDagger
import com.dk.newssync.presentation.views.CustomDividerItemDecoration
import com.dk.newssync.presentation.common.color
import com.dk.newssync.presentation.common.visible
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 12/11/2018.
 **/

open class SearchFragment : BaseFragmentDagger() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val searchViewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    private val itemClickListener = object : ItemClickListener<Story> {
        override fun onItemClick(v: View, item: Story) {
            when(v.id) {
                R.id.favoriteButton -> searchViewModel.toggleFavorite(item)
                else -> startStory(v, item)
            }
        }
    }

    private val adapter = SearchAdapter(itemClickListener)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listTermRecyclerView.layoutManager = LinearLayoutManager(context)
        listTermRecyclerView.adapter = adapter
        listTermRecyclerView.itemAnimator?.changeDuration = 0
        listTermRecyclerView.addItemDecoration(CustomDividerItemDecoration(context, context?.color(R.color.gray), 0.5f))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(searchViewModel) {
            q = arguments?.getString(getString(R.string.entry))
            stories.observe(requireActivity(), Observer { state ->
                showState(state)
            })
            getStories()
        }
    }

    override fun getLayout(): Int = R.layout.fragment_search

    override fun showLoading() {
        retryPanel?.visible(false)
        emptyMessageText?.visible(false)
        listTermRecyclerView?.visible(false)
        loading?.visible(true)
    }

    protected fun showState(state: State<List<Story>>) {
        when(state) {
            is State.Loading -> showLoading()
            is State.Error -> showError(state.errorMessage)
            is State.Success -> {
                when(state.data.size) {
                    0 -> showEmpty()
                    else -> showStories(state.data)
                }
            }
        }
    }

    private fun showError(errorMessage: String?) {
        loading?.visible(false)
        emptyMessageText?.visible(false)
        listTermRecyclerView?.visible(false)
        retryPanel?.visible(true)
        errorMessageText?.text = errorMessage
    }

    private fun showEmpty() {
        retryPanel?.visible(false)
        loading?.visible(false)
        listTermRecyclerView?.visible(false)
        emptyMessageText?.visible(true)
    }

    private fun showStories(stories: List<Story>) {
        retryPanel?.visible(false)
        loading?.visible(false)
        emptyMessageText?.visible(false)
        listTermRecyclerView?.visible(true)
        adapter.submitList(stories)
    }

    private fun startStory(v: View, story: Story) {
        findNavController().navigate(R.id.storyFragment, Bundle().apply {
            putParcelable(v.context.getString(R.string.story), story)
        })
    }

}