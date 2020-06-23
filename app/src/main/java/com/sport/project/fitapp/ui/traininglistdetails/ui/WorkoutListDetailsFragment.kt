package com.sport.project.fitapp.ui.traininglistdetails.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.sport.project.fitapp.R
import com.sport.project.fitapp.databinding.FragmentWorkoutListDetailsBinding
import com.sport.project.fitapp.di.Injectable
import kotlinx.android.synthetic.main.fragment_workout_list_details.*


class WorkoutListDetailsFragment : Fragment(), Injectable {

    private val args: WorkoutListDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentWorkoutListDetailsBinding
    private lateinit var workoutsListDetailsAdapter: WorkoutsListDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutListDetailsBinding.inflate(inflater, container, false)

        setSharedElementTransitionOnEnter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        postponeEnterTransition()
        binding.workoutDetailUpperImage.apply {
            val exerciseName = args.exercise.name
            transitionName = exerciseName
            startEnterTransitionAfterLoadingImagee(getImage(exerciseName), this)
            binding.toolbarLayout.title = exerciseName
        }
    }

    private fun getImage(imageName: String): Int {
        return resources.getIdentifier(imageName, "drawable", activity?.packageName)
    }

    private fun setUpView() {
        workoutsListDetailsAdapter = WorkoutsListDetailsAdapter()
        workoutsListDetailsRecyclerView.adapter = workoutsListDetailsAdapter
        workoutsListDetailsAdapter.submitList(args.exercise.types)
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
    }

    private fun startEnterTransitionAfterLoadingImagee(
        imageAddress: Int,
        imageView: ImageView
    ) {
        Glide.with(this)
            .load(imageAddress)
            .dontAnimate() // 1
            .onlyRetrieveFromCache(false)
            .error(R.drawable.ic_food_placeholder)
            .listener(object : RequestListener<Drawable> { // 2
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .apply {
                RequestOptions().dontTransform()
            }
            .into(imageView)
    }
}
