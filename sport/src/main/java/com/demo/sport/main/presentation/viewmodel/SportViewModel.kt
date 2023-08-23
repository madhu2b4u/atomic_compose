package com.demo.sport.main.presentation.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.sport.common.AppCoroutineDispatcherProvider
import com.demo.sport.common.AppCoroutineDispatchers
import com.demo.sport.common.Event
import com.demo.sport.common.Result
import com.demo.sport.common.SingleLiveEvent
import com.demo.sport.common.Status
import com.demo.sport.main.data.models.Sport
import com.demo.sport.main.domain.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SportViewModel @Inject constructor(
    private val useCase: MainUseCase,
) : ViewModel(), LifecycleObserver {

    val data: LiveData<Sport> get() = _data
    private val _data = MediatorLiveData<Sport>()

    val result = MediatorLiveData<Event<Result<List<Sport>>>>()

    val showLoader: LiveData<Boolean> get() = _showLoader
    private val _showLoader = SingleLiveEvent<Boolean>()

    private val dispatcher: AppCoroutineDispatchers = AppCoroutineDispatcherProvider.dispatcher()

    init {
        getSports()
    }

    fun getSports() {
        viewModelScope.launch {
            withContext(dispatcher.main()) {
                result.addSource(useCase.getSports()) {
                    result.value = Event(it)
                    handleResponse(it)
                }
            }
        }
    }

    private fun handleResponse(it: Result<List<Sport>>) {
        when (it.status) {
            Status.LOADING -> {
                _showLoader.value = true
            }

            Status.ERROR -> {
                _showLoader.value = false
            }

            Status.SUCCESS -> {
                _showLoader.value = false
                it.data?.let { it1 -> handleSportsData(it.data) }
            }
        }
    }

    private fun handleSportsData(data: List<Sport>) {
        _data.value = data.random()
    }
}