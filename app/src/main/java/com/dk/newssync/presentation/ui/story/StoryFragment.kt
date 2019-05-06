package com.dk.newssync.presentation.ui.story

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dk.newssync.R
import com.dk.newssync.data.entity.Story
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.ui.base.BaseFragmentDagger
import com.dk.newssync.presentation.common.loadImage
import kotlinx.android.synthetic.main.fragment_story.*
import javax.inject.Inject
import androidx.browser.customtabs.CustomTabsIntent
import android.net.Uri
import com.dk.newssync.presentation.common.color
import android.content.Intent

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 07/12/2018.
 **/

class StoryFragment : BaseFragmentDagger() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: StoryViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(StoryViewModel::class.java)
    }

    private var story: Story? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        story = arguments?.getParcelable(getString(R.string.story))

        setDetails()
        observeStory()
        observeFavoriteAction()
    }

    override fun getLayout(): Int = R.layout.fragment_story

    private fun observeStory() {
        viewModel.story.observe(viewLifecycleOwner, Observer { value ->
            story = value
        })
        viewModel.getStory(story?.id ?: 0)
    }

    private fun observeFavoriteAction() {
        viewModel.favoriteAction.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is State.Success -> {
                    story = story?.copy(favorite = state.data)
                    when(state.data) {
                        true  -> showSnackBarMessage(R.string.favorite_added)
                        false -> showSnackBarMessage(R.string.favorite_removed)
                    }
                } else -> toggleFavoriteButton()
            }
        })
    }

    private fun setDetails() {
        coverImageView.loadImage(story?.image)
        authorText.text = story?.source
        headLineText.text = story?.title
        descriptionText.text = story?.description
        favoriteButton.isChecked = story?.favorite ?: false
        favoriteButton.setOnClickListener { viewModel.toggleFavorite(story ?: Story()) }
        chromeButton.setOnClickListener { openChrome() }
        shareButton.setOnClickListener { shareUrl() }
    }

    private fun toggleFavoriteButton() {
        showSnackBarMessage(getString(R.string.error))
        favoriteButton.toggle()
    }

    private fun openChrome() {
        val builder = CustomTabsIntent.Builder()
            .setToolbarColor(requireContext().color(R.color.white))
            .enableUrlBarHiding()
            .build()
        builder.launchUrl(activity, Uri.parse(story?.url))
    }

    private fun shareUrl() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, story?.url)
        startActivity(Intent.createChooser(shareIntent, "Share link"))
    }

}