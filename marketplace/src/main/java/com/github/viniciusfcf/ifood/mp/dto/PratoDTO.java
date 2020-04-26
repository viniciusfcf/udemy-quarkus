package com.github.viniciusfcf.ifood.mp.dto;

import java.math.BigDecimal;

import io.vertx.mutiny.sqlclient.Row;

public class PratoDTO {

    public Long id;

    public String nome;

    public String descricao;

    public BigDecimal preco;

    public static PratoDTO from(Row row) {
        PratoDTO dto = new PratoDTO();
        dto.descricao = row.getString("descricao");
        dto.nome = row.getString("nome");
        dto.id = row.getLong("id");
        dto.preco = row.getBigDecimal("preco");
        return dto;
    }

}
