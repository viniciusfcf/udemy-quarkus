package com.github.viniciusfcf.ifood.mp;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PratoResourceTest {

    @Test
    public void testHelloEndpoint() {
        String body = given()
                .when().get("/pratos")
                .then()
                .statusCode(200).extract().asString();
        System.out.println(body);
    }

}