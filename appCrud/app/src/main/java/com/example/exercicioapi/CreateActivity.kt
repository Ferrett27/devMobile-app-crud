package com.example.exercicioapi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicioapi.dados.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val etNome = findViewById<EditText>(R.id.etNome)
        val etPreco = findViewById<EditText>(R.id.etPreco)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString()
            val precoTexto = etPreco.text.toString()

            if (nome.isEmpty() || precoTexto.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            RetrofitClient.api.inserirProduto(nome, precoTexto.toDouble()).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val msg = response.body() ?: "Produto cadastrado com sucesso!"
                        Toast.makeText(this@CreateActivity, msg, Toast.LENGTH_LONG).show()
                        finish() // Fecha a tela e volta ao menu
                    } else {
                        val erro = response.errorBody()?.string() ?: "Erro ao cadastrar"
                        Toast.makeText(this@CreateActivity, erro, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@CreateActivity,
                        "Falha na conexão: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}