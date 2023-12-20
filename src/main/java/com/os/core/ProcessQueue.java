package com.os.core;

import com.os.models.Process;

import java.util.LinkedList;
import java.util.List;

public class ProcessQueue {

    private Process currentProcess = null;

    private List<Process> readyQueue = new LinkedList<>();

    private List<Process> blockingQueue = new LinkedList<>();

    private List<Process> preReadyProcess = new LinkedList<>();

    public ProcessQueue() {
    }

    public void addToReadyQueue(Process process) {
        preReadyProcess.remove(process);
        readyQueue.add(process);
    }

    public void addToPreReadyQueue(Process process) {
        preReadyProcess.add(process);
    }

    public List<Process> getPreReadyProcess() {
        return preReadyProcess;
    }

    public void IOBlock(Process process) {
        blockingQueue.add(process);
    }

    public void IOComplete(Process process) {
        blockingQueue.remove(process);
        if (process.isComplete()) {
            return;
        } else {
            readyQueue.add(process);
        }
    }

    public void releaseProcess(Process process) {
        readyQueue.remove(process);
        blockingQueue.remove(process);
    }

    public void setCurrentProcess(Process process) {
        currentProcess = process;
        var pid = process.getPid();
        readyQueue.removeIf(p -> p.getPid() == pid);
        blockingQueue.removeIf(p -> p.getPid() == pid);
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
        for (var process : blockingQueue) {
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
        if (blockingQueue.size() > 0) {
            return blockingQueue.get(0);
        }
        return null;
    }

    public boolean isEmpty() {
        return readyQueue.isEmpty() && blockingQueue.isEmpty();
    }

    public List<Process> getReadyQueue() {
        return readyQueue;
    }

    public List<Process> getBlockingQueue() {
        return blockingQueue;
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

}
