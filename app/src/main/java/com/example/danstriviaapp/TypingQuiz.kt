package com.example.danstriviaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import constants.GeneralKnowledgeConstants
import constants.Question
import kotlinx.android.synthetic.main.activity_typing_quiz.*
import kotlinx.android.synthetic.main.activity_typing_quiz.bt_continue
import kotlinx.android.synthetic.main.activity_typing_quiz.tv_question
import kotlinx.android.synthetic.main.activity_typing_quiz.tv_quiz_progress

class TypingQuiz : AppCompatActivity() {

    var index = 0
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
        //Submit Button
        bt_submit.setOnClickListener {

            if(questionOverFlag){
                bt_submit.text = "SUBMIT"
                questionOverFlag = false
                et_answer.setVisibility(View.VISIBLE)
                et_answer.requestFocus() //This makes the user ready to type in the next answer as soon as the ET reappears
                nextQuestion()
            }

            //If user clicks submit we check to see if the answer is correct, remove the text from the ET and display green or red and show correct answer if applicable
            else if(et_answer.text.toString() != ""){
                checkAnswer()
                et_answer.setText("")
                et_answer.setVisibility(View.INVISIBLE)
            }
        }

        //Continue Button MIGHT BE DISCONTINUED
        bt_continue.setOnClickListener {
            //Show the next question and then make the button invisible again
            bt_continue.setVisibility(View.GONE);
            et_answer.setVisibility(View.VISIBLE)
            nextQuestion()
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

    private fun nextQuestion(){
        index += 1
        progressBar.progress = index
        tv_quiz_progress.text = "$index/${questionList.size}"

        if(index == questionList.size) {
            endQuiz()
            return
        }
        displayQuestion()
    }

    private fun checkAnswer(){
        val usersAnswer = et_answer.text.toString().trim().lowercase()//Trim removes whitespace at start and end of answer
        val question = questionList[index]
        val displayAnswer = question.options[0]
        val answers = question.options.map { it.lowercase() }

        //If answer is correct:
        if(usersAnswer in answers){
            println("CORRECT,ITS RIGHT")
            tv_question.text = "Correct!"


        }
        else{
            tv_question.text = "Incorrect. The correct answer is: "+displayAnswer
            incorrectQuestionList.add(question)
        }

        questionOverFlag = true
        bt_submit.text = "CONTINUE"
    }

    private fun endQuiz(){
        tv_question.text = "QUIZ IS OVER"
    }

}