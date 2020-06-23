package com.sport.project.fitapp.ui.traininglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sport.project.fitapp.databinding.FragmentWorkoutsListBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.utils.event.EventObserver
import javax.inject.Inject

class WorkoutsListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val vm by viewModels<WorkoutsListViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkoutsListBinding.inflate(inflater, container, false).apply {
            workoutsListRecyclerView.setHasFixedSize(true)
            workoutsListRecyclerView.isNestedScrollingEnabled = true
            viewModel = vm
        }

        val workoutsAdapter = WorkoutsListAdapter()

        setHasOptionsMenu(true)

        setUpRecyclerView(binding, workoutsAdapter)

        setUpView(workoutsAdapter)
        return binding.root
    }

    private fun setUpRecyclerView(binding: FragmentWorkoutsListBinding, workoutsListAdapter: WorkoutsListAdapter) {
        binding.workoutsListRecyclerView.run {
            adapter = workoutsListAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun setUpView(adapter: WorkoutsListAdapter) {
        vm.workoutList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        vm.navToWorkouts.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(WorkoutsListFragmentDirections.actionWorkoutsListFragmentToWorkoutsFragment())
        })
    }
}
