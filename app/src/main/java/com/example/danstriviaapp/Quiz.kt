package com.example.danstriviaapp

import constants.GeneralKnowledgeConstants.returnGK1
import constants.Question
import constants.QuizProgress
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz.*
import android.util.TypedValue
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

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
    val GREEN_COLOUR = "#00FF00"
    val RED_COLOUR = "#FF0000"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        //This gets the quizName (what quiz we're currently in, from the code)
        quizName = intent.getStringExtra("quizName").toString()
        println("Quiz QuizName: " + quizName)

        startQuiz(quizName)


        //Retrieve the primary color from the theme file. Necessary for dark mode differences
        val typedValue = TypedValue()
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, typedValue, true)
        primaryColour = typedValue.data

        initialiseButtonListeners()
    }


    private fun initialiseButtonListeners() {
        //First Button
        bt_option_one.setOnClickListener {
            if (endOfQuizFlag) {
                //User has requested to restart the quiz in full.
                resetQuiz()
                startQuiz(quizName)

            } else //User has selected answer option one
                clickedOption(0, bt_option_one)
        }

        //Second Button
        bt_option_two.setOnClickListener {
            if (endOfQuizFlag) {
                if (hundredPercentFlag) //If the user got 100 percent this button just says Exit so we exit the program
                    finish()
                else { //Now we know the user got some questions wrong so this button text says "Restart with incorrect only"
                    startQuizWrongAnswers()
                }
            } else
                clickedOption(1, bt_option_two)
        }

        //Third Button
        bt_option_three.setOnClickListener {
            if (endOfQuizFlag) { //If this button is visible at the end of the quiz it will display 'exit'. So we close the view
                finish()
            } else
                clickedOption(2, bt_option_three)
        }
        //Fourth Button
        bt_option_four.setOnClickListener {
            clickedOption(3, bt_option_four)
        }

        //Continue Button
        bt_continue.setOnClickListener {
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
        questionList = incorrectQuestionList
        resetQuiz()

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
    //'choice' is an integer value of 0,1,2,3
    //'bt_option' is a reference to the button the user clicked.
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
            bt_option.setBackgroundColor(Color.parseColor(GREEN_COLOUR))
        }
        else{
            bt_option.setBackgroundColor(Color.parseColor(RED_COLOUR)) //Set chosen option to red background
            incorrectQuestionList.add(questionList[index]) //Add the question to the list of incorrectly answered questions

            if(correctOption == 0) {
                bt_option_one.setBackgroundColor(Color.parseColor(GREEN_COLOUR))
                bt_option_one.visibility = View.VISIBLE
            }
            else if(correctOption == 1) {
                bt_option_two.setBackgroundColor(Color.parseColor(GREEN_COLOUR))
                bt_option_two.visibility = View.VISIBLE
            }
            else if(correctOption == 2) {
                bt_option_three.setBackgroundColor(Color.parseColor(GREEN_COLOUR))
                bt_option_three.visibility = View.VISIBLE
            }
            else if(correctOption == 3) {
                bt_option_four.setBackgroundColor(Color.parseColor(GREEN_COLOUR))
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
            //bt_option_three.visibility = View.VISIBLE
        }

        bt_option_four.visibility = View.INVISIBLE

    }

    private fun resetQuiz(){
        resetOptionButtons()
        endOfQuizFlag = false
        index = 0
        incorrectQuestionList = arrayListOf<Question>()
        progress_bar.progress = 0
    }

    //Gson is a library that is used to convert objects into into JSON
    private fun saveQuizProgress(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("QuizProgress", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Create a QuizProgress object
        val quizProgress = QuizProgress(
            quizName = quizName,
            questionList = questionList,
            incorrectQuestionList = incorrectQuestionList,
            index = index,
            questionOverFlag = questionOverFlag,
            endOfQuizFlag = endOfQuizFlag,
            hundredPercentFlag = hundredPercentFlag
        )

        // Convert the QuizProgress object to JSON
        val gson = Gson()
        val quizProgressJson = gson.toJson(quizProgress)

        // Save JSON string to SharedPreferences with a key based on the quiz name
        editor.putString(quizName, quizProgressJson)
        editor.apply()
    }
}