package com.dk.newssync

import com.dk.newssync.di.*
import com.dk.newssync.di.component.DaggerApplicationComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 12/11/2018.
 **/

class NewsSyncApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .applicationModule(ApplicationModule(applicationContext))
            .localModule(LocalModule())
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .useCaseModule(UseCaseModule())
            .build()
    }

}