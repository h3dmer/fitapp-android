package com.sport.project.fitapp.ui.traininglistvideos.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sport.project.fitapp.network.api.IVideosService
import com.sport.project.fitapp.network.networkDTO.YoutubeItem
import com.sport.project.fitapp.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

class TrainingVideosDataSource(
    private val videoService: IVideosService,
    private val compositeDisposable: CompositeDisposable,
    private val retryExecutor: Executor,
    private val exercise: String
) : PageKeyedDataSource<Int, YoutubeItem>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _initialLoad = MutableLiveData<NetworkState>()
    val initialLoad: LiveData<NetworkState>
        get() = _initialLoad

    private var pageStart = 1
    private var token: String = ""

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                token = ""
                it.invoke()
                pageStart = 1
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, YoutubeItem>
    ) {
        _networkState.postValue(NetworkState.LOADING)
        _initialLoad.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            videoService.getVideos(exercise, token).subscribe({ response ->
                retry = null
                _networkState.postValue(NetworkState.LOADED)
                _initialLoad.postValue(NetworkState.LOADED)
                callback.onResult(response.items!!.toList(), null, 2)
                token = response.nextPageToken!!
            }, { error ->
                retry = {
                    loadInitial(params, callback)
                }
                val networkError = NetworkState.error(error.message ?: "Problem z serwerem")
                _networkState.postValue(networkError)
                _initialLoad.postValue(networkError)
            })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, YoutubeItem>) {
        _networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            videoService.getVideos(exercise, token).subscribe({ response ->
                token = response.nextPageToken!!
                retry = null
                callback.onResult(response.items!!.toList(), params.key + 1)
                _networkState.postValue(NetworkState.LOADED)
                pageStart++
            }, { error ->

                retry = {
                    loadAfter(params, callback)
                }
                _networkState.postValue(NetworkState.error(error.message ?: "Problem z serwerem"))
            })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, YoutubeItem>) {}

    override fun invalidate() {
        super.invalidate()
        compositeDisposable.clear()
    }
}