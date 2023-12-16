package com.os.models;


import com.os.core.OS;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




public class Process {

    private String name;

    private int pid;

    private List<Instuctions> instructions = new LinkedList<>();

    private ProcessStatus status;

    private int timeLeftInCurrentInstruction;


    private int pc = 0;

    private Map<String, Pair<Integer,Integer>> memoryMapping = new HashMap<>();


    private List<String> openingFiles = new LinkedList<>();


    private List<String> usingDevices = new LinkedList<>();

    public Process(int pid) {
        this.pid = pid;
        this.status = ProcessStatus.READY;
        this.pc = 0;
    }

    public Instuctions getCurrentInstruction() {
        return instructions.get(pc);
    }

    public Pair<Integer, Integer> getMemoryLocation(String varName) {
        return memoryMapping.get(varName);
    }

    public void setInstructions(List<Instuctions> instructions) {
        this.instructions = instructions;
        // for each instruction, set the current process
        for (var instruction : instructions) {
            instruction.setCurrentProcess(this);
        }
    }

    //    add pc
    public void addPc() {
        this.pc++;
    }

    public ProcessResult runWithTime(int time) {
        // run the process
        this.status = ProcessStatus.RUNNING;
        // if the process is blocked, return Result.IOBlocked
        // if the process is terminated, return Result.Done
        // if the process is still running, return Result.StillRunning
        int timeLeft = time;

        do {
            var currentInstruction = instructions.get(pc);
            int timeNeed = currentInstruction.getTime();
            if (currentInstruction.isIO()) {
                var osInstance = OS.getInstance();
                var scheduler = osInstance.getScheduler();
                scheduler.BlockProcess(this, currentInstruction.getTime());
                return ProcessResult.IOBlocked;
            }
            if (timeNeed > timeLeft) {
                this.timeLeftInCurrentInstruction = timeNeed - timeLeft;
                return ProcessResult.StillRunning;
            }
            currentInstruction.excute();
            timeLeft = timeLeft - timeNeed;
            addPc();
        }
        while (pc < instructions.size());

        return ProcessResult.Done;
    }

    public ProcessResult runToDie() {
        // run the process until it is terminated
        // return Result.Done
        for (var i = 0; i < instructions.size(); i++) {
            var currentInstruction = instructions.get(pc);
            var action = currentInstruction.getAction();
            if (action == InstructionAction.ReadFile || action == InstructionAction.WriteFile) {
                // if the process is blocked, return Result.IOBlocked
                var osInstance = OS.getInstance();
                var scheduler = osInstance.getScheduler();
                scheduler.BlockProcess(this, currentInstruction.getTime());
                return ProcessResult.IOBlocked;
            }

        }
        return ProcessResult.Done;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return this.pid;
    }
}
