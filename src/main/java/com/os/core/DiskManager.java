package com.os.core;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiskManager {

    private List<String> currentReadingList;

    private List<String> currentWritingList;

    public DiskManager() {
        System.out.println("MemoryManager created");
    }

    public void readFile(String address) {
        System.out.println("Reading from address: " + address);

        // new a excutor to remove the address from currentReadingList after 1 second

        var excutor = Executors.newScheduledThreadPool(1);
        excutor.schedule(() -> {
            currentReadingList.remove(address);
        }, 5, java.util.concurrent.TimeUnit.SECONDS);

    }

    public void writeFile(String address) {
        System.out.println("Writing to address: " + address);

        var excutor = Executors.newScheduledThreadPool(1);
        excutor.schedule(() -> {
            currentWritingList.remove(address);
        }, 1, TimeUnit.SECONDS);
    }
}
