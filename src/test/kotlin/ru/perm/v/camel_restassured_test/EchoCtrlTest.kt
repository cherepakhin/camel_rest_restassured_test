package ru.perm.v.camel_restassured_test

import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.ReadContext
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.String.format
import kotlin.test.assertEquals


@DisplayName("EchoCtrl tests")
class EchoCtrlTest {
    @BeforeEach
    fun setup() {
        RestAssured.baseURI = CONSTS.HOST
    }

    @Test
    @DisplayName("GET Hello World REST Request is status=200")
    fun getHelloWorldHttpStatusIsOK() {
        given().`when`().get("/camel/echo/message").then()
            .statusCode(HttpStatus.SC_OK)
    }

    @Test
    @DisplayName("GET Hello World REST Request check response")
    fun getHelloWorldHttpCheckResponse() {
        given().`when`().get("/echo/TEST_MESSAGE").then()
            .statusCode(HttpStatus.SC_OK)
            .body(IsEqual.equalTo("TEST_MESSAGE"))
    }

    @Test
    @DisplayName("GET REST /echo (no end slash).  Request with EMPTY message. Check response code.")
    fun getForEmptyCheckResponseCode() {
        val response = given().`when`().get("/echo")

        assertEquals(503, response.statusCode())
    }

    @Test
    @DisplayName("GET REST /echo/ Request with EMPTY message. Check response code.")
    fun getForEmptyWithEndSlashCheckResponseCode() {
        val response = given().`when`().get("/echo/")

        assertEquals(503, response.statusCode())
    }

    @Test
    @DisplayName("GET REST /echo/ Request with EMPTY message. Check error message.")
    fun getForEmptyWithEndSlashCheckErrorMessage() {
        val response = given().`when`().get("/echo/")
        val json=response.body().print()
        val context: ReadContext = JsonPath.parse(json)
        val errorMessage = context.read<String>("$.message")

        assertEquals("Message empty.", errorMessage)
    }
}