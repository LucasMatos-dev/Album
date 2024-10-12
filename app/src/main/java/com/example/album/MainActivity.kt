package com.example.album

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.album.com.example.album.AlbumAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        val albumList = mutableListOf<Album>() // Inicializa a lista vazia
    }

    private lateinit var recyclerAlbums: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerAlbums = findViewById(R.id.recycler_albums)
        searchView = findViewById(R.id.barraPesquisa)

        // Inicialize o Álbum Padrão se ele ainda não estiver na lista
        if (albumList.isEmpty()) {
            val albumPadrao = Album(
                id = 100, // ID único
                nome = "Padrão",
                imagemCapa = R.drawable.imagem4, // Use uma imagem padrão para a capa do álbum
                imagens = mutableListOf() // Lista vazia de imagens inicialmente
            )
            albumList.add(albumPadrao)
        }

        // Inicialize o Adapter com a lista atualizada de álbuns
        albumAdapter = AlbumAdapter(this, albumList)
        recyclerAlbums.adapter = albumAdapter
        recyclerAlbums.layoutManager = GridLayoutManager(this, 2)

        // Configurar o botão de adicionar álbum (popup)
        val buttonAddAlbum = findViewById<ImageButton>(R.id.buttonPopupCriarAlbun)
        buttonAddAlbum.setOnClickListener {
            showAddAlbumPopup()
        }

        // Configurar o listener da barra de pesquisa
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterAlbums(newText)
                return true
            }
        })
    }

    // Função para mostrar um popup de adicionar álbum
    private fun showAddAlbumPopup() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_album, null)
        dialogBuilder.setView(dialogView)

        val albumNameInput = dialogView.findViewById<EditText>(R.id.edit_album_name)

        dialogBuilder.setTitle("Adicionar Novo Álbum")
        dialogBuilder.setPositiveButton("Adicionar") { dialog, _ ->
            val albumName = albumNameInput.text.toString()
            if (albumName.isNotEmpty()) {
                val newAlbum = Album(
                    id = albumList.size + 1,
                    nome = albumName,
                    imagemCapa = R.drawable.imagem1, // Use uma imagem padrão para o álbum
                    imagens = mutableListOf() // Cria o álbum com uma lista de imagens vazia
                )
                albumList.add(newAlbum)
                albumAdapter.notifyItemInserted(albumList.size - 1)
            }
            dialog.dismiss()
        }

        dialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    // Função para filtrar os álbuns com base no texto de pesquisa
    private fun filterAlbums(query: String?) {
        val filteredAlbumList = if (query.isNullOrEmpty()) {
            albumList // Se não houver nada pesquisado, mostra todos os álbuns
        } else {
            albumList.filter {
                it.nome.contains(query, ignoreCase = true) // Filtrar por nome que contenha o texto pesquisado
            }
        }

        albumAdapter = AlbumAdapter(this, filteredAlbumList)
        recyclerAlbums.adapter = albumAdapter
    }
}