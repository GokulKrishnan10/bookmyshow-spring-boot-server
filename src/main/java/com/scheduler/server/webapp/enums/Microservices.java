package com.scheduler.server.webapp.enums;

public enum Microservices {
    ORDERS(50, "orders"),
    PAYMENTS(100, "payments");

    int value;
    String name;

    Microservices(int value, String name) {
        this.value = value;
        this.name = name;
    }
}