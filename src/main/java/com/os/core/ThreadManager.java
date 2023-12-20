package com.os.core;

import java.util.LinkedList;
import java.util.List;

import com.os.Programs;

public class ThreadManager {

    private List<String> threads = new LinkedList<>();

    private List<String> allThreads = new LinkedList<>();

    public ThreadManager() {
        System.out.println("ThreadManager created");

        var programs = Programs.getInstance();
        var allCOntent = programs.getAllThreads();
        for (var content : allCOntent) {
            allThreads.add(content);
        }

    }

    public void addThread(String threadName) {
        threads.add(threadName);
    }

    public void removeThread(String threadName) {
        threads.remove(threadName);
    }

    public List<String> getStatus() {
        List<String> result = new LinkedList<>();
        for (var thread : allThreads) {
            if (threads.contains(thread)) {
                result.add(thread + " is running");
            } else {
                result.add(thread + " is not running");
            }
        }
        return result;
    }
}
