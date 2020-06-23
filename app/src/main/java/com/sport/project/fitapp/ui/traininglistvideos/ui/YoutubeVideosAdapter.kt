package com.sport.project.fitapp.ui.traininglistvideos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.R
import com.sport.project.fitapp.network.networkDTO.YoutubeItem
import com.sport.project.fitapp.repository.NetworkState
import com.sport.project.fitapp.ui.base.NetworkStateItemViewHolder

class YoutubeVideosAdapter(private val retryCallback: () -> Unit) : PagedListAdapter<YoutubeItem, RecyclerView.ViewHolder>(
    DIFF_CALLBACK) {

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_youtube_video -> (holder as YoutubeVideosViewHolder).bind(getItem(position)!!)
            R.layout.item_network_state -> (holder as NetworkStateItemViewHolder).bindTo(
                networkState, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_youtube_video -> YoutubeVideosViewHolder(inflater.inflate(R.layout.item_youtube_video, parent, false))
            R.layout.item_network_state -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_youtube_video
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<YoutubeItem>() {
            override fun areItemsTheSame(oldItem: YoutubeItem, newItem: YoutubeItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: YoutubeItem, newItem: YoutubeItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}