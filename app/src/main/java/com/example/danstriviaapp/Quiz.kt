package com.example.danstriviaapp

import Constants.GeneralKnowledgeConstants.returnGK1
import Constants.Question
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*
import android.content.res.TypedArray
import android.util.TypedValue

class Quiz : AppCompatActivity() {
    var index = 0
    var correctOption = 0
    var questionOverFlag = false
    var endOfQuizFlag = false
    var hundredPercentFlag = false
    var questionList = arrayListOf<Question>()
    var incorrectQuestionList = arrayListOf<Question>()

    var quizName = ""
    var primaryColour = 0
    val green = "#00FF00"
    val red = "#FF0000"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        //This gets the quizName (what quiz we're currently in, from the code)
        quizName = intent.getStringExtra("quizName").toString()
        println("Quiz QuizName: "+quizName)

        startQuiz(quizName)


        //COLOUR OBTAINING
        // Retrieve the primary color from the theme
        val typedValue = TypedValue()
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, typedValue, true)
        primaryColour = typedValue.data
        //


        //------Button Listeners-------

        //First Button
        bt_option_one.setOnClickListener{
            if(endOfQuizFlag){
                //User has requested to restart the quiz in full.
                nextQuizPrep()
                startQuiz(quizName)

            }
            else //User has selected answer option one
                clickedOption(0,bt_option_one)
        }

        //Second Button
        bt_option_two.setOnClickListener{
            if(endOfQuizFlag) {
                if(hundredPercentFlag) //If the user got 100 percent this button just says Exit so we exit the program
                    finish()

                else { //Now we know the user got some questions wrong so this button text says "Restart with incorrect only"
                    startQuizWrongAnswers()
                }
            }
            else
                clickedOption(1, bt_option_two)
        }

        //Third Button
        bt_option_three.setOnClickListener{
            if(endOfQuizFlag){ //If this button is visible at the end of the quiz it will display 'exit'. So we close the view
                finish()
            }
            else
                clickedOption(2, bt_option_three)
        }
        //Fourth Button
        bt_option_four.setOnClickListener{
            clickedOption(3, bt_option_four)
        }

        //Continue Button
        bt_continue.setOnClickListener{
            nextQuestion()
        }
    }


    private fun startQuiz(quizName:String){
        if (quizName == "General Knowledge 1"){
            questionList = returnGK1(this)
            questionList = ArrayList(questionList).apply { shuffle() }
            tv_quiz_progress.text = "0/${questionList.size}"
            progress_bar.max = questionList.size
            displayOptions()
        }
    }

    private fun startQuizWrongAnswers(){
        //Reset stuff
        resetOptionButtons()
        index = 0
        progress_bar.progress = 0

        questionList = incorrectQuestionList
        incorrectQuestionList = arrayListOf<Question>()
        questionList = ArrayList(questionList).apply { shuffle() }
        tv_quiz_progress.text = "0/${questionList.size}"
        progress_bar.max = questionList.size
        displayOptions()
    }

    private fun displayOptions(){
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
    private fun clickedOption(choice: Int, bt_option: Button){

        //This check is here for when the answers are showing red or green. Only lets you click once per question.
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
            bt_option.setBackgroundColor(Color.parseColor(green))
        }
        else{
            bt_option.setBackgroundColor(Color.parseColor(red)) //Set chosen option to red background
            incorrectQuestionList.add(questionList[index]) //Add the question to the list of incorrectly answered questions

            if(correctOption == 0) {
                bt_option_one.setBackgroundColor(Color.parseColor(green))
                bt_option_one.visibility = View.VISIBLE
            }
            else if(correctOption == 1) {
                bt_option_two.setBackgroundColor(Color.parseColor(green))
                bt_option_two.visibility = View.VISIBLE
            }
            else if(correctOption == 2) {
                bt_option_three.setBackgroundColor(Color.parseColor(green))
                bt_option_three.visibility = View.VISIBLE
            }
            else if(correctOption == 3) {
                bt_option_four.setBackgroundColor(Color.parseColor(green))
                bt_option_four.visibility = View.VISIBLE
            }
        }

        //bt_continue.visibility
        bt_continue.visibility = View.VISIBLE
    }

    private fun resetOptionButtons(){
        //println("primaryCOLOUR VARIABLE = "+primaryColour);
        bt_option_one.setBackgroundColor(primaryColour)
        bt_option_two.setBackgroundColor(primaryColour)
        bt_option_three.setBackgroundColor(primaryColour)
        bt_option_four.setBackgroundColor(primaryColour)
        bt_option_one.visibility = View.VISIBLE
        bt_option_two.visibility = View.VISIBLE
        bt_option_three.visibility = View.VISIBLE
        bt_option_four.visibility = View.VISIBLE
        bt_continue.visibility = View.INVISIBLE
        endOfQuizFlag = false
        questionOverFlag = false
    }

    private fun nextQuestion(){
        resetOptionButtons()

        index += 1
        progress_bar.progress = index
        tv_quiz_progress.text = "$index/${questionList.size}"

        if(index == questionList.size) {
            endQuiz()
            return
        }

        displayOptions()
    }

    //Function to be called when the user has completed all questions.
    private fun endQuiz(){
        endOfQuizFlag = true
        val amountOfQuestions = questionList.size
        val correctCount = amountOfQuestions - incorrectQuestionList.size
        tv_question.text = "You've reached the end of the quiz and scored $correctCount/$amountOfQuestions"

        // Check if the user got 100% correct and display buttons appropriately
        if (correctCount == amountOfQuestions) {
            hundredPercentFlag = true
            bt_option_one.text = "Restart Quiz"
            bt_option_two.text = "Exit Quiz"
            bt_option_three.visibility = View.INVISIBLE
        } else {
            bt_option_one.text = "Restart Quiz"
            bt_option_two.text = "Redo Quiz With Incorrect Answers Only"
            bt_option_three.text = "Exit Quiz"
            bt_option_three.visibility = View.VISIBLE
        }

        bt_option_four.visibility = View.INVISIBLE

    }

    private fun nextQuizPrep(){
        resetOptionButtons()
        index = 0
        incorrectQuestionList = arrayListOf<Question>()
        progress_bar.progress = 0
    }

}