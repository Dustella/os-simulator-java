package com.os;

import com.os.core.OS;
import com.os.core.Scheduler;
import com.os.ui.MainFrame;
import java.util.concurrent.Executors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Ctrl+. with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it
        // read from console
        System.out.println("请选择调度算法：");
        System.out.println("1、先来先执行算法");
        System.out.println("2、短作业优先算法");
        var scanner = new java.util.Scanner(System.in);
        // read a line
        var line = scanner.nextLine();

        if (line.equals("1")) {
            System.out.println("你选择了先来先执行算法");
            Scheduler.mode = "先来先执行算法";
        } else if (line.equals("2")) {
            System.out.println("你选择了短作业优先算法");
            Scheduler.mode = "短作业优先算法";
        } else {
            System.out.println("你选择了默认算法 先来先执行算法");
        }
        scanner.close();

        System.out.println("正在启动......");

        var os = OS.getInstance();
        var pcbs = os.getScheduler().getPCBS();
        var programs = new Programs();
        var processes = programs.parse();
        for (var process : processes) {
            pcbs.addToPreReadyQueue(process);
        }
        os.getScheduler().setScheduleAlgorithm(new com.os.utils.FCFS());

        var excutor = Executors.newSingleThreadExecutor();
        var bankerExcuter = Executors.newSingleThreadExecutor();
        // run a none blocking process
        excutor.submit(() -> {
            os.getScheduler().schedule();
        });
        bankerExcuter.submit(() -> {
            os.getDeadlockHandler().DoLoop();
        });
        // .addToReadyQueue();
        MainFrame.main(args);

    }
}