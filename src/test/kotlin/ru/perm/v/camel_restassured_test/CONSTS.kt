package ru.perm.v.camel_restassured_test

class CONSTS {
    companion object {
        val IP = System.getenv("CAMEL_SIMPLE_APP_IP") ?: "127.0.0.1:9090"
        val HOST = "http://"+ IP
        val ECHO_PATH = HOST + "/echo/"
        val COMPANY_PATH = HOST + "/company/"
        val VACANCY_PATH = HOST + "/vacancy/"
        val INIT_PATH = HOST + "/init/"
    }
}