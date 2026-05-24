package br.ufpr.trab1harrypotterapi

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetalhesFeiticoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalhes_feitico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nome = intent.getStringExtra("NOME") ?: "Desconhecido"
        val descricao = intent.getStringExtra("DESCRICAO") ?: "Sem descrição disponível"

        findViewById<TextView>(R.id.tvNomeFeitico).text = nome
        findViewById<TextView>(R.id.tvDescricaoFeitico).text = descricao
    }
}
