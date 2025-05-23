package com.design.patterns.test;



import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

import com.design.patterns.apis.SingletonPaymentResource;
import com.design.patterns.entities.User;
import com.design.patterns.entities.User.PaymentType;
import static io.restassured.RestAssured.given;




@QuarkusTest
class SingletonPokemonResourceTest {

    @Inject
    SingletonPaymentResource resource;



    @BeforeEach
    @Transactional
    void setup() {
        User.deleteAll();

        User user1 = new User();
        user1.passport = 1;
        user1.name = "Alice";
        user1.lastName = "Smith";
        user1.paymentType = PaymentType.CREDIT;
        User.persist(user1);
    
        User user2 = new User();
        user2.passport = 2;
        user2.name = "Bob";
        user2.lastName = "Jones";
        user2.paymentType = PaymentType.PAYPAL;
        User.persist(user2);

        User user = new User();
        user.passport = 100;
        user.name = "Charlie";
        user.lastName = "Brown";
        user.paymentType = PaymentType.DEBIT;
        User.persist(user);

        User user4 = new User();
        user4.passport = 123;
        user4.name = "John";
        user4.lastName = "Doe";
        user4.paymentType = PaymentType.PAYPAL;
        User.persist(user4);


    }


    @Test
    void testCreateUserSuccess() {
        User user = new User();
        user.passport = 1234;
        user.name = "John";
        user.lastName =  "Doe";
        user.paymentType = PaymentType.PAYPAL;

        given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(user)
          .when()
          .post("/person")
          .then()
          .statusCode(201)   
          .header("Location", "http://localhost:8081/person/1234")
          .body("passport", org.hamcrest.Matchers.is(1234))
          .body("name", org.hamcrest.Matchers.is("John"));
    }

    @Test
    @TestTransaction
    void testCreateUserAlreadyExists() {
        User user = new User();
        user.passport = 123;
        user.name = "John";
        user.lastName = "Doe";
        user.paymentType = PaymentType.PAYPAL;

        given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(user)
          .when()
          .post("/person")
          .then()
          .statusCode(500); 
    }

    @Test
    @TestTransaction
    void testGetUsers() {

        given()
          .when()
          .get("/person")
          .then()
          .statusCode(200)
          .body("size()", org.hamcrest.Matchers.is(4));
    }

    @Test
    @TestTransaction
    void testPayUserSuccess() {

        given()
          .queryParam("amount", 50.0)
          .when()
          .patch("/person/pay/100")
          .then()
          .statusCode(200);

    }

    @Test
    void testPayUserNotFound() {
        given()
          .queryParam("amount", 50.0)
          .when()
          .patch("/person/pay/999")
          .then()
          .statusCode(204);
    }

}
