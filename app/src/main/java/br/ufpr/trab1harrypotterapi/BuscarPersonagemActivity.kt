package br.ufpr.trab1harrypotterapi

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.ufpr.trab1harrypotterapi.network.RetrofitClient
import coil.load
import kotlinx.coroutines.launch

class BuscarPersonagemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_personagem)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etId = findViewById<EditText>(R.id.etIdPersonagem)
        val btnPesquisar = findViewById<Button>(R.id.btnPesquisar)
        val layoutResultado = findViewById<View>(R.id.layoutResultado)
        val ivPersonagem = findViewById<ImageView>(R.id.ivPersonagem)
        val tvNome = findViewById<TextView>(R.id.tvNome)
        val tvEspecie = findViewById<TextView>(R.id.tvEspecie)
        val tvCasa = findViewById<TextView>(R.id.tvCasa)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnPesquisar.setOnClickListener {
            val id = etId.text.toString().trim()
            if (id.isNotEmpty()) {
                buscarPersonagem(id, progressBar, layoutResultado, ivPersonagem, tvNome, tvEspecie, tvCasa)
            } else {
                Toast.makeText(this, getString(R.string.msg_informe_campo), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buscarPersonagem(
        id: String,
        progressBar: ProgressBar,
        layoutResultado: View,
        ivPersonagem: ImageView,
        tvNome: TextView,
        tvEspecie: TextView,
        tvCasa: TextView
    ) {
        progressBar.visibility = View.VISIBLE
        layoutResultado.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.service.getCharacterById(id)
                if (response.isNotEmpty()) {
                    val personagem = response[0]
                    tvNome.text = personagem.name
                    tvEspecie.text = getString(R.string.label_especie, personagem.species ?: "N/A")
                    tvCasa.text = getString(R.string.label_casa, personagem.house ?: "N/A")
                    ivPersonagem.load(personagem.image) {
                        crossfade(true)
                        placeholder(android.R.drawable.ic_menu_gallery)
                        error(android.R.drawable.ic_menu_report_image)
                    }
                    layoutResultado.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@BuscarPersonagemActivity, getString(R.string.msg_nao_encontrado), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@BuscarPersonagemActivity, getString(R.string.msg_erro_busca, e.message), Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
