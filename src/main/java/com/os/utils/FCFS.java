package com.os.utils;

import com.os.core.ProcessQueue;

public class FCFS implements SchedulingAlgorithm{

    @Override
    public void Schedule(ProcessQueue queue) {
        Logger.log("FCFS");
        int time = 0;
        while (!queue.isEmpty()) {
//            var process = queue.getCurrentProcess();
//            if (process == null) {
//                process = queue.getReadyQueue().get(0);
//                queue.setCurrentProcess(process);
//            }
//            var result = process.runWithTime(1);
//            if (result == Result.Done) {
//                queue.setCurrentProcess(null);
//            } else if (result == Result.IOBlocked) {
//                queue.BlockProcess(process, 2);
//                queue.setCurrentProcess(null);
//            }
        }
    }
}
