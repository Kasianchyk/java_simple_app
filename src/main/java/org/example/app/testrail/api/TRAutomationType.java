package org.example.app.testrail.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TRAutomationType {

    NEED_TO_AUTOMATE(1),
    NO_NEED_TO_AUTOMATE(2),
    IN_PROGRESS(3),
    PARTIALLY_AUTOMATED(4),
    NEED_A_REVIEW(5),
    DONE(6);

    private int type;

    public static TRAutomationType parse(int id) {
        TRAutomationType trAutomationType = null;
        for (TRAutomationType type : TRAutomationType.values()) {
            if (type.getType() == id) {
                trAutomationType = type;
                break;
            }
        }
        return trAutomationType;
    }
}
