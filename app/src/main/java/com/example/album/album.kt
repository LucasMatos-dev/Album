package com.example.album // Certifique-se de usar o mesmo pacote

data class Album(
    val id: Int,
    var nome: String,
    val imagemCapa: Int, // ID da imagem de capa (ou qualquer imagem representativa)
    val imagens: MutableList<Int> // Garantir que imagens seja do tipo MutableList
)

