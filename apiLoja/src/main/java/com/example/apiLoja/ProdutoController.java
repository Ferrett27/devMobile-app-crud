package com.example.apiLoja;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Gerenciamento de Produtos", description = "Endpoints para criar, listar (especifico e geral), modificar e deletar produtos")
public class ProdutoController {

    private final GerenciadorProduto gerenciadorProdutos;

    // Correção: O construtor deve ter o mesmo nome da classe (ProdutoController)
    public ProdutoController(GerenciadorProduto gerenciadorProdutos) {
        this.gerenciadorProdutos = gerenciadorProdutos;
    }

    @PostMapping
    @Operation(summary = "Inserir novo produto", description = "Adiciona um novo produto. O ID é auto-incrementado pelo banco.")
    public String inserirProduto(
            @RequestParam String nome,
            @RequestParam BigDecimal preco) {
        // O id não é passado aqui pois o banco de dados o gera sozinho
        gerenciadorProdutos.insercao(nome, preco);
        return "Produto inserido com sucesso!";
    }

    @GetMapping
    @Operation(summary = "Listar Produtos", description = "Retorna uma lista de todos os produtos.")
    public List<Produto> listarProdutos() {
        return gerenciadorProdutos.resultado();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto específico", description = "Retorna os dados de apenas um produto usando seu id.")
    public Produto buscarProduto(
            @PathVariable Integer id) {
        return gerenciadorProdutos.buscarPorId(id);
    }

    @PutMapping
    @Operation(summary = "Modificar Produto", description = "Altera dados de um produto existente usando o seu ID.")
    public void alterarProduto(
            @RequestParam Integer id,
            @RequestParam String novoNome,
            @RequestParam BigDecimal novoPreco) {
        gerenciadorProdutos.editar(id, novoNome, novoPreco);
    }

    @DeleteMapping
    @Operation(summary = "Deletar Produto", description = "Remove um produto do banco de dados utilizando o ID.")
    public void deletarProduto(
            @RequestParam Integer id) {
        gerenciadorProdutos.deletar(id);
    }
}