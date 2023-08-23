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

class ItemAdapterPlain(val context: Context, val items: ArrayList<String>) :
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

        val item = items.get(position)
        println("TEST")

        holder.tvTitle.text = item
        holder.tvProgress.text = "Progress : 1/15"
        holder.tvHighScore.text = "High Score : 5/15"

    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return items.size
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
}
