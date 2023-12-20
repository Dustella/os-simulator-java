package com.os.utils;

import com.os.core.ProcessQueue;
import com.os.models.ProcessResult;

public class RR implements SchedulingAlgorithm {
    // 时间片轮转算法

    private final int timeSlice;

    public RR(int timeSlice) {
        this.timeSlice = timeSlice;
    }

    @Override
    public void Schedule(ProcessQueue queue) {
        int time = 0;
        while (!queue.isEmpty()) {
            Logger.log("Doing FCFS");
            var process = queue.getRandProcess();
            Logger.log("FCFS: No current process, setting it to " + process.getPid());
            queue.setCurrentProcess(process);
            var result = process.runToDie();
            Logger.log("FCFS: Process " + process.getPid() + " result is " + result);
            if (result == ProcessResult.Done) {
                Logger.log("FCFS: Process " + process.getPid() + " is done");
                queue.desetCurrentProcess();
                queue.releaseProcess(process);
            } else if (result == ProcessResult.IOBlocked) {
                Logger.log("FCFS: Process " + process.getPid() + " is blocked");
                queue.IOBlock(process);
                queue.setCurrentProcess(null);
            }
            Logger.log("FCFS: Current process is " + process.getPid());
        }
    }

}
