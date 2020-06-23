package com.sport.project.fitapp.ui.traininglistvideos.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.sport.project.fitapp.network.networkDTO.YoutubeItem
import kotlinx.android.synthetic.main.item_youtube_video.view.*


class YoutubeVideosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(data: YoutubeItem) {
        itemView.videoTitle.text = data.snippet?.title
        itemView.playButton.visibility = View.GONE
        itemView.videoImage.visibility = View.GONE
        itemView.youtubePlayerView.visibility = View.VISIBLE
        Glide.with(itemView)
            .load(data.snippet?.thumbnails?.high?.url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemView.videoImage)
        setUpYoutubeVideo(data.id?.videoId!!)
    }

    private fun setUpYoutubeVideo(videoId: String) {
        itemView.youtubePlayerView.getPlayerUiController().showFullscreenButton(true)
        itemView.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }
}