package me.gb2022.commons.http;

public enum HttpMethod {
    GET("GET"),
    PUT("PUT"),
    POST("POST");

    final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return this.method;
    }
}
