package com.example.apiLoja;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service // Indica ao Spring que esta é uma classe de serviço
public class GerenciadorProduto {

    String jdbcUrl = "jdbc:mysql://localhost:3306/loja";
    String user = "root";
    String password = "admin";

    public Connection conexao() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    public void insercao(String nomev, BigDecimal precov) {
        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO produto (nome, preco) VALUES (?, ?)")) {

            preparedStatement.setString(1, nomev);
            preparedStatement.setBigDecimal(2, precov);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir produto", e);
        }
    }

    public List<Produto> resultado() {
        List<Produto> listaProdutos = new ArrayList<>();

        try (Connection connection = conexao();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nome, preco FROM produto")) {

            // Preenche a lista com os resultados do banco
            while (rs.next()) {
                listaProdutos.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getBigDecimal("preco")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar produtos", e);
        }

        return listaProdutos;
    }

    public Produto buscarPorId(Integer id) {
        Produto produtoEncontrado = null;

        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome, preco FROM produto WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    produtoEncontrado = new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getBigDecimal("preco")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar o produto por ID", e);
        }

        return produtoEncontrado;
    }

    public void editar(Integer id, String novoNome, BigDecimal novoPreco) {
        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE produto SET nome = ?, preco = ? WHERE id = ?")) {

            // Ordem: nome (1), preco (2), id (3)
            preparedStatement.setString(1, novoNome);
            preparedStatement.setBigDecimal(2, novoPreco);
            preparedStatement.setInt(3, id);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhum produto encontrado com o ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao editar produto", e);
        }
    }

    public void deletar(Integer id) {
        try (Connection connection = conexao();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM produto WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Aviso: Nenhum produto encontrado com o ID " + id + " para deletar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar produto", e);
        }
    }
}