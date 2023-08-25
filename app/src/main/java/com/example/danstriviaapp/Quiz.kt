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
    var endOfQuizFlag = false
    var questionList = arrayListOf<Question>()
    var incorrectQuestionList = arrayListOf<Question>()

    var quizName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        quizName = intent.getStringExtra("quizName").toString()
        println("Quiz QuizName: "+quizName)

        startQuiz(quizName)

        //Button Listeners
        bt_option_one.setOnClickListener{
            if(endOfQuizFlag){
                //User has requested to restart the quiz in full.
                resetOptionButtons()
                index = 0
                incorrectQuestionList = arrayListOf<Question>()
                startQuiz(quizName)

            }
            else //User has selected answer option one
                clickedOption(0,bt_option_one)
        }
        bt_option_two.setOnClickListener{
            if(endOfQuizFlag) {
                //TODO Allow the user to restart the quiz with incorrect questions only
            }
            else
                clickedOption(1, bt_option_two)
        }
        bt_option_three.setOnClickListener{
            if(endOfQuizFlag){
                finish()
            }
            else
                clickedOption(2, bt_option_three)
        }
        bt_option_four.setOnClickListener{
            clickedOption(3, bt_option_four)
        }
        bt_continue.setOnClickListener{
            nextQuestion()
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

    //Handles the behaviour for when the user clicks an answer option.
    //Determines whether they've picked the correct answer or not.
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
        //If chosen option is correct, turn it green
        //If incorrect turn it red, and the correct option green so the user can know the right answer.
        if(choice == correctOption){
            bt_option.setBackgroundColor(Color.parseColor("#00FF00"))
        }
        else{
            bt_option.setBackgroundColor(Color.parseColor("#FF0000")) //Set chosen option to red background
            incorrectQuestionList.add(questionList[index]) //Add the question to the list of incorrectly answered questions

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

    fun resetOptionButtons(){
        bt_option_one.setBackgroundColor(Color.parseColor("#6200EE"))
        bt_option_two.setBackgroundColor(Color.parseColor("#6200EE"))
        bt_option_three.setBackgroundColor(Color.parseColor("#6200EE"))
        bt_option_four.setBackgroundColor(Color.parseColor("#6200EE"))
        bt_option_one.visibility = View.VISIBLE
        bt_option_two.visibility = View.VISIBLE
        bt_option_three.visibility = View.VISIBLE
        bt_option_four.visibility = View.VISIBLE
        bt_continue.visibility = View.INVISIBLE
        endOfQuizFlag = false
        questionOverFlag = false
    }

    fun nextQuestion(){
        resetOptionButtons()

        if(index == questionList.size-1) {
            endQuiz()
            return
        }

        index += 1

        displayOptions()
    }

    //Function to be called when the user has completed all questions.
    fun endQuiz(){
        endOfQuizFlag = true
        val amountOfQuestions = questionList.size
        val correctCount = amountOfQuestions - incorrectQuestionList.size
        tv_question.text = "You've reached the end of the quiz and scored "+correctCount+"/"+amountOfQuestions
        bt_option_one.text = "Restart Quiz"
        bt_option_two.text = "Redo Quiz With Incorrect Answers Only"
        bt_option_three.text = "Exit Quiz"
        bt_option_four.visibility = View.INVISIBLE
    }

}