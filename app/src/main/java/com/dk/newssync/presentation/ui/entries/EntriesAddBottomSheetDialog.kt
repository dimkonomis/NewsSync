package com.dk.newssync.presentation.ui.entries

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dk.newssync.R
import com.dk.newssync.presentation.ui.base.BaseBottomSheetDialogFragment
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_entries_add.*
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

class EntriesAddBottomSheetDialog: BaseBottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val entriesViewModel: EntriesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(EntriesViewModel::class.java)
    }

    override fun getLayout(): Int = R.layout.fragment_entries_add

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEditText()
        nameValidationObserve()
        newEntryObserve()
    }

    private fun initEditText() {
        entryInputLayout?.editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                entriesViewModel.validate(entryInputLayout?.editText?.text?.toString()?.trim()?.toLowerCase())
            }
            false
        }
    }

    private fun nameValidationObserve() {
        entriesViewModel.nameValidation.observe(viewLifecycleOwner, Observer { validation ->
            when(validation) {
                true -> {
                    setErrorEnabled(false, null)
                    entriesViewModel.submit(entryInputLayout?.editText?.text?.toString()?.trim()?.toLowerCase())
                }
                false -> setErrorEnabled(true, getString(R.string.no_value))
            }
        })
    }

    private fun newEntryObserve() {
        entriesViewModel.newEntryState.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is State.Loading -> setErrorEnabled(false, null)
                is State.Success -> successAdded()
                is State.Error   -> setErrorEnabled(true, state.errorMessage)
            }
        })
    }

    private fun successAdded() {
        (activity as MainActivity?)?.findSelected()
        dismiss()
    }

    private fun setErrorEnabled(enabled: Boolean, error: String?) {
        entryInputLayout?.isErrorEnabled = enabled
        entryInputLayout?.error = error
    }

}