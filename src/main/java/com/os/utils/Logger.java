package com.os.utils;

import com.os.ui.MainFrame;

public class Logger {

   public static void log(String message) {
        System.out.println(message);
        var frame = MainFrame.getInstance();
        frame.log(message + "\n");
    }

}
