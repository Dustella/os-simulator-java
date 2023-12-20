package com.os.core;

import com.os.models.Process;
import com.os.utils.SchedulingAlgorithm;

import java.util.concurrent.Executors;

public class Scheduler {

    public static String mode;

    private SchedulingAlgorithm currentAlgorithm;

    private ProcessQueue PCBS = new ProcessQueue();

    public ProcessQueue getPCBS() {
        return PCBS;
    }

    public Scheduler() {

    }

    // this is for manage the Processes

    public void schedule() {
        if (currentAlgorithm == null) {
            // throw new Exception("No algorithm is set");
            System.out.println("No algorithm is set");
        } else {
            currentAlgorithm.Schedule(this.PCBS);
        }

    }

    public void setScheduleAlgorithm(SchedulingAlgorithm newAlgorithm) {
        this.currentAlgorithm = newAlgorithm;
    }

    public void BlockProcess(Process process, int time) {
        PCBS.IOBlock(process);

        // wait for time
        var excutor = Executors.newScheduledThreadPool(1);
        excutor.schedule(() -> {
            PCBS.IOComplete(process);
        }, time, java.util.concurrent.TimeUnit.SECONDS);
    }

}
