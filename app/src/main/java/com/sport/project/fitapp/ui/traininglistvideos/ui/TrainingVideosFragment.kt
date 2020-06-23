package com.sport.project.fitapp.ui.traininglistvideos.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.sport.project.fitapp.R
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.di.injectViewModel
import com.sport.project.fitapp.repository.NetworkState
import kotlinx.android.synthetic.main.fragment_training_videos.*
import javax.inject.Inject


class TrainingVideosFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: VideosViewModel
    private lateinit var adapter: YoutubeVideosAdapter

    private val args: TrainingVideosFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel._exerciseName.value = args.exerciseName
        initAdapter()
        initSwipeToRefresh()
        viewModel.show()
    }

    private fun initSwipeToRefresh() {
        viewModel.refreshState.observe(viewLifecycleOwner, Observer {
            videosSwipeReresh.isRefreshing = it == NetworkState.LOADING
        })
        videosSwipeReresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initAdapter() {
        activity?.run {
            adapter = YoutubeVideosAdapter {
                viewModel.retry()
            }
            workoutVideosListRecyclerView.adapter = adapter
            viewModel.videos.observe(this, Observer {
                adapter.submitList(it)
            })
            viewModel.networkState.observe(this, Observer {
                adapter.setNetworkState(it)
            })
        }
    }
}
