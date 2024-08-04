package com.example.recyclerviewtesting.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.danstriviaapp.R
import kotlinx.android.synthetic.main.item_custom_row.view.*
import kotlinx.android.synthetic.main.item_custom_row.view.card_view_item
import kotlinx.android.synthetic.main.item_custom_row_plain.view.*

class ItemAdapterPlain(val context: Context, var items: ArrayList<ArrayList<String>>) :
    RecyclerView.Adapter<ItemAdapterPlain.ViewHolder>() {

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{

        fun onItemClick(position:Int)

    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener = listener
    }

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var a : View

        a = LayoutInflater.from(context).inflate(
            R.layout.item_custom_row_plain,
            parent,
            false
        )
        return ViewHolder(a,mListener)
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val titles = items[0]
        val progressList = items[1]
        val highScores = items[2]

        //println("TITLES: "+titles)
        val title = titles.get(position)
        var progress = progressList.get(position)
        if(progress == "null")
            progress = ""

        val highScore = highScores.get(position)

        holder.tvTitle.text = title
        holder.tvProgress.text = progress

        if(highScore != "null")
            holder.tvHighScore.text = "High-Score : "+highScore //highScore variable is in format 5/10
        else
            holder.tvHighScore.text = ""

    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return items[0].size
    }


    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */

    class ViewHolder(view: View,listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvTitle = view.tv_title
        val tvProgress = view.tv_progress
        val tvHighScore = view.tv_highscore

        val cardViewItem = view.card_view_item


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateData(newItems: ArrayList<ArrayList<String>>) {
        this.items = newItems
        notifyDataSetChanged()
    }

}
