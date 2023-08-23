package com.demo.sport.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.demo.sport.LiveDataTestUtil
import com.demo.sport.MainCoroutineRule
import com.demo.sport.common.Result
import com.demo.sport.common.Status
import com.demo.sport.main.data.models.Sport
import com.demo.sport.main.domain.MainUseCase
import com.demo.sport.main.presentation.viewmodel.SportViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: MainUseCase

    private lateinit var viewModel: SportViewModel

    private val data = Sport.createMockedSports()

    @Before
    fun init() {
        useCase = mock()
    }

    @Test
    fun testGetSportsLoadingData() = mainCoroutineRule.runBlockingTest {
        useCase = mock {
            onBlocking { getSports() } doReturn liveData {
                emit(Result.loading())
            }
        }
        viewModel = SportViewModel(useCase)
        viewModel.getSports()
        val result = viewModel.result
        result.observeForever { }
        assert(LiveDataTestUtil.getValue(result).peekContent().status == Status.LOADING)
    }

    @Test
    fun testGetSportsListSuccessData() = mainCoroutineRule.runBlockingTest {

        useCase = mock {
            onBlocking { getSports() } doReturn liveData {
                emit(Result.success(data))
            }
        }

        viewModel = SportViewModel(useCase)
        viewModel.getSports()

        val result = viewModel.result
        result.observeForever {}
        assert(
            LiveDataTestUtil.getValue(result).peekContent().status == Status.SUCCESS &&
                    LiveDataTestUtil.getValue(result).peekContent().data == data
        )
    }

    @Test
    fun testGetSportsErrorData() = mainCoroutineRule.runBlockingTest {
        useCase = mock {
            onBlocking { getSports() } doReturn object :
                LiveData<Result<List<Sport>>>() {
                init {
                    value = Result.error("error")
                }
            }
        }

        viewModel = SportViewModel(useCase)
        viewModel.getSports()

        val result = viewModel.result
        result.observeForever {}
        assert(
            LiveDataTestUtil.getValue(result).peekContent().status == Status.ERROR &&
                    LiveDataTestUtil.getValue(result).peekContent().message == "error"
        )
    }

}