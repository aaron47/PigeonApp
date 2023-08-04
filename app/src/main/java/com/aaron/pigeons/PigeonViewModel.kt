package com.aaron.pigeons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaron.pigeons.domain.repository.PigeonRepository
import com.aaron.pigeons.remote.dto.PigeonDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PigeonUiState(
    val pigeons: List<PigeonDto> = emptyList(),
    val selectedCategory: String? = null,
) {
    val categories = pigeons.map { it.category }.toSet()
    val selectedPigeons = pigeons.filter { it.category == selectedCategory }
}


@HiltViewModel
class PigeonViewModel @Inject() constructor(private val pigeonRepository: PigeonRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<PigeonUiState>(PigeonUiState())
    val uiState = _uiState.asStateFlow()


    init {
        updatePigeons()
    }

    fun selectCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }

    private fun updatePigeons() {
        viewModelScope.launch {
            val pigeons = getPigeons()
            _uiState.update {
                it.copy(pigeons = pigeons)
            }
        }
    }

    private suspend fun getPigeons(): List<PigeonDto> {
        return pigeonRepository.getPigeons()
    }
}