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

class BuscarProfessorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_professor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNome = findViewById<EditText>(R.id.etNomeProfessor)
        val btnPesquisar = findViewById<Button>(R.id.btnPesquisar)
        val layoutResultado = findViewById<View>(R.id.layoutResultado)
        val ivProfessor = findViewById<ImageView>(R.id.ivProfessor)
        val tvNome = findViewById<TextView>(R.id.tvNome)
        val tvNomesAlternativos = findViewById<TextView>(R.id.tvNomesAlternativos)
        val tvEspecie = findViewById<TextView>(R.id.tvEspecie)
        val tvCasa = findViewById<TextView>(R.id.tvCasa)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnPesquisar.setOnClickListener {
            val nome = etNome.text.toString().trim()
            if (nome.isNotEmpty()) {
                buscarProfessor(nome, progressBar, layoutResultado, ivProfessor, tvNome, tvNomesAlternativos, tvEspecie, tvCasa)
            } else {
                Toast.makeText(this, "Informe o nome do professor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buscarProfessor(
        nomeBusca: String,
        progressBar: ProgressBar,
        layoutResultado: View,
        ivProfessor: ImageView,
        tvNome: TextView,
        tvNomesAlternativos: TextView,
        tvEspecie: TextView,
        tvCasa: TextView
    ) {
        progressBar.visibility = View.VISIBLE
        layoutResultado.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val staff = RetrofitClient.service.getStaff()
                val professor = staff.find { it.name.contains(nomeBusca, ignoreCase = true) }

                if (professor != null) {
                    tvNome.text = professor.name
                    tvNomesAlternativos.text = "Nomes alternativos: ${professor.alternateNames?.joinToString(", ") ?: "N/A"}"
                    tvEspecie.text = "Espécie: ${professor.species ?: "N/A"}"
                    tvCasa.text = "Casa: ${professor.house ?: "N/A"}"
                    ivProfessor.load(professor.image) {
                        crossfade(true)
                        placeholder(android.R.drawable.ic_menu_gallery)
                        error(android.R.drawable.ic_menu_report_image)
                    }
                    layoutResultado.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@BuscarProfessorActivity, "Professor não encontrado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@BuscarProfessorActivity, "Erro ao buscar: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
