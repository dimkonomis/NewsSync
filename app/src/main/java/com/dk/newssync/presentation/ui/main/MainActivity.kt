package com.dk.newssync.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.dk.newssync.R
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.ui.base.BaseActivityDagger
import com.dk.newssync.presentation.ui.entries.EntriesAddBottomSheetDialog
import com.dk.newssync.presentation.ui.entries.EntriesListBottomSheetDialogFragment
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.view.WindowManager
import androidx.work.*
import com.dk.newssync.presentation.sync.SyncWorker
import java.util.concurrent.TimeUnit

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 12/11/2018.
 **/

class MainActivity : BaseActivityDagger() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        setSupportActionBar(bottomAppBar)
        findNavController(
            this,
            R.id.navHostFragment
        ).addOnDestinationChangedListener { controller, destination, arguments ->
            invalidateOptionsMenu()
            when (destination.id) {
                R.id.listFragment -> {
                    setFabAlignment(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER, R.drawable.icon_add)
                    slideUpBar()
                }
                R.id.storyFragment -> setFabAlignment(BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.icon_back)
                R.id.searchFavoritesFragment -> setFabAlignment(BottomAppBar.FAB_ALIGNMENT_MODE_END, R.drawable.icon_back)
            }
        }
        fab.setOnClickListener {
            when (bottomAppBar.fabAlignmentMode) {
                BottomAppBar.FAB_ALIGNMENT_MODE_END -> onBackPressed()
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER -> showEntriesAddBottomDialog(true)
            }
        }
        startWorkManager()
        observeSelected()
        if(savedInstanceState == null) findSelected()
    }

    override fun getLayout(): Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) {
            menuInflater.inflate(R.menu.menu_main, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_favorites -> showFavorites()
            android.R.id.home -> EntriesListBottomSheetDialogFragment().show(supportFragmentManager, "")
        }

        return true
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.navHostFragment).navigateUp()

    fun findSelected() {
        viewModel.findSelected()
    }

    private fun setFabAlignment(alignment: Int, @DrawableRes drawable: Int) {
        bottomAppBar.fabAlignmentMode = alignment
        bottomAppBar.fabAnimationMode = BottomAppBar.FAB_ANIMATION_MODE_SLIDE
        fab.setImageResource(drawable)
    }

    private fun startWorkManager() {
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .build()
        val syncWork = PeriodicWorkRequest
            .Builder(SyncWorker::class.java, 3, TimeUnit.HOURS)
            .setConstraints(myConstraints)
            .build()
        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(SyncWorker::class.java.name, ExistingPeriodicWorkPolicy.KEEP, syncWork)
    }

    private fun observeSelected() {
        viewModel.selectedEntry.observe(this, Observer { state ->
            dismissLoading()
            when (state) {
                is State.Error -> showError()
                is State.Success -> showSelectedEntry(state.data.name)
            }
        })
    }

    private fun showError() {
        showEntriesAddBottomDialog(false)
    }

    private fun showSelectedEntry(entry: String?) {
        findNavController(this, R.id.navHostFragment)
            .navigate(
                R.id.listFragment,
                Bundle().apply {
                    putString(getString(R.string.entry), entry)
                },
                NavOptions.Builder()
                    .setPopUpTo(
                        R.id.placeholderFragment,
                        true
                    )
                    .setLaunchSingleTop(true)
                    .build()
            )
    }

    private fun showFavorites() {
        findNavController(this, R.id.navHostFragment).navigate(R.id.searchFavoritesFragment)
    }

    private fun showEntriesAddBottomDialog(cancellable: Boolean) {
        EntriesAddBottomSheetDialog().apply {
            isCancelable = cancellable
        }.show(supportFragmentManager, "")
    }

    private fun slideUpBar() {
        (bottomAppBar.behavior as HideBottomViewOnScrollBehavior).slideUp(bottomAppBar)
    }
}