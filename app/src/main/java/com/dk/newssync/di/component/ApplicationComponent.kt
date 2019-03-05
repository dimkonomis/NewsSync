package com.dk.newssync.di.component

import android.app.Application
import com.dk.newssync.NewsSyncApplication
import com.dk.newssync.di.*
import com.dk.newssync.di.builder.*
import com.dk.newssync.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    LocalModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    UseCaseModule::class,
    ViewModelModule::class,
    MainActivityBuilder::class,
    SearchFragmentBuilder::class,
    StoryFragmentBuilder::class,
    EntriesBottomSheetDialogFragmentBuilder::class,
    SyncServiceBuilder::class
])
interface ApplicationComponent : AndroidInjector<NewsSyncApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun localModule(localModule: LocalModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
        fun useCaseModule(useCaseModule: UseCaseModule): Builder
        fun build(): ApplicationComponent
    }

    override fun inject(newsSyncApplication: NewsSyncApplication)
}
