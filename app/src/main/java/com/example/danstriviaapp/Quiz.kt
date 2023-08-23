package com.example.danstriviaapp

import Constants.GeneralKnowledgeConstants.returnGK1
import Constants.Question
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Quiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val quizName:String = intent.getStringExtra("quizName").toString()
        println("Quiz QuizName: "+quizName)

        startQuiz(quizName)
    }

    fun startQuiz(quizName:String){

        var questionList = arrayListOf<Question>()

        if (quizName == "General Knowledge 1"){
            questionList = returnGK1(this)
            val questionText = questionList[0].questionText
            println("Quiz Object: "+questionText)
        }
    }
}