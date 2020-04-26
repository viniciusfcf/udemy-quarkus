package com.github.viniciusfcf.ifood.pedido;

import java.math.BigDecimal;

public class PratoPedidoDTO {

    public String nome;

    public String descricao;

    public BigDecimal preco;

    public PratoPedidoDTO() {
        super();
    }

    public PratoPedidoDTO(String nome, String descricao, BigDecimal preco) {
        super();
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

}
