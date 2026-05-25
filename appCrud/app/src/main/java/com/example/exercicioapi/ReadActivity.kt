package com.example.exercicioapi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicioapi.dados.Produto
import com.example.exercicioapi.dados.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val etBuscarId = findViewById<EditText>(R.id.etBuscarId)
        val btnBuscarPorId = findViewById<Button>(R.id.btnBuscarPorId)
        val btnListarTodos = findViewById<Button>(R.id.btnListarTodos)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoConsultas)

        btnListarTodos.setOnClickListener {
            tvResultado.text = "Carregando..."

            RetrofitClient.api.listarProdutos().enqueue(object : Callback<List<Produto>> {
                override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                    if (response.isSuccessful) {
                        val produtos = response.body()
                        if (produtos.isNullOrEmpty()) {
                            tvResultado.text = "Nenhum produto cadastrado."
                        } else {
                            val blocoDeTexto = StringBuilder()
                            for (produto in produtos) {
                                blocoDeTexto.append("ID: ${produto.id} | Nome: ${produto.nome} | Preço: R$ ${produto.preco}\n")
                                blocoDeTexto.append("--------------------------------------------------\n")
                            }
                            tvResultado.text = blocoDeTexto.toString()
                        }
                    } else {
                        tvResultado.text = "Erro no servidor: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                    tvResultado.text = "Falha na conexão: ${t.message}"
                }
            })
        }

        btnBuscarPorId.setOnClickListener {
            val idTexto = etBuscarId.text.toString()
            if (idTexto.isEmpty()) {
                Toast.makeText(this, "Informe um ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tvResultado.text = "Buscando..."

            RetrofitClient.api.buscarProduto(idTexto.toInt()).enqueue(object : Callback<Produto> {
                override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                    if (response.isSuccessful) {
                        val produto = response.body()
                        if (produto != null) {
                            tvResultado.text = "Produto Encontrado:\n\nID: ${produto.id}\nNome: ${produto.nome}\nPreço: R$ ${produto.preco}"
                        }
                    } else {
                        val menssagemErro = response.errorBody()?.string() ?: "Não encontrado"
                        tvResultado.text = menssagemErro
                    }
                }

                override fun onFailure(call: Call<Produto>, t: Throwable) {
                    tvResultado.text = "Erro ao processar consulta local."
                }
            })
        }
    }
}