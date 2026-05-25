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

class DeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val etId = findViewById<EditText>(R.id.etDeletarId)
        val btnDeletar = findViewById<Button>(R.id.btnDeletarConfirmar)

        btnDeletar.setOnClickListener {
            val id = etId.text.toString()

            if (id.isEmpty()) {
                Toast.makeText(this, "Por favor, digite um ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            RetrofitClient.api.deletarProduto(id.toInt()).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val msg = response.body() ?: "Produto removido com sucesso!"
                        Toast.makeText(this@DeleteActivity, msg, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val erro = response.errorBody()?.string() ?: "Erro ao deletar"
                        Toast.makeText(this@DeleteActivity, erro, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@DeleteActivity, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}