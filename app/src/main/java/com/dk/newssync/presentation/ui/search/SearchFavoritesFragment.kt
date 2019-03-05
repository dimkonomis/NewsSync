package com.dk.newssync.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 04/01/2019.
 **/

class SearchFavoritesFragment: SearchFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchViewModel.favorites.observe(requireActivity(), Observer { state ->
            showState(state)
        })
    }

}