package com.akhenaton.scanrateitapp.common.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.akhenaton.scanrateitapp.R
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel

class RatingAdapter(private val reviewsList: List<ReviewModel>) :
    RecyclerView.Adapter<RatingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rating_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviewsList[position]
        holder.txtName.text = review.product
        holder.ratingBar.rating = review.rating
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_item_rating_name)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rbr_item_rating)
    }
}
