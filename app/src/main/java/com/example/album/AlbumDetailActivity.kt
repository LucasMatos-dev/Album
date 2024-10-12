package com.example.album

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AlbumDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_album) // Utilizando o layout_album.xml

        // Recuperar o ID do álbum da intent
        val albumId = intent.getIntExtra("albumId", -1)

        // Buscar o álbum com o ID correspondente
        val album = getAlbumById(albumId)

        if (album != null) {
            // Atualizar o título do álbum
            val albumTitle = findViewById<TextView>(R.id.albumAberto)
            albumTitle.text = album.nome

            // Configurar o GridLayout para exibir as imagens do álbum
            val gridLayout = findViewById<GridLayout>(R.id.grid_layout)
            gridLayout.removeAllViews() // Limpar todas as views existentes no GridLayout

            if (album.imagens.isEmpty()) {
                // Se o álbum não tiver imagens, mostra apenas a mensagem
                val emptyMessage = TextView(this).apply {
                    text = "Nenhuma mídia adicionada ao álbum."
                    textSize = 16f
                    setPadding(16, 16, 16, 16)
                }
                gridLayout.addView(emptyMessage)
            } else {
                addImagesToGridLayout(gridLayout, album.imagens)
            }

            // Configurar o botão de voltar para finalizar a activity
            val backButton = findViewById<ImageButton>(R.id.button_back)
            backButton.setOnClickListener {
                finish() // Finaliza a activity e retorna à anterior
            }

            // Configurar o botão de editar
            val editButton = findViewById<ImageButton>(R.id.button_editar)
            editButton.setOnClickListener {
                showEditAlbumNamePopup(album, albumTitle)
            }

            // Configurar o botão de compartilhar
            val shareButton = findViewById<ImageButton>(R.id.button_compartilhar)
            shareButton.setOnClickListener {
                shareAlbum(album)
            }
        }
    }

    // Função para buscar o álbum por ID
    private fun getAlbumById(albumId: Int): Album? {
        return MainActivity.albumList.find { it.id == albumId }
    }

    // Função para mostrar o popup de edição de nome do álbum
    private fun showEditAlbumNamePopup(album: Album, albumTitle: TextView) {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_album_name, null)
        dialogBuilder.setView(dialogView)

        val albumNameInput = dialogView.findViewById<EditText>(R.id.edit_album_name)
        albumNameInput.setText(album.nome) // Preencher com o nome atual do álbum

        dialogBuilder.setTitle("Editar Nome do Álbum")
        dialogBuilder.setPositiveButton("Salvar") { dialog, _ ->
            val newName = albumNameInput.text.toString()
            if (newName.isNotEmpty()) {
                // Atualizar o nome do álbum diretamente no objeto
                album.nome = newName
                albumTitle.text = newName
            }
            dialog.dismiss()
        }

        dialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    // Função para compartilhar o álbum
    private fun shareAlbum(album: Album) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Confira o álbum: ${album.nome}")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Compartilhar álbum via"))
    }

    // Função para adicionar imagens ao GridLayout
    private fun addImagesToGridLayout(gridLayout: GridLayout, images: List<Int>) {
        gridLayout.removeAllViews() // Limpar qualquer imagem existente no layout

        // Para cada imagem no álbum, crie uma ImageView e adicione ao GridLayout
        for (imageResId in images) {
            val imageView = ImageView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = GridLayout.LayoutParams.WRAP_CONTENT
                    height = 300 // Altura fixa de 300 pixels
                    setMargins(8, 8, 8, 8) // Margens em pixels para separar as imagens
                }
                setImageResource(imageResId)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            gridLayout.addView(imageView)
        }
    }
}
