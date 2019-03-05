package com.dk.newssync.presentation.ui.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dk.newssync.R
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 12/11/2018.
 **/

abstract class BaseActivity: AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayout() != 0) setContentView(getLayout())
    }

    abstract fun getLayout(): Int

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showLoading() {
        if(progressDialog == null) progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(getString(R.string.please_wait))
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    fun showLoading(message: String) {
        if(progressDialog == null) progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(message)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    fun dismissLoading() {
        progressDialog?.let { if (it.isShowing) it.dismiss() }
    }

    fun showSnackBarMessage(message: String) {
        Snackbar.make(findViewById(R.id.root), message, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackBarMessage(@StringRes message: Int) {
        Snackbar.make(findViewById(R.id.root), message, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackBarMessage(@StringRes message: Int, @StringRes action: Int, unit: () -> Unit) {
        Snackbar.make(findViewById(R.id.root), message, Snackbar.LENGTH_LONG).apply {
            setAction(action) {
                unit()
            }
        }.show()
    }

    fun showMessageDialog(@StringRes message: Int,
                          @StringRes positive: Int? = null, positiveListener: DialogInterface.OnClickListener? = null,
                          @StringRes negative: Int? = null, negativeListener: DialogInterface.OnClickListener? = null) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)

        positive?.let { builder.setPositiveButton(it, positiveListener) }

        negative?.let { builder.setNegativeButton(it, negativeListener) }

        builder.show()
    }

}