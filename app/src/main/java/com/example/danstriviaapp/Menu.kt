package com.example.danstriviaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtesting.Adapters.ItemAdapter
import com.example.recyclerviewtesting.Adapters.ItemAdapterPlain
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var topicName = intent.getStringExtra("topicName")
        var topicArray : ArrayList<String>
        if(topicName == "General Knowledge"){
            supportActionBar!!.setTitle("General Knowledge");
            topicArray = getGeneralKnowledgeTopics()
        }
        else{
            supportActionBar!!.setTitle("Other");
            topicArray = arrayListOf("Not GK")
        }




        //Creating the recyclerview
        recycler_view_plain.layoutManager = LinearLayoutManager(this)

        //adapter class is initialised and list is passed
        val itemAdapter = ItemAdapterPlain(this,topicArray)

        recycler_view_plain.adapter = itemAdapter

        //Code that handles when any of the categories are picked
        itemAdapter.setOnItemClickListener(object:ItemAdapterPlain.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Here we decide whether we should launch another menu or the quiz

                //Find Quiz Name
                val quizName = topicArray[position]
                //println("quizName:"+quizName)

                val intent = Intent(this@Menu, Quiz::class.java)
                intent.putExtra("quizName",quizName)
                startActivity(intent)
            }

        })

    }


    //TODO FIll in position and retrieve value specific to what user clicked in screen before!
    fun getGeneralKnowledgeTopics(): ArrayList<String> {
        return arrayListOf<String>("General Knowledge 1","General Knowledge 2")
    }
}