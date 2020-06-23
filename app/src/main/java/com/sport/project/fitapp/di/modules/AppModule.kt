package com.sport.project.fitapp.di.modules

import android.app.Application
import android.content.Context
import com.sport.project.fitapp.FitApp
import com.sport.project.fitapp.db.AppDatabase
import com.sport.project.fitapp.di.ViewModelsModule
import com.sport.project.fitapp.di.qualifiers.NutritionApiOkHttp
import com.sport.project.fitapp.network.api.AuthInterceptor
import com.sport.project.fitapp.network.api.INutritionService
import com.sport.project.fitapp.network.api.IVideosService
import com.sport.project.fitapp.ui.stepcounter.utils.ObservePreferences
import com.sport.project.fitapp.ui.traininglistvideos.data.VideosRepository
import com.sport.project.fitapp.utils.Constants
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import javax.inject.Singleton

@Module(includes = [ViewModelsModule::class, CoreDataModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: FitApp): Context =
        app

    @Provides
    @Singleton
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Provides
    @Singleton
    fun provideExerciseDao(db: AppDatabase) = db.exercisesDao()

    @Provides
    @Singleton
    fun provideDailyStepsDao(db: AppDatabase) = db.dailyStepsDao()

    @Provides
    @Singleton
    fun provideWorkoutsDao(db: AppDatabase) = db.workoutsDao()

    @Provides
    fun provideObservePreferences(app: Application) = ObservePreferences(app)

    @NutritionApiOkHttp
    @Provides
    fun provideNutritionOkHttpClient(
        okHttpClient: OkHttpClient
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptor(AuthInterceptor()).build()
    }

    @Provides
    @Singleton
    fun providesNutritionService(
        @NutritionApiOkHttp okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): INutritionService =
        provideService(
            okhttpClient,
            converterFactory,
            rxJava2CallAdapterFactory,
            INutritionService::class.java
        )

    @Provides
    @Singleton
    fun provideYoutubeService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ) =
        provideYoutubeService(
            okhttpClient,
            converterFactory,
            rxJava2CallAdapterFactory,
            IVideosService::class.java
        )

    @Provides
    @Singleton
    fun provideVideosRepository(videosService: IVideosService, compositeDisposable: CompositeDisposable,
                                networkExecutor: ExecutorService) =
        VideosRepository(videosService, networkExecutor, compositeDisposable)

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.NUTRITIONIX_API_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun youtubeRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.YOUTUBE_API_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    private fun <T> provideService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        clazz: Class<T>
    ): T {
        return providesRetrofit(
            converterFactory, rxJava2CallAdapterFactory,
            okHttpClient
        ).create(clazz)
    }

    private fun <T> provideYoutubeService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        clazz: Class<T>
    ): T {
        return youtubeRetrofit(
            converterFactory, rxJava2CallAdapterFactory,
            okHttpClient
        ).create(clazz)
    }
}