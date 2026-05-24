package br.ufpr.trab1harrypotterapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpr.trab1harrypotterapi.adapter.FeiticoAdapter
import br.ufpr.trab1harrypotterapi.network.RetrofitClient
import kotlinx.coroutines.launch

class FeiticosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feiticos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvFeiticos = findViewById<RecyclerView>(R.id.rvFeiticos)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        rvFeiticos.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val feiticos = RetrofitClient.service.getSpells()
                rvFeiticos.adapter = FeiticoAdapter(feiticos) { feitico ->
                    val intent = Intent(this@FeiticosActivity, DetalhesFeiticoActivity::class.java).apply {
                        putExtra("NOME", feitico.name)
                        putExtra("DESCRICAO", feitico.description)
                    }
                    startActivity(intent)
                }
            } catch (e: Exception) {
                Toast.makeText(this@FeiticosActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
