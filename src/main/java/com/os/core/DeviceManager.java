package com.os.core;

import java.util.HashMap;
import java.util.Map;

public class DeviceManager {

//    contains devices
//    a device map to record whether it is used, a map string to boolean
    private Map<String, Boolean> deviceMap = new HashMap<>();

    private Map<String,Integer> usageMap = new HashMap<>();

    public DeviceManager() {
        System.out.println("DeviceManager created");
    }

    public void addDevice(String deviceName,int PID) {
        deviceMap.put(deviceName, false);
    }

    public void useDevice(String deviceName,int PID) {
//        if device is not available, add it to the map


        deviceMap.put(deviceName, true);
        usageMap.put(deviceName,PID);
    }

    public void releaseDevice(String deviceName) {
        deviceMap.put(deviceName, false);
        usageMap.remove(deviceName);
    }

    public boolean getDeviceAvailable(String deviceName) {
        return deviceMap.get(deviceName);
    }

    public Map<String, Boolean> getDeviceMap() {
        return deviceMap;
    }

    public Map<String,Integer> getUsageMap() {
        return usageMap;
    }


}
