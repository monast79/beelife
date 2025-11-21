package ru.crimea.beelife.model;

import lombok.Getter;

@Getter
public enum ApiaryType {
    stationary("stationary"), mobile("mobile");

    private final String apiaryType;

    ApiaryType(String type) {
        apiaryType = type;
    }
}