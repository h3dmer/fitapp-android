package com.sport.project.fitapp.ui.workouts.ui.workoutsdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.WorkoutsDialogBinding
import com.sport.project.fitapp.db.entities.Workout
import com.sport.project.fitapp.di.Injectable
import com.sport.project.fitapp.utils.event.EventObserver
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject

class WorkoutsDialog(private val workout: Workout?) : DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val vm by viewModels<WorkoutsDialogViewModel> { viewModelProvider }
    private lateinit var binding: WorkoutsDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WorkoutsDialogBinding.inflate(inflater, container, false)
        binding.viewModel = vm
        binding.lifecycleOwner = this@WorkoutsDialog

        binding.toolbar.apply {
            inflateMenu(R.menu.menu_dialog_workouts)
            setNavigationOnClickListener { dismissDialog() }
            setOnMenuItemClickListener {
                vm.saveWorkout()
                true
            }
        }

        val exercisesAdapter = WorkoutExercisesAdapter({}) { exercise ->
            vm.deleteWorkout(exercise)
        }

        binding.exercisesRecyclerView.adapter = exercisesAdapter

        onMusclePartSelected()
        subscribeUi(exercisesAdapter)
        viewNavigation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.checkIfNew(workout)
    }

    private fun subscribeUi(exercisesAdapter: WorkoutExercisesAdapter) {
        vm.muscleParts.observe(viewLifecycleOwner, Observer {
            val musclesAdapter = ArrayAdapter(
                requireContext(),
                R.layout.list_item,
                vm.getMusclesParts()
            )
            binding.musclePartAutocomplete.setAdapter(musclesAdapter)
            vm.exerciseName.value = ""
        })

        vm.exercise.observe(viewLifecycleOwner, Observer {
            binding.exerciseMenu.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.list_item,
                    it.types.toTypedArray()
                )
            )
        })

        vm.workoutExercises.observe(viewLifecycleOwner, Observer {
            exercisesAdapter.submitList(it.sortedByDescending { list -> list.id })
        })

        vm.snackbarMessage.observe(viewLifecycleOwner, EventObserver {
            view?.snackbar(it)
        })
    }

    private fun viewNavigation() {
        vm.addWorkoutEvent.observe(viewLifecycleOwner, EventObserver {
            dismissDialog()
        })
    }

    private fun onMusclePartSelected() {
        binding.musclePartAutocomplete.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                vm.setExercise(vm.muscleParts.value?.get(position)!!.name)
            }
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            dialog!!.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog!!.window?.setWindowAnimations(R.style.DialogSlide)
        }
    }

    private fun dismissDialog(): Boolean {
        dismiss()
        return true
    }
}