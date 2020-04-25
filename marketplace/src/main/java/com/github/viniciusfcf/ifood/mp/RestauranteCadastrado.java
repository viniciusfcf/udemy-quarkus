package com.github.viniciusfcf.ifood.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class RestauranteCadastrado {

    @Incoming("restaurantes")
    public void receberRestaurante(String json) {
        Jsonb create = JsonbBuilder.create();
        Restaurante restaurante = create.fromJson(json, Restaurante.class);

        System.out.println("------------------------");
        System.out.println(json);
        System.out.println("------------------------");
        System.out.println(restaurante);
    }
}
