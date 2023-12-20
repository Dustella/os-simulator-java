package com.os.ui;

import com.os.core.OS;

import javax.swing.*;
import java.util.LinkedList;

public class DevicePannel {

    private DefaultListModel<String> pipeListModel;

    private DefaultListModel<String> fileListModel;

    private JList<String> pipeList;
    private JList<String> fileList;

    private JPanel devicePane;

    private JPanel filePane;

    public DevicePannel() {
        pipeListModel = new DefaultListModel<>();
        pipeList = new JList<>(pipeListModel);
        JScrollPane listScrollPane1 = new JScrollPane(pipeList);

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        JScrollPane listScrollPane2 = new JScrollPane(fileList);

        JPanel _pipePane = new JPanel();
        JLabel label1 = new JLabel("管道(进程通信)");
        label1.setFont(new java.awt.Font("微软雅黑", 1, 20));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setSize(800, 100);

        _pipePane.setLayout(new BoxLayout(_pipePane, BoxLayout.Y_AXIS));
        _pipePane.add(label1);
        _pipePane.add(listScrollPane1);

        JPanel _filePane = new JPanel();
        JLabel label2 = new JLabel("文件");
        label2.setFont(new java.awt.Font("微软雅黑", 1, 20));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setSize(800, 100);

        _filePane.setLayout(new BoxLayout(_filePane, BoxLayout.Y_AXIS));
        _filePane.add(label2);
        _filePane.add(listScrollPane2);

        devicePane = _pipePane;
        filePane = _filePane;

    }

    public JPanel getDevicePane() {
        return devicePane;
    }

    public JPanel getFilePane() {
        return filePane;
    }

    public void updateDeviceList() {
        pipeListModel.clear();
        var os = OS.getInstance();
        var pipeMan = os.getPipeManager();
        var info = pipeMan.getStatus();

        for (String line : info) {
            pipeListModel.addElement(line);
        }
    }

    public void updateFileList() {
        var os = OS.getInstance();
        var fileMan = os.getDiskManager();
        var deviceMan = os.getDeviceManager();

        var readingLs = fileMan.getCurrentReadingList();
        var writingLs = fileMan.getCurrentWritingList();
        var usageMap = fileMan.getUsageMap();

        fileListModel.clear();
        for (var file : readingLs) {
            var message = "正在读取";
            fileListModel.addElement(file + " " + message);
        }

        for (var file : writingLs) {
            var message = "正在写入";
            fileListModel.addElement(file + " " + message);
        }

        for (var file : usageMap.entrySet()) {
            var message = "正在使用";
            fileListModel.addElement(file.getKey() + " " + message);
        }

        var de = deviceMan.getDeviceMap();
        pipeListModel.clear();
        for (var device : de.entrySet()) {
            var message = device.getValue() ? "正在使用" : "空闲";
            pipeListModel.addElement(device.getKey() + " " + message);
        }

        // fileListModel.clear();
        // var os = OS.getInstance();
        // var fileMan = os.getDiskManager();
        //
        // for (String file : files) {
        // fileListModel.addElement(file);
        // }
    }
}
