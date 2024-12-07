package com.scheduler.server.webapp.enums;

public enum Region {
    US(100),
    AU(200),
    EU(300),
    ALL(400),
    ASIA(500);

    int value;

    Region(int value) {
        this.value = value;
    }
}
