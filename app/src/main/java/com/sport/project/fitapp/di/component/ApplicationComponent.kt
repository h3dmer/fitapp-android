package com.sport.project.fitapp.di.component

import android.app.Application
import com.sport.project.fitapp.FitApp
import com.sport.project.fitapp.di.modules.ActivityBuildersModule
import com.sport.project.fitapp.di.modules.AppModule
import com.sport.project.fitapp.repository.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RepositoryModule::class,
        ActivityBuildersModule::class
    ]
)
interface ApplicationComponent {

    fun inject(app: FitApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}