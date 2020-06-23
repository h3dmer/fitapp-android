package com.sport.project.fitapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sport.project.fitapp.ui.foodmacrodetails.ui.FoodMacroDetailsViewModel
import com.sport.project.fitapp.ui.macro.MacroViewModel
import com.sport.project.fitapp.ui.nutritions.ui.NutritionViewModel
import com.sport.project.fitapp.ui.stepcounter.ui.StepCounterViewModel
import com.sport.project.fitapp.ui.stepcounterhistory.ui.StepCounterHistoryViewModel
import com.sport.project.fitapp.ui.traininglist.ui.WorkoutsListViewModel
import com.sport.project.fitapp.ui.traininglistvideos.ui.VideosViewModel
import com.sport.project.fitapp.ui.workoutdetails.ui.WorkoutDetailViewModel
import com.sport.project.fitapp.ui.workoutdetails.ui.updateexercisedialog.UpdateExerciseDialogViewModel
import com.sport.project.fitapp.ui.workouts.ui.WorkoutsViewModel
import com.sport.project.fitapp.ui.workouts.ui.workoutsdialog.WorkoutsDialogViewModel
//import com.sport.project.fitapp.ui.traininglist.ui.WorkoutsListViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NutritionViewModel::class)
    abstract fun bindFoodViewModel(foodViewModel: NutritionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MacroViewModel::class)
    abstract fun bindMacroViewModel(viewModel: MacroViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutsListViewModel::class)
    abstract fun workoutsListViewModel(workoutsListViewModel: WorkoutsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideosViewModel::class)
    abstract fun videosViewModel(videosViewModel: VideosViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FoodMacroDetailsViewModel::class)
    abstract fun foodDetailsViewModel(foodDetailsViewModel: FoodMacroDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StepCounterViewModel::class)
    abstract fun bindStepCounterViewModel(stepCounterViewModel: StepCounterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StepCounterHistoryViewModel::class)
    abstract fun bindStepCounterHistoryViewModel(stepCounterHistoryViewModel: StepCounterHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutsViewModel::class)
    abstract fun bindWorkoutsViewModel(workoutsViewModel: WorkoutsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutsDialogViewModel::class)
    abstract fun bindWorkoutsDialogViewModel(workoutsDialogViewModel: WorkoutsDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutDetailViewModel::class)
    abstract fun bindWorkoutDetailViewModel(workoutDetailViewModel: WorkoutDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateExerciseDialogViewModel::class)
    abstract fun bindUpdateExerciseDialogViewModel(workoutDetailViewModel: UpdateExerciseDialogViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}