package com.os.core;

import com.os.models.Process;

import java.util.LinkedList;
import java.util.List;

public class ProcessQueue {

    private Process currentProcess;

    private List<Process> readyQueue = new LinkedList<>();

    private List<Process> waitingQueue = new LinkedList<>();


    public ProcessQueue() {
    }

    public void addToReadyQueue(Process process) {
        readyQueue.add(process);
    }

    public void moveToWaitingQueue(Process process) {
        waitingQueue.add(process);
    }

    public void IOComplete(Process process) {
        waitingQueue.remove(process);
        readyQueue.add(process);
    }

    public void setCurrentProcess(Process process) {
        currentProcess = process;
        var pid = process.getPid();
        readyQueue.removeIf(p -> p.getPid() == pid);
        waitingQueue.removeIf(p -> p.getPid() == pid);
    }

    public boolean isEmpty(){
        return readyQueue.isEmpty() && waitingQueue.isEmpty();
    }

    public List<Process> getReadyQueue() {
        return readyQueue;
    }

    public List<Process> getWaitingQueue() {
        return waitingQueue;
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void wakeUpProcess(Process process) {
        waitingQueue.remove(process);
        readyQueue.add(process);
    }
}
