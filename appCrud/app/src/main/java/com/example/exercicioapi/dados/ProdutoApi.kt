package com.example.exercicioapi.dados

import retrofit2.Call
import retrofit2.http.*

interface ProdutoApi {

    @GET("api/produto")
    fun listarProdutos(): Call<List<Produto>>

    @GET("api/produto/{id}")
    fun buscarProduto(@Path("id") id: Int): Call<Produto>

    @POST("api/produto")
    fun inserirProduto(
        @Query("nome") nome: String,
        @Query("preco") preco: Double
    ): Call<String>

    @PUT("api/produto")
    fun alterarProduto(
        @Query("id") id: Int,
        @Query("novoNome") novoNome: String,
        @Query("novoPreco") novoPreco: Double
    ): Call<String>

    @DELETE("api/produto")
    fun deletarProduto(
        @Query("id") id: Int
    ): Call<String>
}