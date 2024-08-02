package com.example.composition.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.entities.Question
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingUseCase

class GameViewModel: ViewModel() {
    private val repository = GameRepositoryImpl

    private val getGameSettingsUseCase = GetGameSettingUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)

    private val _formatedTime = MutableLiveData<String>()
    val formatedTime: LiveData<String>
        get() = _formatedTime

    private var timer: CountDownTimer? = null

    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers
    
    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private fun generateQuestion(){
        _question.value = generateQuestionUseCase.invoke(gameSettings.maxSumValue)
    }

    fun chooseAnswer(number: Int){
        checkAnswer(number)

        generateQuestion()

    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) countOfRightAnswers++
        countOfQuestions++
    }

    fun startGame(level: Level){
        getGameSettings(level)
        startTimer()
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
    }

    private fun startTimer(){
        timer = object : CountDownTimer(gameSettings.gameTimeInSeconds* MILLIS_IN_SECONDS, MILLIS_IN_SECONDS){
            override fun onTick(p0: Long) {
                _formatedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }

        timer?.start()
    }

    private fun formatTime(millis: Long): String{
        val seconds = millis/ MILLIS_IN_SECONDS
        val minutes = seconds/ SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun finishGame(){}

    companion object{
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}