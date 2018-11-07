package com.eslimaf.coroutines.ui.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eslimaf.coroutines.R
import com.eslimaf.coroutines.data.marvel.api.model.entity.Character
import com.eslimaf.coroutines.extensions.load

class TimelineListAdapter : RecyclerView.Adapter<TimelineListAdapter.CharacterViewHolder>() {
    private val characters = mutableListOf<Character>()
    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(character: Character)
    }

    fun setCharacters(newCharacters: List<Character>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.character_item_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return characters.count()
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position], onItemClickListener)
    }

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val containerView: View = view.findViewById(R.id.itemContainer)
        private val thumbnail: ImageView = view.findViewById(R.id.characterThumbnail)
        private val title: TextView = view.findViewById(R.id.characterName)
        private val desc: TextView = view.findViewById(R.id.characterDesc)

        fun bind(character: Character, listener: OnItemClickListener?) {
            if (!character.thumbnail.path.isEmpty()) {
                thumbnail.load(character.thumbnail.getStandardUrl())
            }
            title.text = character.name
            desc.text = character.description
            containerView.setOnClickListener {
                listener?.onItemClick(character)
            }
        }
    }
}