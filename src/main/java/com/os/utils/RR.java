package com.os.utils;

import com.os.core.ProcessQueue;


public class RR implements SchedulingAlgorithm {
    // 时间片轮转算法

    private final int timeSlice;

    public RR(int timeSlice ) {
        this.timeSlice = timeSlice;
    }



    @Override
    public void Schedule(ProcessQueue queue) {
        Logger.log("RR");

        int time = 0;

//        while (!queue.isEmpty()) {
//            var process = queue.getCurrentProcess();
//            if (process == null) {
//                process = queue.getReadyQueue().get(0);
//                queue.setCurrentProcess(process);
//            }
//            var result = process.runWithTime(timeSlice);
//            if (result == Result.Done) {
//                queue.setCurrentProcess(null);
//            } else if (result == Result.IOBlocked) {
//                queue.BlockProcess(process, 2);
//                queue.setCurrentProcess(null);
//            }


    }



}
