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

    private fun returnArrayList(IS:InputStream):ArrayList<Question>{
        var list: ArrayList<Question> = arrayListOf<Question>()

        IS.bufferedReader().forEachLine {
            var question:Question
            var line = it
            var array = line.split("#").toTypedArray()
            val text = array[0]
            val options = listOf<String>(array[1],array[2],array[3],array[4])
            val answer = array[5].toInt()
            question = Question(text,options,answer)
            list.add(question)

        }

        return list
    }
}