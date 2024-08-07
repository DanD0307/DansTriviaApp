package com.example.danstriviaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewtesting.Adapters.ItemAdapter
import com.example.recyclerviewtesting.Adapters.ItemAdapterPlain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import constants.QuizProgress
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*

class ImageMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Creating the recyclerview
        recycler_view_items.layoutManager = GridLayoutManager(this,2)

        //adapter class is initialised and list is passed
        val itemAdapter = ItemAdapter(this,getItemsList(),getImageList(),true)

        recycler_view_items.adapter = itemAdapter

        //actionBar!!.setTitle("heading")
        supportActionBar!!.setTitle("Capital Cities");

        //Code that handles when any of the categories are picked
        itemAdapter.setOnItemClickListener(object:ItemAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Code that runs when something is clicked

                //Working out what user has clicked
                val topicList = getItemsList()
                val topicName = topicList.get(position)

                val intent = Intent(this@ImageMenu, Menu::class.java)
                intent.putExtra("topicName",topicName)
                startActivity(intent)
            }

        })

    }

    private fun getItemsList(): ArrayList<String>{
        val list = arrayListOf("Europe","Asia","Africa","Oceania","North America","South America")
        return list
    }
    private fun getImageList(): ArrayList<Int>{
        val images = arrayListOf<Int>(R.drawable.light,R.drawable.eiffeltower,R.drawable.periodictable,R.drawable.usa,R.drawable.earth,R.drawable.kinghenryviii)
        return images
    }

    fun clearAllSharedPreferences(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("QuizProgress", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
