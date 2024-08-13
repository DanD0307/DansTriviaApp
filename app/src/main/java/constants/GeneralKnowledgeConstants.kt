package constants

import android.content.Context
import android.content.res.AssetManager
import java.io.InputStream
import constants.Question as Question

object GeneralKnowledgeConstants {
    fun returnGK1(context: Context):ArrayList<Question> {
        val manager: AssetManager = context.getAssets()
        val IS : InputStream = manager.open("gk/gk1.txt")
        val list = returnArrayList(IS)
        return list
    }
    fun returnGK2(context: Context):ArrayList<Question> {
        val manager: AssetManager = context.getAssets()
        val IS : InputStream = manager.open("gk/gk2.txt")
        val list = returnArrayList(IS)
        return list
    }
    //-----FUNCTIONALITY FOR RETURNING A QUIZ WITH MULTIPLE OPTIONS-----
    fun returnQuiz(context: Context, quizPath:String):ArrayList<Question> {
        val manager: AssetManager = context.getAssets()
        val IS : InputStream = manager.open(quizPath)
        val list = returnArrayList(IS)
        return list
    }

    private fun returnArrayList(IS:InputStream):ArrayList<Question>{
        var list: ArrayList<Question> = arrayListOf<Question>()

        IS.bufferedReader().forEachLine {
            var question:Question
            var line = it
            var array = line.split("#").toTypedArray()
            val text = array[0]
            val options = listOf<String>(array[1],array[2],array[3],array[4])
            question = Question(text,options)
            list.add(question)

        }
        return list
    }


    //-----FUNCTIONALITY FOR RETURNING A QUIZ WITH ONE ANSWER OTHERWISE KNOWN AS A TYPED QUIZ-----
    //For MCQ I use data class called a question to store the question text and an array of the options
    //I reuse the data class for typed quizzes but instead of there being options they are alternative answers
    //For example different spellings.
    //However, some questions might only have one interpretable answer so I need a seperate function to deal with variablity in 'option' length

    fun returnTypedQuiz(context: Context, quizPath:String):ArrayList<Question> {
        val manager: AssetManager = context.getAssets()
        val IS : InputStream = manager.open(quizPath)
        val list = returnArrayListTyped(IS)
        return list
    }

    private fun returnArrayListTyped(IS: InputStream): ArrayList<Question> {
        val list: ArrayList<Question> = arrayListOf()

        IS.bufferedReader().forEachLine {
            val array = it.split("#").toTypedArray()
            val text = array[0]
            val options = array.slice(1 until array.size) //Get everything after the first element which is the question
            val question = Question(text, options)
            list.add(question)
        }
        return list
    }

}