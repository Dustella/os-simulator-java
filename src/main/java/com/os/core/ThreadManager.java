package com.os.core;

import java.util.LinkedList;
import java.util.List;

public class ThreadManager {

    private List<String> threads = new LinkedList<>();

    public ThreadManager() {
        System.out.println("ThreadManager created");
    }

    public void addThread(String threadName) {
        threads.add(threadName);
    }

    public void removeThread(String threadName) {
        threads.remove(threadName);
    }
}
