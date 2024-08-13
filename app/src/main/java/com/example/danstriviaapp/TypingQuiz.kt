package com.example.danstriviaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import constants.GeneralKnowledgeConstants
import constants.Question
import kotlinx.android.synthetic.main.activity_typing_quiz.*

class TypingQuiz : AppCompatActivity() {

    var index = 0
    var correctOption = 0
    var questionOverFlag = false
    var endOfQuizFlag = false
    var hundredPercentFlag = false
    var inSubQuiz = false
    var questionList = arrayListOf<Question>()
    var incorrectQuestionList = arrayListOf<Question>()

    var quizName = ""
    var primaryColour = 0
    val GREEN_COLOUR = "#00FF00"
    val RED_COLOUR = "#FF0000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_typing_quiz)

        //This gets the quizName (what quiz we're currently in, from the code)
        quizName = intent.getStringExtra("quizName").toString()
        println("THE QUIZNAME IS: " + quizName)

        initialiseButtonListeners()
        startQuiz(quizName)

    }

    private fun initialiseButtonListeners() {
        //First Button
        bt_submit.setOnClickListener {
            //If user clicks submit we check to see if the answer is correct, remove the text from the ET and display green or red and show correct answer if applicable
            if(et_answer.text.toString() != ""){
                checkAnswer()
            }

        }
    }

    private fun startQuiz(quizName:String){
        if (quizName == "Europe Type the Capital") {
            questionList = GeneralKnowledgeConstants.returnTypedQuiz(this,"capitalcities/europeTypeTheCapital.txt")
        }

        questionList = ArrayList(questionList).apply { shuffle() }
        tv_quiz_progress.text = "0/${questionList.size}"
        progressBar.max = questionList.size
        displayQuestion()
    }

    private fun displayQuestion(){

        val question = questionList[index]
        tv_question.text = question.questionText

    }

    private fun checkAnswer(){
        //TODO I need a way of consistently making the answers lowercase so it compares properly in the if statement, but also displaying the answer in true form
        val usersAnswer = et_answer.text.toString().lowercase()
        val question = questionList[index]
        val displayAnswer = question.options[0]
        val answers = question.options.map { it.lowercase() }

        if(usersAnswer in answers){

            println("CORRECT,ITS RIGHT")
            tv_question.text = "Correct!"

        }
        else{
            tv_question.text = "Incorrect. The correct answer is: "+displayAnswer

        }



    }


}