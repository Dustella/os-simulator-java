package com.os.ui;

import com.os.core.OS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProcessPannel {


    private DefaultTableModel readyTable;

    private DefaultTableModel blockTable;
    private JTable readyQue;

    private JTable blockQue;

    private JPanel readyQueuePane;

    private JPanel blockQueuePane;

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


    public ProcessPannel() {
        // 创建一个表格模型
        readyTable = new DefaultTableModel();
        readyTable.addColumn("PID");
        readyTable.addColumn("占用内存");
        readyQue = new JTable(readyTable);
        var tableScrollPane = new JScrollPane(readyQue);

        JPanel contentPane = new JPanel();
//        add title
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

        currentProcessLabel = new JLabel("当前进程：");
        currentProcessLabel.setFont(new java.awt.Font("微软雅黑", 1, 20));
        currentProcessLabel.setSize(100, 100);
        currentProcessLabel.setHorizontalAlignment(SwingConstants.CENTER);


    }

    public void UpdateProcesses(){
        var os = OS.getInstance();
        var scheduler = os.getScheduler();
        var pcbs = scheduler.getPCBS();

//        clear table
        readyTable.setRowCount(0);
        blockTable.setRowCount(0);

        pcbs.getReadyQueue().forEach(pcb -> {
            var row = new Object[]{pcb.getPid()};
            readyTable.addRow(row);
        });

        pcbs.getBlockingQueue().forEach(pcb -> {
            var row = new Object[]{pcb.getPid()};
            blockTable.addRow(row);
        });

        var process  = pcbs.getCurrentProcess();
        if (process != null) {
            currentProcessLabel.setText("当前进程：" + process.getPid());
        } else {
            currentProcessLabel.setText("当前进程：");
        }
        currentProcessLabel.setFont(new java.awt.Font("微软雅黑", 1, 20));


    }
}
