package com.bjjh.MessMan;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestTask {

    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                System.out.println("每1秒打印一次。");
            }
        };

        threadPool.scheduleAtFixedRate(task, 0 , 1000, TimeUnit.MILLISECONDS);
    }
}
