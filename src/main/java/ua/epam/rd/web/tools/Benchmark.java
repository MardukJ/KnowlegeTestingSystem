package ua.epam.rd.web.tools;

/**
 * Created by Mykhaylo Gnylorybov on 24.04.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */
public class Benchmark {
    private long timeStart;
    private long timeFinish;

    public void start() {
        timeStart = System.currentTimeMillis();
    }

    public void stop() {
        timeFinish = System.currentTimeMillis();
    }

    public long getDifferce() {
        return timeFinish - timeStart;
    }
}
