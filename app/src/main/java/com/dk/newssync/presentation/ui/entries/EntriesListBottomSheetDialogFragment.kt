package com.dk.newssync.presentation.ui.entries

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dk.newssync.R
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.presentation.ui.base.BaseBottomSheetDialogFragment
import com.dk.newssync.presentation.common.ItemClickListener
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_entries_list.*
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 07/12/2018.
 **/

class EntriesListBottomSheetDialogFragment: BaseBottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val itemClickListener = object : ItemClickListener<Entry> {
        override fun onItemClick(v: View, item: Entry) {
            entriesViewModel.setSelected(item)
        }
    }

    private val entriesViewModel: EntriesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(EntriesViewModel::class.java)
    }

    override fun getLayout(): Int = R.layout.fragment_entries_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        entriesViewModel.entries.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is State.Loading -> showLoading()
                is State.Error -> showError(state.errorMessage)
                is State.Success -> showStories(state.data)
            }
        })
        entriesViewModel.selectedConfirmed.observe(viewLifecycleOwner, Observer { confirmed ->
            when(confirmed) {
                true ->  startSelected()
                false -> showSnackBarMessage(R.string.error)
            }
        })
    }

    override fun showLoading() {

    }

    private fun showError(errorMessage: String?) {

    }

    private fun showStories(entries: List<Entry>) {
        entriesListTermRecyclerView.layoutManager = LinearLayoutManager(context)
        entriesListTermRecyclerView.adapter = EntriesAdapter(itemClickListener).apply { submitList(entries) }
    }

    private fun startSelected() {
        (activity as MainActivity?)?.findSelected()
        dismiss()
    }


}