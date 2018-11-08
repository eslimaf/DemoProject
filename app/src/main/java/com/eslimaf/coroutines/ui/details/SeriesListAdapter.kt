package com.eslimaf.coroutines.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eslimaf.coroutines.R
import com.eslimaf.coroutines.data.marvel.api.model.entity.Series
import com.eslimaf.coroutines.extensions.load

class SeriesListAdapter : RecyclerView.Adapter<SeriesListAdapter.StoryViewHolder>() {
    private val storiesList = mutableListOf<Series>()

    fun setStories(series: List<Series>) {
        storiesList.apply {
            clear()
            addAll(series)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder =
        StoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.series_item_view,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = storiesList.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.onBind(storiesList[position])
    }

    class StoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val thumbnail = view.findViewById<ImageView>(R.id.storyItemThumbnail)
        private val title = view.findViewById<TextView>(R.id.storyItemTitle)
        fun onBind(series: Series) {
            title.text = series.title ?: "@null"
            series.thumbnail?.let { t -> thumbnail.load(t.getPortraitUrl()) }
        }
    }
}