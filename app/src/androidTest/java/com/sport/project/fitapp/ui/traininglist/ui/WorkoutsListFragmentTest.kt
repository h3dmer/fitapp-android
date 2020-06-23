package com.sport.project.fitapp.ui.traininglist.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.sport.project.fitapp.R
import com.sport.project.fitapp.db.entities.Exercise
import com.sport.project.fitapp.ui.mainactivity.MainActivity
import com.sport.project.fitapp.ui.traininglistdetails.ui.WorkoutsListDetailsAdapter.WorkoutsListDetailsViewHolder
import com.sport.project.fitapp.ui.workouts.ui.WorkoutsViewHolder
import com.sport.project.fitapp.utils.CustomMatchers
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class WorkoutsListFragmentTest {

    private lateinit var viewModel: WorkoutsListViewModel

    @Before
    fun init() {
        viewModel = mockk()
        every {
            viewModel.workoutList.value
        } returns exercise
        launchActivity()
    }

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.workoutsListRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isExercisesVisible() {

        onView(withId(R.id.workoutsListRecyclerView))
            .perform(actionOnItemAtPosition<WorkoutsViewHolder>(0, click()))

        onView(withId(R.id.workoutsListDetailsRecyclerView)).check(matches(isDisplayed()))
        //onView(isAssignableFrom(CollapsingToolbarLayout::class.java)).check(matches(CustomMatchers.withCollapsingToolbarTitle(exercise[0].name)))
    }

    @Test
    fun test_backNavigation_toWorkoutsFragment() {
        onView(withId(R.id.workoutsListRecyclerView))
            .perform(actionOnItemAtPosition<WorkoutsViewHolder>(0, click()))

        onView(withId(R.id.workoutsListDetailsRecyclerView)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.workoutsListRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navWorkoutDetails_validateVideosList() {
        onView(withId(R.id.workoutsListRecyclerView))
            .perform(actionOnItemAtPosition<WorkoutsViewHolder>(0, click()))

        onView(withId(R.id.workoutsListDetailsRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.workoutsListDetailsRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.workoutsListDetailsRecyclerView))
            .perform(actionOnItemAtPosition<WorkoutsListDetailsViewHolder>(0, click()))

        onView(withId(R.id.workoutVideosListRecyclerView)).check(matches(isDisplayed()))

    }

    @Test
    fun displayWorkouts_whenViewModelHasData() {

        // GIVEN
        viewModel.workoutList

        // WHEN
        //launchActivity()

        // THEN
        onView(isAssignableFrom(CollapsingToolbarLayout::class.java))
            .check(matches(CustomMatchers.withCollapsingToolbarTitle(R.string.muscles_parts)))

//        onView(withId(R.id.workoutsListRecyclerView))
//            .perform(
//                actionOnItemAtPosition<WorkoutsViewHolder>(1,clickItemWithId(R.id.button))
//            )
        onView(withText("abs")).check(matches(isDisplayed()))
        onView(withText("chest")).check(matches(isDisplayed()))
        onView(withText("shoulder")).check(matches(isDisplayed()))
//        onView(withText("biceps")).check(matches(isDisplayed()))
//        onView(withText("triceps")).check(matches(isDisplayed()))
//        onView(withText("legs")).check(matches(isDisplayed()))
//        onView(withText("back")).check(matches(isDisplayed()))
//        onView(withText("calf")).check(matches(isDisplayed()))
//        onView(withText("forearm")).check(matches(isDisplayed()))
//        onView(withText("triceps")).check(matches(isDisplayed()))
    }


    fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }

    private fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.workoutsListRecyclerView) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }

    private val exercise = listOf(
        Exercise(id = 0, name = "abs", types = listOf("Crunches, Decline crunch, Dumbell Side Bends")),
        Exercise(id = 1, name = "chest", types = listOf("chest1, chest2, chest3")),
        Exercise(id = 2, name = "shoulder", types = listOf("shoulder1, shoulder2, shoulder3")),
        Exercise(id = 3, name = "back", types = listOf("shoulder1, shoulder2, shoulder3")),
        Exercise(id = 4, name = "biceps", types = listOf("shoulder1, shoulder2, shoulder3")),
        Exercise(id = 5, name = "legs", types = listOf("shoulder1, shoulder2, shoulder3")),
        Exercise(id = 6, name = "calf", types = listOf("shoulder1, shoulder2, shoulder3")),
        Exercise(id = 7, name = "triceps", types = listOf("shoulder1, shoulder2, shoulder3"))
    )
}