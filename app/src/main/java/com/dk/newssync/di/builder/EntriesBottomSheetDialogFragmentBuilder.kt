package com.dk.newssync.di.builder

import com.dk.newssync.di.module.EntriesModule
import com.dk.newssync.presentation.ui.entries.EntriesAddBottomSheetDialog
import com.dk.newssync.presentation.ui.entries.EntriesListBottomSheetDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

@Module
interface EntriesBottomSheetDialogFragmentBuilder {
    @ContributesAndroidInjector(modules = [EntriesModule::class])
    fun contributeEntriesListBottomSheetDialogFragment(): EntriesListBottomSheetDialogFragment
    @ContributesAndroidInjector(modules = [EntriesModule::class])
    fun contributeAddEntryBottomSheetDialog(): EntriesAddBottomSheetDialog
}