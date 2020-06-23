package com.sport.project.fitapp.ui.stepcounterhistory.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sport.project.fitapp.db.entities.DailySteps

class StepCounterHistoryAdapter : ListAdapter<DailySteps, StepCounterHistoryViewHolder>(StepCounterHistoryDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StepCounterHistoryViewHolder =
        StepCounterHistoryViewHolder.from(parent)

    override fun onBindViewHolder(holder: StepCounterHistoryViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class StepCounterHistoryDiffCallback : DiffUtil.ItemCallback<DailySteps>() {
    override fun areItemsTheSame(oldItem: DailySteps, newItem: DailySteps): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DailySteps, newItem: DailySteps): Boolean {
        return oldItem == newItem
    }
}
