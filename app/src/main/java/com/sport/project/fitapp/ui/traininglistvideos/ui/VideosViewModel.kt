package com.sport.project.fitapp.ui.traininglistvideos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sport.project.fitapp.network.networkDTO.YoutubeItem
import com.sport.project.fitapp.repository.Listing
import com.sport.project.fitapp.ui.traininglistvideos.data.VideosRepository
import javax.inject.Inject


class VideosViewModel @Inject constructor(private val repository: VideosRepository) : ViewModel() {

    private val repoResult = MutableLiveData<Listing<YoutubeItem>>()
    val _exerciseName = MutableLiveData<String>()

    private val exerciseName: LiveData<String>
        get() = _exerciseName


    val videos = Transformations.switchMap(repoResult) { it.pagedList }
    val networkState = Transformations.switchMap(repoResult) { it.networkState }
    val refreshState = Transformations.switchMap(repoResult) { it.refreshState }

    /**
     * Displays first orders
     */
    fun show() {
        repoResult.value = repository.videos(exerciseName.value!!)
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }
}