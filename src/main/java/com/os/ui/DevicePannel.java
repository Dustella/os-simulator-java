package com.os.ui;

import com.os.core.OS;

import javax.swing.*;
import java.util.LinkedList;

public class DevicePannel {

    private DefaultListModel<String> pipeListModel;

    private DefaultListModel<String> fileListModel;

    private DefaultListModel<String> threadModel;

    private JTextArea loggerForPordu;

    private JList<String> pipeList;
    private JList<String> fileList;
    private JList<String> threadList;

    private JPanel devicePane;

    private JPanel filePane;

    private JPanel threadPane;

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

        threadModel = new DefaultListModel<>();
        threadList = new JList<>(threadModel);
        JScrollPane listScrollPane3 = new JScrollPane(threadList);

        JPanel _threadPane = new JPanel();
        JLabel label3 = new JLabel("线程");
        label3.setFont(new java.awt.Font("微软雅黑", 1, 20));
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setSize(800, 100);

        _threadPane.setLayout(new BoxLayout(_threadPane, BoxLayout.Y_AXIS));
        _threadPane.add(label3);
        _threadPane.add(listScrollPane3);

        loggerForPordu = new JTextArea(10, 20);
        loggerForPordu.setEditable(false); // 设置为只读

        threadPane = _threadPane;
        devicePane = _pipePane;
        filePane = _filePane;

    }

    public JTextArea getLoggerForPordu() {
        return loggerForPordu;
    }

    public JPanel getThreadPane() {
        return threadPane;
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

    public void updateThreadList() {
        var os = OS.getInstance();
        var threadMan = os.getThreadManager();
        var entrys = threadMan.getStatus();

        threadModel.clear();
        for (var entry : entrys) {
            threadModel.addElement(entry);
        }
    }

    public void updateFileList() {
        var os = OS.getInstance();
        var fileMan = os.getDiskManager();
        var pipMan = os.getPipeManager();

        var fileStatus = fileMan.getStatus();

        fileListModel.clear();
        for (var file : fileStatus) {
            fileListModel.addElement(file);
        }

        var de = pipMan.getStatus();
        pipeListModel.clear();
        for (var device : de) {
            pipeListModel.addElement(device);
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
