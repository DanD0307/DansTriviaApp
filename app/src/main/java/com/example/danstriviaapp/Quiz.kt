package com.example.danstriviaapp

import Constants.GeneralKnowledgeConstants.returnGK1
import Constants.Question
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*


class Quiz : AppCompatActivity() {
    var index = 0
    var correctOption = 0
    var questionOverFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val quizName:String = intent.getStringExtra("quizName").toString()
        println("Quiz QuizName: "+quizName)

        startQuiz(quizName)

        //Button Listeners
        bt_option_one.setOnClickListener{
            clickedOption(0,bt_option_one)
        }
        bt_option_two.setOnClickListener{
            clickedOption(1, bt_option_two)
        }
        bt_option_three.setOnClickListener{
            clickedOption(2, bt_option_three)
        }
        bt_option_four.setOnClickListener{
            clickedOption(3, bt_option_four)
        }
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

        //Here I randomise the options by obtaining what the correct answer is in plaintext.
        //Then I randomise the options and obtain the new index of the correct answer.
        var options = question.options
        correctOption = question.correctAnswer
        val correctAnswer = options[correctOption]
        options = ArrayList(question.options).apply { shuffle() }
        correctOption = options.indexOf(correctAnswer)

        //This allows me to have the index CorrectOption to check against the button presses later in the code.

        //Change the question to display the current question
        tv_question.text = questionText

        //Change the option buttons to the different answer options
        bt_option_one.text = options[0]
        bt_option_two.text = options[1]
        bt_option_three.text = options[2]
        bt_option_four.text = options[3]

    }

    fun clickedOption(choice: Int, bt_option: Button){

        if(questionOverFlag)
            return

        questionOverFlag = true

        //Compare chosen option with actual correct option
        if(choice == correctOption){
            bt_option.setBackgroundColor(Color.parseColor("#00FF00"))
        }
        else{
            bt_option.setBackgroundColor(Color.parseColor("#FF0000"))
            if(correctOption == 0)
                bt_option_one.setBackgroundColor(Color.parseColor("#00FF00"))
            else if(correctOption == 1)
                bt_option_two.setBackgroundColor(Color.parseColor("#00FF00"))
            else if(correctOption == 2)
                bt_option_three.setBackgroundColor(Color.parseColor("#00FF00"))
            else if(correctOption == 3)
                bt_option_four.setBackgroundColor(Color.parseColor("#00FF00"))
        }
    }

}