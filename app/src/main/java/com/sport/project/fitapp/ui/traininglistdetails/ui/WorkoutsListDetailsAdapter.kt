package com.sport.project.fitapp.ui.traininglistdetails.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class WorkoutsListDetailsAdapter : ListAdapter<String, WorkoutsListDetailsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsListDetailsViewHolder =
        WorkoutsListDetailsViewHolder.from(parent)
        //WorkoutsListDetailsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_workouts_list, parent, false))

    override fun onBindViewHolder(holder: WorkoutsListDetailsViewHolder, position: Int) =
        holder.bind(getItem(position))

//    inner class WorkoutsListDetailsViewHolder(view: View, private val clickCallback: (String) -> Unit) : RecyclerView.ViewHolder(view) {
//        fun bind(exerciseName: String) {
//            itemView.workoutListCardView.clipToOutline = false
//            itemView.workoutListImage.visibility = View.VISIBLE
//            itemView.workoutListCardView.radius = 30f
//            itemView.workoutListName.text = exerciseName
//            itemView.workoutListCardView.setBackgroundColor(itemView.resources.getColor(R.color.logoColor, null))
//            itemView.workoutListCardView.onClick { clickCallback(exerciseName) }
//        }
//    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}