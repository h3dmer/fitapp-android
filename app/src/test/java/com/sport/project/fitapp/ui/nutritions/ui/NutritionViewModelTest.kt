package com.sport.project.fitapp.ui.nutritions.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.whenever
import com.sport.project.fitapp.network.networkDTO.CalculateExercise
import com.sport.project.fitapp.network.networkDTO.NaturalExercise
import com.sport.project.fitapp.testutils.RxSchedulerRule
import com.sport.project.fitapp.ui.nutritions.data.NaturalLanguageRepositoryImpl
import com.sport.project.fitapp.ui.nutritions.ui.state.NutritionViewState
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NutritionViewModelTest {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: NaturalLanguageRepositoryImpl

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    @InjectMocks
    lateinit var viewModel: NutritionViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun burnedCaloriesFromExercises_withSuccess() {
        //Given
        val expected = NutritionViewState.NutritionSuccess(NaturalExercise()).data
        viewModel = NutritionViewModel(repo, compositeDisposable)
        viewModel.burnedCaloriesFromExercises(CalculateExercise())

        // When
        whenever(repo.calculateCaloriesExercises(CalculateExercise()))
            .thenReturn(Single.just(NaturalExercise()))

        // Then
        Truth.assert_()
            .that(expected)
            .isEqualTo(viewModel.fetchExercises.value)
    }

    @Test
    fun burnedCaloriesFromFood() {

    }

    @Test
    fun burnedCaloriesFromExercises_withError(){
        // Given that the comment reply is posted with error
        val expected = "Error"
        whenever(repo.calculateCaloriesExercises(CalculateExercise()))
            .thenReturn(Single.just(NaturalExercise()))
        // And the view model is constructed
        val viewModel = NutritionViewModel(repo, compositeDisposable)
    }
}