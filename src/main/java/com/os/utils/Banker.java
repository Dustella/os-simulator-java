package com.os.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Banker {
    List<List<Integer>> max = new ArrayList<>();
    List<List<Integer>> allocation = new ArrayList<>();
    List<List<Integer>> need = new ArrayList<>();
    List<Integer> available = new ArrayList<>();

    public static void main(String[] args) {
        // 初始化系统资源、进程数量和进程资源需求
        int n = 5; // 资源种类
        int m = 3; // 进程数量
        List<List<Integer>> max = new ArrayList<>();
        List<List<Integer>> allocation = new ArrayList<>();
        List<List<Integer>> need = new ArrayList<>();
        List<Integer> available = new ArrayList<>();

        // 初始化进程资源需求
        for (int i = 0; i < m; i++) {
            List<Integer> maxRow = new ArrayList<>();
            List<Integer> allocationRow = new ArrayList<>();
            List<Integer> needRow = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                maxRow.add(10);
                allocationRow.add(5);
                needRow.add(maxRow.get(j) - allocationRow.get(j));
            }
            max.add(maxRow);
            allocation.add(allocationRow);
            need.add(needRow);
        }

        // 初始化系统可用资源
        for (int i = 0; i < n; i++) {
            available.add(10);
        }

        // 创建一个Banker类的实例
        Banker banker = new Banker();

        // 输入进程资源需求
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                need.get(i).set(j, sc.nextInt());
            }
        }

        // 输入系统资源初始化情况
        for (int i = 0; i < n; i++) {
            available.set(i, sc.nextInt());
        }

        // 调用银行家算法
        banker.bank();
    }

    public void bank() {
        // 调用安全性检查函数
        if (safe()) {
            // 输出安全序列
            for (int i = 0; i < max.size(); i++) {
                System.out.print("P" + i + " ");
            }
            System.out.println();
        } else {
            System.out.println("系统不安全");
        }
    }

    public boolean safe() {
        // 检查系统是否安全
        for (int i = 0; i < max.size(); i++) {
            for (int j = 0; j < max.get(i).size(); j++) {
                if (need.get(i).get(j) > 0 && available.get(j) < need.get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
