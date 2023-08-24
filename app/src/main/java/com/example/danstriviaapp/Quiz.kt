package com.example.danstriviaapp

import Constants.GeneralKnowledgeConstants.returnGK1
import Constants.Question
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.Collections.shuffle

var index = 0

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
            displayOptions(questionList)
        }
    }

    fun displayOptions(questionList:ArrayList<Question>){
        val randomQuestions = ArrayList(questionList).apply { shuffle() }
        val question = randomQuestions[index]
        val questionText = question.questionText
        val options = question.options
        val correctOption = question.correctAnswer

        //Change the question to display the current question
        tv_question.text = questionText

        //Change the option buttons to the different answer options
        bt_option_one.text = options[0]
        bt_option_two.text = options[1]
        bt_option_three.text = options[2]
        bt_option_four.text = options[3]

    }
}