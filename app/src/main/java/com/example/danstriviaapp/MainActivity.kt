package com.example.danstriviaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerviewtesting.Adapters.ItemAdapter
//import com.example.recyclerviewtesting.Adapters.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Creating the recyclerview
        recycler_view_items.layoutManager = GridLayoutManager(this,2)

        //adapter class is initialised and list is passed
        val itemAdapter = ItemAdapter(this,getItemsList(),getImageList(),true)

        recycler_view_items.adapter = itemAdapter

        //actionBar!!.setTitle("heading")
        supportActionBar!!.setTitle("Main Menu");

        //Code that handles when any of the categories are picked
        itemAdapter.setOnItemClickListener(object:ItemAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Code that runs when something is clicked

                //Working out what user has clicked
                val topicList = getItemsList()
                val topicName = topicList.get(position)

                val intent = Intent(this@MainActivity, Menu::class.java)
                intent.putExtra("topicName",topicName)
                startActivity(intent)
            }

        })

    }

    private fun getItemsList(): ArrayList<String>{
        val list = arrayListOf("General Knowledge","Capital\nCities","Periodic Table","US State Capitals","Geography","History","Film and Television","Sport","Science","Food and Drink")
        return list
    }
    private fun getImageList(): ArrayList<Int>{
        val images = arrayListOf<Int>(R.drawable.light,R.drawable.eiffeltower,R.drawable.periodictable,R.drawable.usa,R.drawable.earth,R.drawable.kinghenryviii,R.drawable.tv,R.drawable.sport,R.drawable.space,R.drawable.food)
        return images
    }
    /*
    fun getImagesList():ArrayList<Int>{
        val list = arrayListOf<Int>(R.drawable.light,R.drawable.eiffeltower,R.drawable.periodictable,R.drawable.usa,R.drawable.earth,R.drawable.kinghenryviii,R.drawable.tv,R.drawable.sport,R.drawable.space,R.drawable.food)
        return list
    }

     */

}