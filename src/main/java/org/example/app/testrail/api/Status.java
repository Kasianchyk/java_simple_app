package org.example.app.testrail.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Status {
    PASSED(1),
    BLOCKED(2),
    RETEST(4),
    FAILED(5),
    SKIP(6),
    NOT_IMPLEMENTED(7);

    private int status;

    public static Status valueOf(int value) {
        return Arrays.stream(values())
                .filter(val -> val.status == value)
                .findFirst().get();
    }
}
