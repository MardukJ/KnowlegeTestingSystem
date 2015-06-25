package ua.epam.rd.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 05.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public enum  ExamStatus {
    NEW(1),IN_PROGRESS(2), FINISHED(10), CANCELED(100);

    int i;
    ExamStatus(int i) {
        this.i=i;
    }

    public int getI() {
        return i;
    }
}
