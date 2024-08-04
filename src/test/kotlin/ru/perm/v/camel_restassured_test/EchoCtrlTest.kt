package ru.perm.v.camel_restassured_test

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EchoCtrl tests")
class EchoCtrlTest {
    @BeforeEach
    fun setup() {
        RestAssured.baseURI = CONSTS.HOST
    }

    @Test
    @DisplayName("GET Hello World REST Request is status=200")
    fun getHelloWorldHttpStatusIsOK() {
        given().`when`().get("/echo/message").then()
            .statusCode(HttpStatus.SC_OK)
    }

    @Test
    @DisplayName("GET Hello World REST Request check response")
    fun getHelloWorldHttpCheckResponse() {
        given().`when`().get("/echo/message").then()
            .statusCode(HttpStatus.SC_OK)
            .body(IsEqual.equalTo("message"))
    }

}