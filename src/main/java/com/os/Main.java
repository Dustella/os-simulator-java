package com.os;

import com.os.core.AProducer;
import com.os.core.OS;
import com.os.core.Scheduler;
import com.os.ui.MainFrame;
import com.os.utils.FCFS;
import com.os.utils.SJF;
import com.os.utils.SchedulingAlgorithm;

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

        SchedulingAlgorithm alg = null;

        if (line.equals("1")) {
            System.out.println("你选择了先来先执行算法");
            Scheduler.mode = "先来先执行算法";
            alg = new FCFS();
        } else if (line.equals("2")) {
            System.out.println("你选择了短作业优先算法");
            Scheduler.mode = "短作业优先算法";
            alg = new SJF();
        } else {
            System.out.println("你选择了默认算法 先来先执行算法");
        }
        scanner.close();

        System.out.println("正在启动......");

        var programs = Programs.getInstance();
        var processes = programs.parse();

        var os = OS.getInstance();
        var pcbs = os.getScheduler().getPCBS();
        var firstProcess = processes.remove(0);
        pcbs.addToReadyQueue(firstProcess);
        for (var process : processes) {
            pcbs.addToPreReadyQueue(process);
        }
        os.getScheduler().setScheduleAlgorithm(alg);

        var excutor = Executors.newSingleThreadExecutor();
        var bankerExcuter = Executors.newSingleThreadExecutor();
        var dummyExcutor = Executors.newSingleThreadExecutor();
        // run a none blocking process
        // .addToReadyQueue();
        bankerExcuter.submit(() -> {
            os.getDeadlockHandler().DoLoop();
        });
        excutor.submit(() -> {
            os.getScheduler().schedule();
        });
        dummyExcutor.submit(() -> {
            AProducer.loooooog();
        });

        MainFrame.main();

    }
}