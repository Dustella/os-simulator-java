package com.os.ui;

import com.os.utils.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainFrame extends JFrame {

    public static MainFrame mainFrame;

    public static MainFrame getInstance() {
        if (mainFrame == null) {
            mainFrame = new MainFrame();
        }
        return mainFrame;
    }

    private JList<String> list1;
    private JList<String> list2;
    private JLabel label;
    private JTextArea logTextArea;

    private MemoryPannel memoryPannel;

    private ProcessPannel processPannel;

    private DevicePannel devicePannel;


    public MainFrame() {
        setTitle("OS 模拟器");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建一个字符串列表模型
//        DefaultListModel<String> deviceList = new DefaultListModel<>();
//        list1 = new JList<>(deviceList);
//        JScrollPane listScrollPane1 = new JScrollPane(list1);
//
//        DefaultListModel<String> fileList = new DefaultListModel<>();
//        list2 = new JList<>(fileList);
//        JScrollPane listScrollPane2 = new JScrollPane(list2);
        devicePannel = new DevicePannel();
        var devicePane = devicePannel.getDevicePane();
        var filePane = devicePannel.getFilePane();


        label = new JLabel("Label Text");

        // 创建一个表格模型
        processPannel = new ProcessPannel();
        var readyQueuePane = processPannel.getReadyQueuePane();
        var blockedQueuePane = processPannel.getBlockQueuePane();

        logTextArea = new JTextArea(10, 40);
        logTextArea.setEditable(false); // 设置为只读
        JScrollPane logScrollPane = new JScrollPane(logTextArea);

        // 创建一个按钮，用于更新控件内容
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> flushInfo());

        // 添加控件到窗口
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(blockedQueuePane);
        panel.add(readyQueuePane);
        panel.add(devicePane);
        panel.add(filePane);

        memoryPannel = new MemoryPannel();

        Random random = new Random();
        int[] memoryStatus = new int[10];
        for (int i = 0; i < 10; i++) {
            memoryStatus[i] = random.nextInt(2); // 随机状态，例如：0表示空闲，1表示已分配，2表示被占用
        }
        memoryPannel.setMemoryStatus(memoryStatus);

        getContentPane().add(memoryPannel, BorderLayout.SOUTH);
        getContentPane().add(logScrollPane, BorderLayout.EAST);
        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    // 更新列表、标签和表格内容的方法
    private void flushInfo() {
        processPannel.UpdateProcesses();

        // 更新列表1的内容
//        DefaultListModel<String> listModel1 = (DefaultListModel<String>) list1.getModel();
//        listModel1.removeAllElements();
//        listModel1.addElement("Item 1");
//        listModel1.addElement("Item 2");
//        listModel1.addElement("Item 3");
//
//        // 更新列表2的内容
//        DefaultListModel<String> listModel2 = (DefaultListModel<String>) list2.getModel();
//        listModel2.removeAllElements();
//        listModel2.addElement("A");
//        listModel2.addElement("B");
//        listModel2.addElement("C");
//
//        // 更新标签文本
//        label.setText("Updated Label Text");

        // 更新表格内容

    }

    public void mainLoop() {
//        setInterval
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            log
            flushInfo();
        }
    }

    public static void main(String[] args) {
        var example = new com.os.ui.MainFrame();
//        invoke later
        SwingUtilities.invokeLater(() -> {

            example.setVisible(true);
        });
        example.mainLoop();
    }
}


