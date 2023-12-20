package com.os.ui;

import com.os.core.OS;
import com.os.models.MemoryPage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MemoryPannel extends JPanel {

    public static final int PAGE_COUNT = 9;
    private int[] memoryStatus;

    public MemoryPannel() {
        memoryStatus = new int[PAGE_COUNT];
        // 初始化内存页面状态
        for (int i = 0; i < PAGE_COUNT; i++) {
            memoryStatus[i] = 0; // 初始状态为0表示空闲
        }
        setPreferredSize(new Dimension(1200, 100));
    }

    public void setMemoryStatus(int[] memoryStatus) {
        this.memoryStatus = memoryStatus;
        repaint();
    }

    public void setMemoryPages(List<MemoryPage> pages) {
        for (MemoryPage page : pages) {
            this.memoryStatus[page.getPageNumber()] = page.isOccupied() ? 1 : 0;
        }
        repaint();
    }

    public void updateMemoryPages() {
        var os = OS.getInstance();
        var menMan = os.getMemoryManager();
        var pages = menMan.getMemoryPages();
        for (MemoryPage page : pages) {
            this.memoryStatus[page.getPageNumber()] = page.isOccupied() ? 1 : 0;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int cellWidth = width / PAGE_COUNT;

        // 绘制内存页面状态
        for (int i = 0; i < PAGE_COUNT; i++) {
            int x = i * cellWidth;
            int y = 0;
            int cellHeight = height;
            if (memoryStatus[i] == 1) {
                g.setColor(Color.BLUE); // 已分配状态
                g.fillRect(x, y, cellWidth, cellHeight);
            }
            g.setColor(Color.GREEN); // 空闲状态
            g.drawRect(x, y, cellWidth, cellHeight);

        }
    }
}