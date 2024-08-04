package com.example.danstriviaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewtesting.Adapters.ItemAdapterPlain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import constants.QuizProgress
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapterPlain
    private lateinit var topicArray: ArrayList<ArrayList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Initial setup
        setupRecyclerView()

        // Load data and set up the RecyclerView
        loadDataAndSetupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        // Reload data and update the RecyclerView when the activity is resumed
        loadDataAndSetupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view_plain.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapterPlain(this, ArrayList())
        recycler_view_plain.adapter = itemAdapter

        itemAdapter.setOnItemClickListener(object : ItemAdapterPlain.onItemClickListener {
            override fun onItemClick(position: Int) {
                val titles = topicArray[0]
                val quizName = titles[position]
                val intent = Intent(this@Menu, Quiz::class.java)
                intent.putExtra("quizName", quizName)
                startActivity(intent)
            }
        })
    }

    private fun loadDataAndSetupRecyclerView() {
        val category = intent.getStringExtra("topicName")
        //TODO TOPICS SHOULD BE GATHERED SOMEWHERE PROPER BASED ON THE TOPIC NAME
        val quizNames : ArrayList<String>
        topicArray = if (category == "General Knowledge") {
            supportActionBar!!.title = "General Knowledge"
            val quizNames = arrayListOf("General Knowledge 1", "General Knowledge 2")
            getGeneralKnowledgeTopics(quizNames)
        }else if (category == "Capital Cities"){

            val quizNames = arrayListOf("Capital Cities 1", "Capital Cities 2")
            getGeneralKnowledgeTopics(quizNames)
        }

        else {
            supportActionBar!!.title = "Other"
            arrayListOf(arrayListOf("Not GK"), arrayListOf("null"),arrayListOf("null"))
        }

        // Update the adapter with the new data
        itemAdapter.updateData(topicArray)
    }

    private fun getGeneralKnowledgeTopics(quizNames: ArrayList<String>): ArrayList<ArrayList<String>> {
        val progress = arrayListOf<String>()
        val highScores = arrayListOf<String>()

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("QuizProgress", Context.MODE_PRIVATE)

        for (quizName in quizNames) {
            val quizProgressJson = sharedPreferences.getString(quizName, null)
            if (quizProgressJson != null) {
                val gson = Gson()
                val quizProgressType = object : TypeToken<QuizProgress>() {}.type
                val quizProgress: QuizProgress = gson.fromJson(quizProgressJson, quizProgressType)
                progress.add(quizProgress.progress)
            } else {
                progress.add("null")
            }

            // Now for high scores
            val highScore = sharedPreferences.getString(quizName + "HS", "null")
            highScores.add(highScore!!)
        }

        val combined = ArrayList<ArrayList<String>>()
        combined.add(quizNames)
        combined.add(progress)
        combined.add(highScores)
        return combined
    }

    fun clearAllSharedPreferences(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("QuizProgress", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
