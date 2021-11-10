package com.vertx.example;
import io.vertx.core.Vertx;

public class Main {
public static void main(String[] args) {
    // Start server
    Vertx.vertx().deployVerticle(new ServerVerticle());
}
}
