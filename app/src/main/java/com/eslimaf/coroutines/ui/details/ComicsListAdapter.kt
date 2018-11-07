package com.eslimaf.coroutines.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eslimaf.coroutines.R
import com.eslimaf.coroutines.data.marvel.api.model.entity.Comic
import com.eslimaf.coroutines.extensions.load

class ComicsListAdapter : RecyclerView.Adapter<ComicsListAdapter.ComicCardHolder>() {
    private var comicsList = mutableListOf<Comic>()
    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(character: Comic)
    }

    fun setComics(comics: List<Comic>) {
        comicsList.apply {
            clear()
            addAll(comics)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicCardHolder {
        return ComicCardHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comic_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    override fun onBindViewHolder(holder: ComicCardHolder, position: Int) {
        holder.bind(comicsList[position], onItemClickListener)
    }

    class ComicCardHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val containerView = view.findViewById<View>(R.id.itemContainerView)
        private val thumbnail = view.findViewById<ImageView>(R.id.comicItemThumbnail)
        private val title = view.findViewById<TextView>(R.id.comicItemTitle)

        fun bind(comic: Comic, listener: OnItemClickListener?) {
            title.text = comic.title
            thumbnail.load(comic.thumbnail.getPortraitUrl())
            listener?.let { l ->
                containerView.setOnClickListener {
                    l.onItemClick(comic)
                }
            }
        }
    }
}