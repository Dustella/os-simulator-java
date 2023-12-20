package com.os.ui;

import com.os.core.OS;
import com.os.core.Scheduler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProcessPannel {

    private DefaultTableModel readyTable;

    private DefaultTableModel blockTable;

    private DefaultTableModel preReadyTable;

    private JTable readyQue;

    private JTable blockQue;

    private JTable preReadyQue;

    private JPanel readyQueuePane;

    private JPanel blockQueuePane;

    private JPanel preReadyPane;

    private JLabel currentProcessLabel;

    public JPanel getReadyQueuePane() {
        return readyQueuePane;
    }

    public JPanel getBlockQueuePane() {
        return blockQueuePane;
    }

    public JLabel getCurrentProcessLabel() {
        return currentProcessLabel;
    }

    public JPanel getPreReadyPane() {
        return preReadyPane;
    }

    public ProcessPannel() {
        // 创建一个表格模型
        readyTable = new DefaultTableModel();
        readyTable.addColumn("PID");
        readyTable.addColumn("占用内存");
        readyQue = new JTable(readyTable);
        var tableScrollPane = new JScrollPane(readyQue);

        JPanel contentPane = new JPanel();
        // add title
        JLabel label = new JLabel("就绪队列");
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(label);
        contentPane.add(tableScrollPane);

        readyQueuePane = contentPane;

        // 创建一个表格模型
        blockTable = new DefaultTableModel();
        blockTable.addColumn("PID");
        blockTable.addColumn("占用内存");
        blockQue = new JTable(blockTable);
        var tableScrollPane2 = new JScrollPane(blockQue);

        JPanel contentPane2 = new JPanel();

        JLabel label2 = new JLabel("阻塞队列");

        contentPane2.setLayout(new BoxLayout(contentPane2, BoxLayout.Y_AXIS));
        contentPane2.add(label2);
        contentPane2.add(tableScrollPane2);

        blockQueuePane = contentPane2;

        // 创建一个表格模型
        preReadyTable = new DefaultTableModel();
        preReadyTable.addColumn("PID");

        preReadyQue = new JTable(preReadyTable);
        var tableScrollPane3 = new JScrollPane(preReadyQue);

        JPanel contentPane3 = new JPanel();

        JLabel label3 = new JLabel("即将加入的进程队列");

        contentPane3.setLayout(new BoxLayout(contentPane3, BoxLayout.Y_AXIS));
        contentPane3.add(label3);
        contentPane3.add(tableScrollPane3);

        preReadyPane = contentPane3;

        currentProcessLabel = new JLabel("当前进程：");
        currentProcessLabel.setFont(new java.awt.Font("微软雅黑", 1, 20));
        currentProcessLabel.setSize(100, 100);
        currentProcessLabel.setHorizontalAlignment(SwingConstants.CENTER);

    }

    public void UpdateProcesses() {
        var os = OS.getInstance();
        var scheduler = os.getScheduler();
        var pcbs = scheduler.getPCBS();

        // clear table
        readyTable.setRowCount(0);
        blockTable.setRowCount(0);
        preReadyTable.setRowCount(0);

        pcbs.getReadyQueue().forEach(pcb -> {
            var row = new Object[] { pcb.getPid() };
            readyTable.addRow(row);
        });

        pcbs.getBlockingQueue().forEach(pcb -> {
            var row = new Object[] { pcb.getPid() };
            blockTable.addRow(row);
        });

        pcbs.getPreReadyProcess().forEach(pcb -> {
            var row = new Object[] { pcb.getPid() };
            preReadyTable.addRow(row);
        });

        String out;
        var process = pcbs.getCurrentProcess();
        if (process != null) {
            out = "正在执行：" + Scheduler.mode + "，当前进程：" + process.getPid();
        } else {
            out = "当前无进程";
        }
        currentProcessLabel.setText(out);
        currentProcessLabel.setFont(new java.awt.Font("微软雅黑", 1, 20));

    }
}
