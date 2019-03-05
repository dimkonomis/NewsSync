package com.dk.newssync.presentation.ui.base

import android.app.Activity
import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 26/02/2019.
 **/

abstract class BaseActivityDagger: BaseActivity(), HasActivityInjector {

    @Inject
    lateinit var appCompatActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return appCompatActivityInjector
    }

}