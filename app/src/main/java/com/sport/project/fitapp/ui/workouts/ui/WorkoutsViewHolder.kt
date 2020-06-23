package com.sport.project.fitapp.ui.workouts.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.ItemWorkoutBinding
import com.sport.project.fitapp.db.entities.Workout


class WorkoutsViewHolder private constructor(val binding: ItemWorkoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.setOpenWorkoutDetails { view ->
            binding.workout?.let { workout ->
                view.findNavController().navigate(WorkoutsFragmentDirections.actionWorkoutsFragmentToWorkoutDetailFragment(workout.id))
            }
        }
    }

    fun bind(workout: Workout, deleteWorkout: (Workout) -> Unit, updateWorkout: (Workout) -> Unit) {
        binding.workout = workout
        binding.setOpenWorkoutMenu { view ->
            showMenu(view, workout, deleteWorkout, updateWorkout)
        }
        binding.executePendingBindings()
    }

    private fun showMenu(view: View, workout: Workout, deleteWorkout: (Workout) -> Unit, updateWorkout: (Workout) -> Unit) {
        val popup = PopupMenu(itemView.context, view, 0, 0, R.style.WorkoutPopUp)
        popup.menuInflater.inflate(R.menu.menu_workout_item, popup.menu)
        popup.show()

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.updateWorkout -> {
                    updateWorkout(workout)
                    true
                }
                R.id.deleteWorkout -> {
                    deleteWorkout(workout)
                    true
                }
                else -> throw IllegalArgumentException()
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): WorkoutsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemWorkoutBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return WorkoutsViewHolder(binding)
        }
    }
}