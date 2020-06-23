package com.sport.project.fitapp.di.modules

import com.sport.project.fitapp.ui.calculatecalories.CalculateCaloriesFragment
import com.sport.project.fitapp.ui.exercisedetails.ExerciseDetailsFragment
import com.sport.project.fitapp.ui.foodmacrodetails.ui.FoodMacroDetailsFragment
import com.sport.project.fitapp.ui.macro.MacroFragment
import com.sport.project.fitapp.ui.naturallanguageexercises.ui.NaturalLanguageExercisesFragment
import com.sport.project.fitapp.ui.naturallanguagefoods.ui.NaturalLanguageFoodsFragment
import com.sport.project.fitapp.ui.nutritions.ui.NutritionViewPagerFragment
import com.sport.project.fitapp.ui.stepcounter.ui.StepCounterFragment
import com.sport.project.fitapp.ui.stepcounterhistory.ui.StepCounterHistoryFragment
import com.sport.project.fitapp.ui.traininglist.ui.WorkoutsListFragment
import com.sport.project.fitapp.ui.traininglistdetails.ui.WorkoutListDetailsFragment
import com.sport.project.fitapp.ui.traininglistvideos.ui.TrainingVideosFragment
import com.sport.project.fitapp.ui.workoutdetails.ui.WorkoutDetailFragment
import com.sport.project.fitapp.ui.workoutdetails.ui.updateexercisedialog.UpdateExerciseDialog
import com.sport.project.fitapp.ui.workouts.ui.WorkoutsFragment
import com.sport.project.fitapp.ui.workouts.ui.workoutsdialog.WorkoutsDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeWorkoutFragment(): WorkoutsListFragment

    @ContributesAndroidInjector
    abstract fun contributeMacroFragment(): MacroFragment

    @ContributesAndroidInjector
    abstract fun contributeCalculateCaloriesFragment(): CalculateCaloriesFragment

    @ContributesAndroidInjector
    abstract fun contributeStepCounterFragment(): StepCounterFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFoodFragment(): NutritionViewPagerFragment

    @ContributesAndroidInjector
    abstract fun contributeWorkoutListDetails(): WorkoutListDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeWorkoutVideos(): TrainingVideosFragment

    @ContributesAndroidInjector
    abstract fun contributeExerciseDetailsFragment(): ExerciseDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeNaturalLanguageExercises(): NaturalLanguageExercisesFragment

    @ContributesAndroidInjector
    abstract fun contributeNaturalLanguageFoods(): NaturalLanguageFoodsFragment

    @ContributesAndroidInjector
    abstract fun contributeFoodMacroDetailsFragment(): FoodMacroDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeStepCounterHistory(): StepCounterHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeWorkoutsFragment(): WorkoutsFragment

    @ContributesAndroidInjector
    abstract fun contributeWorkoutsDialog(): WorkoutsDialog

    @ContributesAndroidInjector
    abstract fun contributeWorkoutDetailsFragment(): WorkoutDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeUpdateExerciseDialog(): UpdateExerciseDialog

}