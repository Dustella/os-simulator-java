package com.os;

import com.os.models.InstructionAction;
import com.os.models.Instuctions;
import com.os.models.Process;

import java.util.*;

public class Programs {

    // examples of instuctions:
    // malloc var 100 1s
    // free var 100 1s
    // readfile aa.txt 1s
    // writefile aa.txt 1s
    // createthread newname 1s
    // killthread newname 1s
    // usedevice printer 1s
    static Programs _instance = null;

    public static Programs getInstance() {
        if (_instance == null) {
            _instance = new Programs();
        }
        return _instance;
    }

    private List<List<String>> programs = new ArrayList<>();

    private List<Process> result;

    public Programs() {
        loadExamples();
    }

    private List<String> parseProgram(String program) {
        var result = new ArrayList<String>();
        for (var line : program.split("\n")) {
            if (!line.trim().isEmpty()) {
                result.add(line.trim());
            }
        }
        return result;
    }

    private void loadExamples() {
        List<String> programsRaw = new ArrayList<String>();
        var p0 = """
                createpipe pipe1 2s
                createpipe pipe3 2s
                writepipe pipe1 content 2s
                readpipe pipe1 2s
                """;

        var p1 = """
                createpipe pipe3 2s
                malloc var 6 2s
                free var 6 2s
                """;
        var p2 = """
                malloc var 6 2s
                free var 6 2s
                """;
        var p3 = """
                usedevice printer 2s
                createthread newname 2s
                killthread newname 2s
                releasedevice printer 2s
                """;
        var p4 = """
                readfile aa.txt 2s
                writefile aa.txt 2s
                """;
        var p5 = """
                createthread p55 2s
                killthread p55 2s
                """;
        var p6 = """
                malloc var 2 2s
                free var 2 2s
                """;
        programsRaw.add(p0);
        programsRaw.add(p1);
        programsRaw.add(p2);
        programsRaw.add(p3);
        programsRaw.add(p4);
        programsRaw.add(p5);
        programsRaw.add(p6);

        for (var lines : programsRaw) {
            var list = parseProgram(lines);
            programs.add(list);
        }

    }

    public List<Process> parse() {
        var result = new ArrayList<Process>();
        var pids = new HashSet<Integer>();
        for (var program : programs) {
            // assemble a process
            Random random = new Random();
            var pid = random.nextInt(100);
            while (pids.contains(pid)) {
                pid = random.nextInt(100);
            }
            pids.add(pid);
            var process = new Process(pid);
            process.setName("Process " + pid);
            List<Instuctions> insts = new ArrayList<Instuctions>();
            for (var line : program) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                var inst = new Instuctions(line);
                insts.add(inst);
            }
            process.setInstructions(insts);
            result.add(process);
        }
        this.result = result;
        return result;
    }

    public List<String> getAllFiles() {
        var files = new HashSet<String>();
        for (var p : result) {
            for (var action : p.getInstructions()) {
                if (action.getAction() == InstructionAction.ReadFile
                        || action.getAction() == InstructionAction.WriteFile) {
                    files.add(action.getTarget());
                }
            }
        }
        return new ArrayList<>(files);
    }

    public List<String> getAllThreads() {

        var threads = new HashSet<String>();
        for (var p : result) {
            for (var action : p.getInstructions()) {
                if (action.getAction() == InstructionAction.CreateThread
                        || action.getAction() == InstructionAction.KillThread) {
                    threads.add(action.getTarget());
                }
            }
        }

        return new ArrayList<>(threads);

    }

    public List<String> getAllPipes() {
        var pipes = new HashSet<String>();
        for (var p : result) {
            for (var action : p.getInstructions()) {
                if (action.getAction() == InstructionAction.CreatePipe
                        || action.getAction() == InstructionAction.WritePipe
                        || action.getAction() == InstructionAction.ReadPipe) {
                    pipes.add(action.getTarget());
                }
            }
        }
        return new ArrayList<>(pipes);
    }
}
