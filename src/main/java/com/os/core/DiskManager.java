package com.os.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

public class DiskManager {

    private List<String> currentReadingList = new LinkedList<>();

    private List<String> currentWritingList = new LinkedList<>();

    private Map<String,Integer> usageMap = new HashMap<>();

    public DiskManager() {
        System.out.println("MemoryManager created");
    }

    public void readFile(String address, int PID) {
        System.out.println("Reading from address: " + address);
        currentReadingList.add(address);
        usageMap.put(address, PID);

        // new a excutor to remove the address from currentReadingList after 1 second

        var excutor = Executors.newScheduledThreadPool(1);
        excutor.schedule(() -> {
            currentReadingList.remove(address);
            usageMap.remove(address);
        }, 5, java.util.concurrent.TimeUnit.SECONDS);

    }

    public void writeFile(String address,int PID) {
        System.out.println("Writing to address: " + address);
        currentWritingList.add(address);
        usageMap.put(address, PID);

        var excutor = Executors.newScheduledThreadPool(1);
        excutor.schedule(() -> {
            currentWritingList.remove(address);
            usageMap.remove(address);
        }, 1, TimeUnit.SECONDS);
    }

    public List<String> getCurrentReadingList() {
        return currentReadingList;
    }

    public List<String> getCurrentWritingList() {
        return currentWritingList;
    }

    public Map<String,Integer> getUsageMap() {
        return usageMap;
    }
}
