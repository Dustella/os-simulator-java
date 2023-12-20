package com.os.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.os.core.OS;

import javafx.util.Pair;

public class BankVisual {

    private JPanel bankPanel;

    private Map<Integer, Pair<Integer, Integer>> innerMapping = new HashMap<>();

    private DefaultTableModel bankTable;

    public BankVisual() {
        // Create a table
        String[] columnNames = { "进程PID", "内存总需求", "设备总需求" };
        Object[][] data = {
        };
        bankTable = new DefaultTableModel(data, columnNames);
        var table = new JTable(bankTable);

        // Create a title
        JLabel titleLabel = new JLabel("银行家算法状态");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel safeLabel = new JLabel("序列是否安全：是");
        safeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a panel and add the table and title
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titleLabel);
        panel.add(safeLabel);
        panel.add(new JScrollPane(table));
        this.bankPanel = panel;

    }

    public JPanel getResult() {
        return bankPanel;
    }

    public void initFakeData() {
        var os = OS.getInstance();
        var schedular = os.getScheduler();
        var pcbs = schedular.getPCBS();
        var readyQueue = pcbs.getPreReadyProcess();
        for (var p : readyQueue) {
            var pid = p.getPid();
            // random gen memory 0-3
            // ramdom geng device 0-1
            var rand = new Random();
            var men = rand.nextInt(4);
            var dev = rand.nextInt(2);
            innerMapping.put(pid, new Pair<>(men * 2, dev));

        }

    }

    public void updateData() {
        // put mapping into table
        // erase data in table
        bankTable.setRowCount(0);
        for (var item : innerMapping.keySet()) {
            var row = new Object[] { item, innerMapping.get(item).getKey(), innerMapping.get(item).getValue() };
            bankTable.addRow(row);

        }
    }
}
