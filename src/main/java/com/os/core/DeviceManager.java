package com.os.core;

import java.util.Map;

public class DeviceManager {

//    contains devices
//    a device map to record whether it is used, a map string to boolean
    private Map<String, Boolean> deviceMap;

    public DeviceManager() {
        System.out.println("DeviceManager created");
    }

    public void addDevice(String deviceName) {
        deviceMap.put(deviceName, false);
    }

    public void useDevice(String deviceName) {
        deviceMap.put(deviceName, true);
    }

    public void releaseDevice(String deviceName) {
        deviceMap.put(deviceName, false);
    }

    public boolean getDeviceAvailable(String deviceName) {
        return deviceMap.get(deviceName);
    }

    public Map<String, Boolean> getDeviceMap() {
        return deviceMap;
    }


}
