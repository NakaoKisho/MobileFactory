package com.example.mobilefactory.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefactory.data.OfflineTimeRepository
import com.example.mobilefactory.database.model.TimeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(
    private val offlineTimeRepository: OfflineTimeRepository
) : ViewModel() {
    val uiState = offlineTimeRepository
        .getAllTime()
        .map(RegistrationUiState::Time)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RegistrationUiState.Loading,
        )

    // Specific time
    private var _specificTime = MutableStateFlow("")
    val specificTime = _specificTime
    fun setSpecificTime(specificTime: String) {
        _specificTime.value = specificTime
    }

    // Start time
    private var _startTime = MutableStateFlow("")
    val startTime = _startTime
    fun setStartTime(startTime: String) {
        _startTime.value = startTime
    }

    // Ending time
    private var _endingTime = MutableStateFlow("")
    val endingTime = _endingTime
    fun setEndingTime(endingTime: String) {
        _endingTime.value = endingTime
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun registerTime() {
        viewModelScope.launch {
            val specificTimeText = _specificTime.value
            val startTimeText = _startTime.value
            val endingTimeText = _endingTime.value
            if (specificTimeText == "" || startTimeText == "" || endingTimeText == "")
                return@launch

            val specificTimeInt = specificTimeText.toInt()
            val startTimeInt = startTimeText.toInt()
            val endingTimeInt = endingTimeText.toInt()
            var result = false
            if (startTimeInt == endingTimeInt) {
                if (specificTimeInt == startTimeInt) {
                    result = true
                }
            } else {
                if (startTimeInt < endingTimeInt) {
                    if (specificTimeInt in startTimeInt..< endingTimeInt) {
                        result = true
                    }
                } else {
                    if (
                        specificTimeInt in startTimeInt..24 ||
                       specificTimeInt in 0..< endingTimeInt
                    ) {
                        result = true
                    }
                }
            }

            withContext(Dispatchers.IO) {
                offlineTimeRepository
                    .insertTime(
                        TimeEntity(
                            startTime = startTimeText,
                            endingTime = endingTimeText,
                            registeredTime = endingTimeText,
                            result = result,
                        )
                    )
            }
        }
    }
}

sealed interface RegistrationUiState {
    object Loading : RegistrationUiState

    data class Time(
        val time: List<TimeEntity>,
    ): RegistrationUiState

    object Empty : RegistrationUiState
}