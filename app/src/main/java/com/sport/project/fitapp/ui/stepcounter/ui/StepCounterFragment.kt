package com.sport.project.fitapp.ui.stepcounter.ui


import android.os.Bundle
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.FragmentStepCounterBinding
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.di.injectRequireActivityViewModel
import com.sport.project.fitapp.utils.addGoalDialog
import com.sport.project.fitapp.utils.delay
import com.sport.project.fitapp.utils.event.EventObserver
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject


class StepCounterFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentStepCounterBinding
    private lateinit var stepCounterViewModel: StepCounterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        stepCounterViewModel = injectRequireActivityViewModel(viewModelFactory)
        binding = FragmentStepCounterBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = stepCounterViewModel

            var isToolbarShown = false

            stepCounterScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                    val shouldShowToolbar = scrollY > (toolbar.height / 3)
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar
                        appbar.isActivated = shouldShowToolbar
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )
        }

        setHasOptionsMenu(true)

        subscribeUi()
        onPrepareOptionsMenu(binding.toolbar.menu)
        setupToolbar()

        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.set_goal -> {
                    addGoalDialog(context) { stepCounterViewModel.updateDailyGoal(it) }
                    true
                }
                R.id.turn_off_set_counter -> {
                    if (stepCounterViewModel.getServiceStatus()) {
                        stepCounterViewModel.setServiceStatus(TURN_OFF_SERVICE)
                        toast("Step counter is turned off")
                    } else {
                        stepCounterViewModel.setServiceStatus(TURN_ON_SERVICE)
                        toast("Step counter is turned on")
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_step_counter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val toolbarMenu = menu.findItem(R.id.turn_off_set_counter)
        stepCounterViewModel.serviceStatus.observe(viewLifecycleOwner, Observer {
            delay(1) {
                toolbarMenu.setTitle(
                    if (stepCounterViewModel.getServiceStatus()) {
                        R.string.step_counter_turn_off
                    } else {
                        R.string.step_counter_turn_on
                    }
                )
            }
        })
    }

    private fun subscribeUi() {
        stepCounterViewModel.today.observe(viewLifecycleOwner, Observer {
            binding.waveLoading.apply {
                topTitle = "${it.progressPercentage()}%"
                centerTitle = "Steps: ${it.steps}"
                bottomTitle = "/${it.goal}"
                progressValue = it.progressPercentage()
            }
        })

        stepCounterViewModel.navigationToHistory.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(StepCounterFragmentDirections.actionStepCounterFragmentToStepCounterHistoryFragment())
        })
    }

    companion object {
        private const val TURN_OFF_SERVICE = false
        private const val TURN_ON_SERVICE = true
    }
}
