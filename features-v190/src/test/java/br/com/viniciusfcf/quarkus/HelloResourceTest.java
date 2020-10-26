package br.com.viniciusfcf.quarkus;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;

@QuarkusTest
@TestHTTPEndpoint(HelloResource.class)
public class HelloResourceTest {

    @Inject
    PessoaService service;

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get()
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

    @Test
    @TestTransaction
    public void testSalvar() {
       service.salvar();
    }

}