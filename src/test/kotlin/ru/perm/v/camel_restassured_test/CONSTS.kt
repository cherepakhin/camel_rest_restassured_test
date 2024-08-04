package ru.perm.v.camel_restassured_test

class CONSTS {
    companion object {
        val IP = System.getenv("CAMEL_SIMPLE_APP_IP") ?: "127.0.0.1:8980"
        val HOST = "http://"+ IP + "/camel_rest/api"
    }
}