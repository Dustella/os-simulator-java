package com.os.ui;

import com.os.core.OS;

import javax.swing.*;
import java.util.LinkedList;

public class DevicePannel {

    private DefaultListModel<String> deviceListModel;

    private DefaultListModel<String> fileListModel;

    private JList<String> deviceList;
    private JList<String> fileList;

    private JPanel devicePane;

    private JPanel filePane;

    public DevicePannel() {
        // TODO
         deviceListModel = new DefaultListModel<>();
        deviceList = new JList<>(deviceListModel);
        JScrollPane listScrollPane1 = new JScrollPane(deviceList);

         fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        JScrollPane listScrollPane2 = new JScrollPane(fileList);

        JPanel _devicePane = new JPanel();
        JLabel label1 = new JLabel("设备");
        label1.setFont(new java.awt.Font("微软雅黑", 1, 20));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setSize(800, 100);

        _devicePane.setLayout(new BoxLayout(_devicePane, BoxLayout.Y_AXIS));
        _devicePane.add(label1);
        _devicePane.add(listScrollPane1);

        JPanel _filePane = new JPanel();
        JLabel label2 = new JLabel("文件");
        label2.setFont(new java.awt.Font("微软雅黑", 1, 20));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setSize(800, 100);

        _filePane.setLayout(new BoxLayout(_filePane, BoxLayout.Y_AXIS));
        _filePane.add(label2);
        _filePane.add(listScrollPane2);

        devicePane = _devicePane;
        filePane = _filePane;

    }

    public JPanel getDevicePane() {
        return devicePane;
    }

    public JPanel getFilePane() {
        return filePane;
    }

    public void updateDeviceList() {
        deviceListModel.clear();
        var os = OS.getInstance();
        var deviceMan = os.getDeviceManager();
        var devicesRaw = deviceMan.getDeviceMap();
        var devices = new LinkedList<String>();
        for (var device : devicesRaw.entrySet()) {
            if (device.getValue()) {
                var message = device.getValue()? "正在使用" : "空闲";
                devices.add(device.getKey() + " " + message);
            }
        }


        for (String device : devices) {
            deviceListModel.addElement(device);
        }
    }

    public void updateFileList() {
//        fileListModel.clear();
//        var os = OS.getInstance();
//        var fileMan = os.getDiskManager();
//
//        for (String file : files) {
//            fileListModel.addElement(file);
//        }
    }
}
