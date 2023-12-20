package com.os.core;

import com.os.utils.RR;

public class OS {

    static OS instance;

    private final MemoryManager memoryManager;

    private ThreadManager threadManager;

    private DiskManager diskManager;

    private Scheduler scheduler;

    private DeviceManager deviceManager;

    private PipeManager pipeManager;

    public static OS getInstance() {
        if (instance == null) {
            instance = new OS();
        }
        return instance;
    }

    public OS() {
        System.out.println("OS created");
        var scheduler = new Scheduler();
        scheduler.setScheduleAlgorithm(new RR(2));
        scheduler.schedule();
        memoryManager = new MemoryManager(24, 2);
        threadManager = new ThreadManager();
        diskManager = new DiskManager();
        deviceManager = new DeviceManager();
        pipeManager = new PipeManager();
        this.scheduler = scheduler;
    }

    public void start() {
        System.out.println("OS started");
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public DiskManager getDiskManager() {
        return diskManager;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public DeviceManager getDeviceManager() {
        return deviceManager;
    }

    public PipeManager getPipeManager() {
        return pipeManager;
    }
}
