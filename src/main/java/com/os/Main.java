package com.os;

import com.os.core.OS;
import com.os.models.Instuctions;
import com.os.ui.MainFrame;
import com.os.models.Process;

import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Ctrl+. with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it
        var os = OS.getInstance();
        var pcbs = os.getScheduler().getPCBS();
        var programs = new Programs();
        var processes = programs.parse();
        for (var process : processes) {
            pcbs.addToReadyQueue(process);
        }
//        .addToReadyQueue();
        MainFrame.main(args);
    }
}