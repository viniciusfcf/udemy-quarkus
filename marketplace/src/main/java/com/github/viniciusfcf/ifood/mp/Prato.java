package com.github.viniciusfcf.ifood.mp;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

import com.github.viniciusfcf.ifood.mp.dto.PratoDTO;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class Prato {

    public Long id;

    public String nome;

    public String descricao;

    public Restaurante restaurante;

    public BigDecimal preco;

    public static Multi<PratoDTO> findAll(PgPool pgPool) {
        Uni<RowSet<Row>> preparedQuery = pgPool.query("select * from prato").execute();
        return unitToMulti(preparedQuery);
    }

    public static Multi<PratoDTO> findAll(PgPool client, Long idRestaurante) {
        Uni<RowSet<Row>> preparedQuery = client
                .preparedQuery("SELECT * FROM prato where prato.restaurante_id = $1 ORDER BY nome ASC").execute(
                        Tuple.of(idRestaurante));
        return unitToMulti(preparedQuery);
    }

    private static Multi<PratoDTO> unitToMulti(Uni<RowSet<Row>> queryResult) {
        return queryResult.onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> {
                    return StreamSupport.stream(set.spliterator(), false);
                }))
                .onItem().apply(PratoDTO::from);
    }

    public static Uni<PratoDTO> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT * FROM prato WHERE id = $1").execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> iterator.hasNext() ? PratoDTO.from(iterator.next()) : null);
    }

}
