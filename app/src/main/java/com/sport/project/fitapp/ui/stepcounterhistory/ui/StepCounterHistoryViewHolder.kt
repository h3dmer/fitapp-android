package com.sport.project.fitapp.ui.stepcounterhistory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.databinding.ItemStepCounterHistoryBinding
import com.sport.project.fitapp.db.entities.DailySteps

class StepCounterHistoryViewHolder private constructor(val binding: ItemStepCounterHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        dailySteps: DailySteps
    ) {
        binding.dailySteps = dailySteps
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): StepCounterHistoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemStepCounterHistoryBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return StepCounterHistoryViewHolder(binding)
        }
    }
}