package com.os.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.os.utils.Banker;

public class DeadlockHandler {

    // 使用银行家算法检测死锁
    private Banker banker = new Banker();

    private boolean isSafe = true;

    public void DoLoop() {
        var os = OS.getInstance();
        var scheduler = os.getScheduler();
        var pcbs = scheduler.getPCBS();
        while (!pcbs.getPreReadyProcess().isEmpty()) {
            // thread sleep 1s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (pcbs.getPreReadyProcess().size() == 1) {
                isSafe = false;
                var excuter = Executors.newScheduledThreadPool(1);
                excuter.schedule(() -> {
                    isSafe = true;
                }, 4000, null);

            }

            var newProcess = pcbs.getPreReadyProcess().get(0);
            pcbs.getPreReadyProcess().remove(0);
            pcbs.getReadyQueue().add(newProcess);

        }

    }

    public boolean isSafe() {
        return isSafe;
    }
}
