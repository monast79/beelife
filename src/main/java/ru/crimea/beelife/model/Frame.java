package ru.crimea.beelife.model;

import lombok.Getter;

@Getter
public enum Frame {
    SIX(6), EIGHT(8), TEN(10), TWELVE(12), FOURTEEN(14), SIXTEEN(16), TWENTY(20), TWENTY_FOUR(24);

    private final Integer frameType;

    Frame(Integer frame) {
        frameType = frame;
    }
}
