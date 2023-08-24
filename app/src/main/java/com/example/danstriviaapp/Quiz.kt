package com.example.danstriviaapp

import Constants.GeneralKnowledgeConstants.returnGK1
import Constants.Question
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*


class Quiz : AppCompatActivity() {
    var index = 0
    var correctOption = 0
    var questionOverFlag = false
    var questionList = arrayListOf<Question>()


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
        bt_continue.setOnClickListener{
            resetOptions()
        }
    }

    fun startQuiz(quizName:String){

        if (quizName == "General Knowledge 1"){
            questionList = returnGK1(this)
            questionList = ArrayList(questionList).apply { shuffle() }
            displayOptions()
        }
    }

    fun displayOptions(){
        val question = questionList[index]
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

        //Set all option buttons invisible, just so I can set the relevant options visible again
        bt_option_one.visibility = View.INVISIBLE
        bt_option_two.visibility = View.INVISIBLE
        bt_option_three.visibility = View.INVISIBLE
        bt_option_four.visibility = View.INVISIBLE
        bt_option.visibility = View.VISIBLE

        //Compare chosen option with actual correct option
        if(choice == correctOption){
            bt_option.setBackgroundColor(Color.parseColor("#00FF00"))
        }
        else{
            bt_option.setBackgroundColor(Color.parseColor("#FF0000"))
            if(correctOption == 0) {
                bt_option_one.setBackgroundColor(Color.parseColor("#00FF00"))
                bt_option_one.visibility = View.VISIBLE
            }
            else if(correctOption == 1) {
                bt_option_two.setBackgroundColor(Color.parseColor("#00FF00"))
                bt_option_two.visibility = View.VISIBLE
            }
            else if(correctOption == 2) {
                bt_option_three.setBackgroundColor(Color.parseColor("#00FF00"))
                bt_option_three.visibility = View.VISIBLE
            }
            else if(correctOption == 3) {
                bt_option_four.setBackgroundColor(Color.parseColor("#00FF00"))
                bt_option_four.visibility = View.VISIBLE
            }
        }

        //bt_continue.visibility
        bt_continue.visibility = View.VISIBLE
    }

    fun resetOptions(){
        bt_option_one.setBackgroundColor(Color.parseColor("#6200EE"))
        bt_option_two.setBackgroundColor(Color.parseColor("#6200EE"))
        bt_option_three.setBackgroundColor(Color.parseColor("#6200EE"))
        bt_option_four.setBackgroundColor(Color.parseColor("#6200EE"))
        bt_option_one.visibility = View.VISIBLE
        bt_option_two.visibility = View.VISIBLE
        bt_option_three.visibility = View.VISIBLE
        bt_option_four.visibility = View.VISIBLE

        bt_continue.visibility = View.INVISIBLE

        if(index == questionList.size-1) {
            //TODO Handle the end of the quiz
            println("END OF QUIZ I GUESS")
            return
        }

        questionOverFlag = false
        index += 1

        displayOptions()
    }

}