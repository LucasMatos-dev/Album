package com.example.album

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelecaoAlbumActivity : AppCompatActivity() {
    private val listaAlbuns = mutableListOf("Álbum Padrão")
    private lateinit var adapter: AlbumListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecao_album)

        // Inicializando o botão de voltar
        val botaoVoltar: ImageButton = findViewById(R.id.botaoVoltar)
        botaoVoltar.setOnClickListener {
            finish() // Volta para a atividade anterior
        }

        // Inicializando RecyclerView e configurando uma lista de álbuns fictícios
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewAlbuns)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AlbumListAdapter(listaAlbuns) { nomeAlbum: String ->
            Toast.makeText(this, "Salvo no álbum $nomeAlbum", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Inicializando botão de criar novo álbum
        val botaoCriarAlbum: Button = findViewById(R.id.botaoCriarAlbum)
        botaoCriarAlbum.setOnClickListener {
            mostrarPopupCriacaoAlbum()
        }
    }

    // Função para mostrar o pop-up de criação de um novo álbum
    private fun mostrarPopupCriacaoAlbum() {
        // Inflar o layout do pop-up
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_criar_album, null)
        val editTextNomeAlbum = view.findViewById<EditText>(R.id.editTextNomeAlbum)

        // Criar o AlertDialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Criar Novo Álbum")
            .setView(view)
            .setPositiveButton("Criar") { _, _ ->
                val nomeAlbum = editTextNomeAlbum.text.toString()
                if (nomeAlbum.isNotBlank()) {
                    listaAlbuns.add(nomeAlbum)
                    adapter.notifyItemInserted(listaAlbuns.size - 1)
                    Toast.makeText(this, "Álbum '$nomeAlbum' criado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "O nome do álbum não pode estar vazio.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
}
