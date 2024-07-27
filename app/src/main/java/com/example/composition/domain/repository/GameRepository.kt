package com.example.composition.domain.repository

import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.entities.Question

interface GameRepository {
    fun getGameSettings(level: Level): GameSettings
    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
}