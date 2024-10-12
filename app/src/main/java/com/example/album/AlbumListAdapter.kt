package com.example.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlbumListAdapter(
    private val albuns: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        // Inflando o layout personalizado do item (item_album2.xml)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album2, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albuns[position]
        holder.bind(album)
    }

    override fun getItemCount(): Int = albuns.size

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val albumCover: ImageView = itemView.findViewById(R.id.albumCover)
        private val albumName: TextView = itemView.findViewById(R.id.albumName)

        fun bind(album: String) {
            albumName.text = album
            albumCover.setImageResource(R.drawable.imagem3) // Substitua pela imagem desejada

            // Definindo ação de clique
            itemView.setOnClickListener {
                onItemClick(album)
            }
        }
    }
}
