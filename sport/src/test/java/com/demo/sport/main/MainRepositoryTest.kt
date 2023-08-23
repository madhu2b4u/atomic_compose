package com.demo.sport.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.sport.LiveDataTestUtil
import com.demo.sport.MainCoroutineRule
import com.demo.sport.common.Status
import com.demo.sport.main.data.models.Sport
import com.demo.sport.main.data.repository.MainRepository
import com.demo.sport.main.data.repository.MainRepositoryImpl
import com.demo.sport.main.data.source.LocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class MainRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repositoryTest: MainRepository

    @Mock
    lateinit var localStore: LocalDataSource

    private val sportData = Sport.createMockedSports()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repositoryTest = MainRepositoryImpl(localStore)
    }

    @Test
    fun getSports() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(localStore.getSports())
            .thenReturn(sportData)

        val result = repositoryTest.getSports()
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        delay(2500)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)

        assert(LiveDataTestUtil.getValue(result).data == sportData)
    }

    @Test(expected = Exception::class)
    fun getSportsThrowsException() = mainCoroutineRule.runBlockingTest {
        Mockito.doThrow(Exception("no data"))
            .`when`(localStore.getSports())
        repositoryTest.getSports()
    }

}