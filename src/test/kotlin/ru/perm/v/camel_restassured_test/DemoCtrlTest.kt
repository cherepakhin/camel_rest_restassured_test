package ru.perm.v.camel_restassured_test

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals

@DisplayName("DemoCtrl tests")
class DemoCtrlTest {
    @BeforeEach
    fun setup() {
        RestAssured.baseURI = CONSTS.HOST
    }

    @Test
    @DisplayName("GET REST Request is status=200")
    fun getHelloWorldHttpStatusIsOK() {
        given().`when`().get("/demo/message").then()
            .statusCode(HttpStatus.SC_OK)
    }

    @Test
    @DisplayName("GET /demo Request is OK and response OK")
    fun getHelloWorldHttpCheckResponse() {
        given().`when`().get("/demo/message").then()
            .statusCode(HttpStatus.SC_OK)
            .body(IsEqual.equalTo("message"))
    }

    @Test
    @DisplayName("GET REST /demo Request with EMPTY message. Check response status.")
    fun getForEmptyCheckResponseStatus() {
        given().`when`().get("/demo/e").then()
            .statusCode(HttpStatus.SC_BAD_GATEWAY)
    }

    @Test
    @DisplayName("GET REST /demo Request with EMPTY message. Check response message.")
    fun getForEmptyCheckResponseMessage() {
        val response = given().`when`().get("/demo/e")

        println("-------------response.body--------------")
        println(response.body)
        println("---------------------------")

        println("-------------response.body.print()--------------")
        println(response.body.print())
        println("---------------------------")

        println("-------------response.body.jsonPath()--------------")
        println(response.body.jsonPath().getString("message"))
        println("---------------------------")

        println("-------------response.statusLine--------------")
        println(response.statusLine)
        println("---------------------------")

        println("-------------response.headers()--------------")
        println(response.headers())
        println("---------------------------")

        assertEquals("Message empty.", response.body.jsonPath().getString("message"))
    }

    @Test
    @DisplayName("GET REST /echo Request with EMPTY message. Check response code.")
    fun getForEmptyCheckResponseCode() {
        val response = given().`when`().get("/echo/")

        assertEquals(502, response.statusCode())
    }

    @Test
    @DisplayName("GET REST /echo Request with EMPTY message. Check status line.")
    fun getForEmptyCheckStatusLine() {
        val response = given().get("/echo/e")

        assertEquals(502, response.statusCode())
        assertEquals("HTTP/1.1 502 ", response.statusLine())
    }
}