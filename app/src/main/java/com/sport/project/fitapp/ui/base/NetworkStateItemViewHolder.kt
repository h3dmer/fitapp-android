package com.sport.project.fitapp.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sport.project.fitapp.R
import com.sport.project.fitapp.repository.NetworkState
import com.sport.project.fitapp.repository.Status
import kotlinx.android.synthetic.main.item_network_state.view.*


class NetworkStateItemViewHolder(root: View, retryCallback: () -> Unit) :
    RecyclerView.ViewHolder(root) {

    private val progressBar = root.paginationProgressBar
    private val retry = root.paginationRetryButton
    private val errorMsg = root.paginationErrorMsg

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    fun bindTo(networkState: NetworkState?, position: Int) {
        progressBar.visibility =
            toVisibility(networkState?.status == Status.RUNNING && position != 0)
        retry.visibility = toVisibility(networkState?.status == Status.FAILED)
        errorMsg.visibility = toVisibility(networkState?.msg != null)
        errorMsg.text = networkState?.msg
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_network_state, parent, false)
            return NetworkStateItemViewHolder(view, retryCallback)
        }

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}