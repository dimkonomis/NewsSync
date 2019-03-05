package com.dk.newssync.presentation.common

import android.view.View

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 03/01/2019.
 **/

interface ItemClickListener<T> {
    fun onItemClick(v: View, item : T)
}