package com.os.utils;

import com.os.core.ProcessQueue;
import com.os.models.ProcessResult;

public class SJF implements SchedulingAlgorithm {
    @Override
    public void Schedule(ProcessQueue queue) {
        while (!queue.isEmpty()) {
            Logger.log("Doing SJF");
            var process = queue.getRandProcess();
            Logger.log("SJF: No current process, setting it to " + process.getPid());
            queue.setCurrentProcess(process);
            var result = process.runToDie();
            Logger.log("SJF: Process " + process.getPid() + " result is " + result);
            if (result == ProcessResult.Done) {
                Logger.log("SJF: Process " + process.getPid() + " is done");
                queue.desetCurrentProcess();
                queue.releaseProcess(process);
            } else if (result == ProcessResult.IOBlocked) {
                Logger.log("SJF: Process " + process.getPid() + " is blocked");
                queue.IOBlock(process);
                queue.setCurrentProcess(null);
            }
            Logger.log("SJF: Current process is " + process.getPid());
        }
    }
}
