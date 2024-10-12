package com.example.album.com.example.album

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.album.Album
import com.example.album.AlbumDetailActivity
import com.example.album.R

class AlbumAdapter(
    private val context: Context,
    private val albumList: List<Album>
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.textAlbumName.text = album.nome
        holder.imageAlbum.setImageResource(album.imagemCapa)

        // Implementar o clique no item para abrir os detalhes do álbum
        holder.itemView.setOnClickListener {
            val intent = Intent(context, AlbumDetailActivity::class.java)
            intent.putExtra("albumId", album.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = albumList.size

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageAlbum: ImageView = itemView.findViewById(R.id.image_album)
        val textAlbumName: TextView = itemView.findViewById(R.id.text_album_name)
    }
}
