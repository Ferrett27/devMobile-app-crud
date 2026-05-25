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

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val etId = findViewById<EditText>(R.id.etUpdateId)
        val etNome = findViewById<EditText>(R.id.etUpdateNome)
        val etPreco = findViewById<EditText>(R.id.etUpdatePreco)
        val btnAtualizar = findViewById<Button>(R.id.btnAtualizar)

        btnAtualizar.setOnClickListener {
            val id = etId.text.toString()
            val nome = etNome.text.toString()
            val preco = etPreco.text.toString()

            if (id.isEmpty() || nome.isEmpty() || preco.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            RetrofitClient.api.alterarProduto(id.toInt(), nome, preco.toDouble()).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val msg = response.body() ?: "Produto atualizado com sucesso!"
                        Toast.makeText(this@UpdateActivity, msg, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val erro = response.errorBody()?.string() ?: "Erro ao atualizar"
                        Toast.makeText(this@UpdateActivity, erro, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@UpdateActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}