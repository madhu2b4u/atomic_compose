package com.demo.sport.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.demo.sport.LiveDataTestUtil
import com.demo.sport.MainCoroutineRule
import com.demo.sport.common.Result
import com.demo.sport.common.Status
import com.demo.sport.main.data.models.Sport
import com.demo.sport.main.data.repository.MainRepository
import com.demo.sport.main.domain.MainUseCase
import com.demo.sport.main.domain.MainUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
class MainUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var useCase: MainUseCase

    lateinit var repository: MainRepository

    private val data = Sport.createMockedSports()

    @Test
    fun testSportsRequestLoading() = mainCoroutineRule.runBlockingTest {

        repository = mock {
            onBlocking {
                getSports()
            } doReturn liveData {
                emit(Result.loading())
            }
        }

        useCase = MainUseCaseImpl(repository)

        val result = useCase.getSports()

        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun testSportsRequestSuccess() = mainCoroutineRule.runBlockingTest {

        repository = mock {
            onBlocking {
                getSports()
            } doReturn liveData {
                emit(Result.success(data))
            }
        }

        useCase = MainUseCaseImpl(repository)

        val result = useCase.getSports()

        assert(
            LiveDataTestUtil.getValue(result).data == data &&
                    LiveDataTestUtil.getValue(result).status == Status.SUCCESS
        )
    }

    @Test
    fun testSportsRequestErrorData() = mainCoroutineRule.runBlockingTest {
        repository = mock {
            onBlocking { getSports() } doReturn liveData {
                emit(Result.error("no data"))
            }
        }
        useCase = MainUseCaseImpl(repository)

        val result = useCase.getSports()
        result.observeForever { }
        assert(
            LiveDataTestUtil.getValue(result).status == Status.ERROR && LiveDataTestUtil.getValue(
                result
            ).message == "no data"
        )

    }

}