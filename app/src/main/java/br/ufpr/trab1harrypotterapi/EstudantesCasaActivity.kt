package br.ufpr.trab1harrypotterapi

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpr.trab1harrypotterapi.adapter.EstudanteAdapter
import br.ufpr.trab1harrypotterapi.network.RetrofitClient
import kotlinx.coroutines.launch

class EstudantesCasaActivity : AppCompatActivity() {

    private lateinit var adapter: EstudanteAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estudantes_casa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressBar)
        val rvEstudantes = findViewById<RecyclerView>(R.id.rvEstudantes)
        val rgCasas = findViewById<RadioGroup>(R.id.rgCasas)

        adapter = EstudanteAdapter(emptyList())
        rvEstudantes.layoutManager = LinearLayoutManager(this)
        rvEstudantes.adapter = adapter

        rgCasas.setOnCheckedChangeListener { _, checkedId ->
            val casa = when (checkedId) {
                R.id.rbGryffindor -> "gryffindor"
                R.id.rbSlytherin -> "slytherin"
                R.id.rbHufflepuff -> "hufflepuff"
                R.id.rbRavenclaw -> "ravenclaw"
                else -> ""
            }
            if (casa.isNotEmpty()) {
                buscarEstudantes(casa)
            }
        }
    }

    private fun buscarEstudantes(casa: String) {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val estudantes = RetrofitClient.service.getCharactersByHouse(casa)
                adapter.updateData(estudantes.filter { it.hogwartsStudent })
            } catch (e: Exception) {
                Toast.makeText(this@EstudantesCasaActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
