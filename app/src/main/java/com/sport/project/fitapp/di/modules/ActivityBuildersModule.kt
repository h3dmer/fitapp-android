package com.sport.project.fitapp.di.modules

import com.sport.project.fitapp.ui.SplashActivity
import com.sport.project.fitapp.ui.mainactivity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * All activities intended to user Dagger @Inject should be listed here
 */
@Suppress("unused")
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [FragmentsBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

}