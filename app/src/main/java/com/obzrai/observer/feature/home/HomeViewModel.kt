package com.obzrai.observer.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obzrai.observer.core.model.SessionState
import com.obzrai.observer.core.storage.LibraryRepository
import com.obzrai.observer.core.storage.entity.SavedItemEntity
import com.obzrai.observer.feature.session.ObservationSessionController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

data class HomeUiState(
    val sessionState: SessionState = SessionState.STOPPED,
    val savedCount: Int = 0,
    val todayCount: Int = 0,
    val topicCount: Int = 0,
    val recentItems: List<SavedItemEntity> = emptyList(),
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionController: ObservationSessionController,
    libraryRepository: LibraryRepository,
) : ViewModel() {
    private val dayStart: Long =
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

    val uiState: StateFlow<HomeUiState> =
        combine(
            sessionController.state,
            libraryRepository.observeItemCount(),
            libraryRepository.observeTodayCount(dayStart),
            libraryRepository.observeEnabledTopicCount(),
            libraryRepository.observeRecent(5),
        ) { session, saved, today, topics, recent ->
            HomeUiState(
                sessionState = session,
                savedCount = saved,
                todayCount = today,
                topicCount = topics,
                recentItems = recent,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(),
        )

    fun onToggleObserve() {
        when (sessionController.state.value) {
            SessionState.ACTIVE -> sessionController.stop("user_home")
            SessionState.PAUSED -> sessionController.requestStart()
            SessionState.STOPPED,
            SessionState.NEEDS_PERMISSION,
            -> sessionController.requestStart()
            SessionState.STARTING,
            SessionState.STOPPING,
            -> Unit
        }
    }
}
