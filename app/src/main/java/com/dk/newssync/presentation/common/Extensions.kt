package com.dk.newssync.presentation.common

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dk.newssync.presentation.ui.base.GlideApp
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import timber.log.Timber

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 12/11/2018.
 **/

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.drawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)

fun Context.toast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.hideKeyboard() {
    var view = (this as AppCompatActivity).currentFocus
    if (view == null) view = View(this)
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard() {
    var view = (this as AppCompatActivity).currentFocus
    if (view == null) view = View(this)
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

fun View.visible(visible: Boolean, invisibility: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else invisibility
}

fun ImageView.loadImage(url: String?, priority: Priority = Priority.IMMEDIATE) {
    GlideApp.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(150))
        .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(16)))
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .priority(priority)
        .into(this)
}

fun ImageView.colorFilter(colorRes: Int, mode: PorterDuff.Mode = android.graphics.PorterDuff.Mode.SRC_IN) {
    setColorFilter(context.color(colorRes), mode)
}

fun TextView.color(colorRes: Int) {
    setTextColor(context.color(colorRes))
}

fun <T> Publisher<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this) as LiveData<T>

fun <T> Flowable<T>.toState(): Flowable<State<T>> {
    return compose { item ->
        item
            .map { State.success(it) }
            .startWith(State.loading())
            .onErrorReturn { e -> Timber.e(e); State.error(e.message ?: "Unknown Error", e) }
    }
}

fun defaultErrorHandler(): (Throwable) -> Unit = { e -> Timber.e(e) }