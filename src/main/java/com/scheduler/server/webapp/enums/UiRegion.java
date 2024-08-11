package com.scheduler.server.webapp.enums;

public enum UiRegion {
    USA(100),
    AU(200),
    EU(300),
    ALL(400),
    ASIA(500);

    int value;

    UiRegion(int value) {
        this.value = value;
    }
}
