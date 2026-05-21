package br.ufpr.trab1harrypotterapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<android.view.View>(R.id.btnBuscarPersonagem).setOnClickListener {
            startActivity(Intent(this, BuscarPersonagemActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnBuscarProfessor).setOnClickListener {
            startActivity(Intent(this, BuscarProfessorActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnEstudantesCasa).setOnClickListener {
            startActivity(Intent(this, EstudantesCasaActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnFeiticos).setOnClickListener {
            startActivity(Intent(this, FeiticosActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnSair).setOnClickListener {
            finishAffinity()
        }
    }
}
