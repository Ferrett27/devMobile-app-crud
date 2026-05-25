package com.example.apiLoja;

import java.math.BigDecimal;

public record Produto(int id, String nome, BigDecimal preco) {
}
