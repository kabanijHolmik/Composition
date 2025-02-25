package com.example.composition.domain.usecases

import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.repository.GameRepository

class GetGameSettingUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings{
        return repository.getGameSettings(level)
    }
}