package com.sport.project.fitapp.ui.traininglistvideos.data

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import com.sport.project.fitapp.network.api.IVideosService
import com.sport.project.fitapp.network.networkDTO.YoutubeItem
import com.sport.project.fitapp.repository.Listing
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRepository @Inject constructor(
    @JvmSuppressWildcards private val videosService: IVideosService,
    private val networkExecutor: Executor,
    private val compositeDisposable: CompositeDisposable
) {

    @MainThread
    fun videos(query: String): Listing<YoutubeItem> {
        val sourceFactory = VideosDataSourceFactory(videosService, compositeDisposable, networkExecutor, "$query exercise")

        val livePagedList = LivePagedListBuilder(sourceFactory, 10)
            // provide custom executor for network requests, otherwise it will default to
            // Arch Components' IO pool which is also used for disk access
            .setFetchExecutor(networkExecutor)
            .build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }
}

class VideosDataSourceFactory(private val videoService: IVideosService,
                             private val compositeDisposable: CompositeDisposable,
                             private val retryExecutor: Executor,
                             private val exercise: String
) : DataSource.Factory<Int, YoutubeItem>() {
    val sourceLiveData = MutableLiveData<TrainingVideosDataSource>()
    override fun create(): DataSource<Int, YoutubeItem> {
        val source = TrainingVideosDataSource(videoService, compositeDisposable, retryExecutor, exercise)
        sourceLiveData.postValue(source)
        return source
    }
}