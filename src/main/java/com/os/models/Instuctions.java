package com.os.models;


import com.os.core.OS;
import com.os.models.InstructionAction;
import com.os.models.Process;

public class Instuctions {

    private InstructionAction instructionAction;


    private int time;

    private int amount;

    private Process currentProcess;

    private String target;


    public void setCurrentProcess(Process currentProcess) {
        this.currentProcess = currentProcess;
    }

    public Instuctions(String instruction) {
        // examples of instuctions:
//    malloc var 100 1s
//    free var 100 1s
//    readfile aa.txt 1s
//    writefile aa.txt 1s
//    createthread newname 1s
//    killthread newname 1s
//        usedevice printer 1s
        //releasedevice printer 1s
        var instructions = instruction.split(" ");
        String action = instructions[0];
        switch (action) {
            case "malloc" -> {
                this.instructionAction = InstructionAction.Malloc;
                this.amount = Integer.parseInt(instructions[2]);
                this.target = instructions[1];
            }
            case "free" -> {
                this.instructionAction = InstructionAction.Free;
                this.amount = Integer.parseInt(instructions[2]);
                this.target = instructions[1];
            }
            case "readfile" -> {
                this.instructionAction = InstructionAction.ReadFile;
                this.target = instructions[1];
            }
            case "writefile" -> {
                this.instructionAction = InstructionAction.WriteFile;
                this.target = instructions[1];
            }
            case "createthread" -> {
                this.instructionAction = InstructionAction.CreateThread;
                this.target = instructions[1];
            }
            case "killthread" -> {
                this.instructionAction = InstructionAction.KillThread;
                this.target = instructions[1];
            }
            case "idle" -> this.instructionAction = InstructionAction.Idle;
            case "usedevice" -> {
                this.instructionAction = InstructionAction.UseDevice;
                this.target = instructions[1];
            }
            case "releasedevice" -> {
                this.instructionAction = InstructionAction.ReleaseDevice;
                this.target = instructions[1];
            }
            default -> throw new RuntimeException("Invalid instruction");
        }

//        read time
        this.time = Integer.parseInt(instructions[instructions.length - 1].substring(0, instructions[instructions.length-1].length() - 1));
    }


    public boolean isIO() {
        return instructionAction == InstructionAction.ReadFile || instructionAction == InstructionAction.WriteFile;
    }

    public InstructionAction getAction() {
        return instructionAction;
    }

    public int getTime() {
        return time;
    }

    public int getAmount() {
        return amount;
    }

    public String getTarget() {
        return target;
    }

    public void excute() {
        var OSInstance = OS.getInstance();
        var memoryManager = OSInstance.getMemoryManager();
        var diskManager = OSInstance.getDiskManager();
        var threadManager = OSInstance.getThreadManager();
        var scheduler = OSInstance.getScheduler();
        var deviceManager = OSInstance.getDeviceManager();

        // malloc
        switch (this.getAction()) {
            case Malloc -> {
                int addr = memoryManager.allocate(this.getAmount());
                currentProcess.addMemoryMapping(this.getTarget(), addr, this.getAmount());
            }
            case Free -> {
                var location = currentProcess.getMemoryLocation(this.getTarget());
                memoryManager.free(location.getKey(), location.getValue());
            }
            case ReadFile -> {
                scheduler.BlockProcess(currentProcess, getTime());
                diskManager.readFile(this.getTarget(),this.currentProcess.getPid());
            }
            case WriteFile -> {
                scheduler.BlockProcess(currentProcess, getTime());
                diskManager.writeFile(this.getTarget(),this.currentProcess.getPid());
            }
            case CreateThread -> {
                threadManager.addThread(this.getTarget());
            }
            case KillThread -> {
                threadManager.removeThread(this.getTarget());
            }
            case Idle -> {
                scheduler.schedule();
            }
            case UseDevice -> {
                deviceManager.useDevice(this.getTarget(),this.currentProcess.getPid());
            }
            case ReleaseDevice -> {
                deviceManager.releaseDevice(this.getTarget());
            }
        }

    }
        public void excuteWithTime(int timing){
//          block thread to   wait for time
            try {
                Thread.sleep(timing * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            excute();

        }


}
