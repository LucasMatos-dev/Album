package com.example.album

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImageSaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_save)

        // Referenciar a imagem e o botão do layout
        val imageView = findViewById<ImageView>(R.id.image_to_save)
        val saveButton = findViewById<Button>(R.id.button_save_image)

        // Configurar a ação do botão de salvar
        saveButton.setOnClickListener {
            // Navegar para a SelecaoAlbumActivity para escolher um álbum
            val intent = Intent(this, SelecaoAlbumActivity::class.java)
            startActivity(intent)
        }
    }
}
