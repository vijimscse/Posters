package com.diagnal.movies.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.diagnal.movies.R
import com.diagnal.movies.data.ContentItem

class ItemAdapter:
    PagedListAdapter<ContentItem, ItemAdapter.ContentItemViewHolder> {
    private var mCtx: Context

    constructor(context: Context): super(object: DiffUtil.ItemCallback<ContentItem>() {
        override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
            return oldItem == newItem
        }

    }) {
        mCtx = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentItemViewHolder {
        Log.d("TAG", "onCreateViewHolder")
        val view: View =
            LayoutInflater.from(mCtx).inflate(R.layout.item_row, parent, false)
        return ContentItemViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ContentItemViewHolder, position: Int) {
        Log.d("TAG", "onbind")
        val item: ContentItem? = getItem(position)
        if (item != null) {
            holder.title.text = item.name
            val resourceID: Int = mCtx.resources.getIdentifier(
                item.posterImage?.replace(".jpg", ""),
                "drawable",
                mCtx.packageName
            )
            holder.poster.setImageResource(resourceID)
        } else {
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show()
        }
    }

    class ContentItemViewHolder(itemView: View) : ViewHolder(itemView) {
        val title: AppCompatTextView = itemView.findViewById(R.id.movieTitle)
        val poster: ImageView = itemView.findViewById(R.id.moviePoster)
    }
}