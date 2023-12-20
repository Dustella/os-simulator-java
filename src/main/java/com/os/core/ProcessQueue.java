package com.os.core;

import com.os.models.Process;

import java.util.LinkedList;
import java.util.List;

public class ProcessQueue {

    private Process currentProcess = null;

    private List<Process> readyQueue = new LinkedList<>();

    private List<Process> blockingQueye = new LinkedList<>();

    public ProcessQueue() {
    }

    public void addToReadyQueue(Process process) {
        readyQueue.add(process);
    }

    public void IOBlock(Process process) {
        blockingQueye.add(process);
    }

    public void IOComplete(Process process) {
        blockingQueye.remove(process);
        if (process.isComplete()) {
            return;
        } else {
            readyQueue.add(process);
        }
    }

    public void releaseProcess(Process process) {
        readyQueue.remove(process);
        blockingQueye.remove(process);
    }

    public void setCurrentProcess(Process process) {
        currentProcess = process;
        var pid = process.getPid();
        readyQueue.removeIf(p -> p.getPid() == pid);
        blockingQueye.removeIf(p -> p.getPid() == pid);
    }

    public void desetCurrentProcess() {
        readyQueue.add(currentProcess);
        currentProcess = null;
    }

    public Process getProcess(int pid) {
        for (var process : readyQueue) {
            if (process.getPid() == pid) {
                return process;
            }
        }
        for (var process : blockingQueye) {
            if (process.getPid() == pid) {
                return process;
            }
        }
        return null;
    }

    public Process getRandProcess() {
        if (readyQueue.size() > 0) {
            return readyQueue.get(0);
        }
        if (blockingQueye.size() > 0) {
            return blockingQueye.get(0);
        }
        return null;
    }

    public boolean isEmpty() {
        return readyQueue.isEmpty() && blockingQueye.isEmpty();
    }

    public List<Process> getReadyQueue() {
        return readyQueue;
    }

    public List<Process> getBlockingQueue() {
        return blockingQueye;
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

}
