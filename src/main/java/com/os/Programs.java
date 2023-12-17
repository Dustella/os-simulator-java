package com.os;

import com.os.models.Instuctions;
import com.os.models.Process;

import java.util.*;

public class Programs {


    // examples of instuctions:
//    malloc var 100 1s
//    free var 100 1s
//    readfile aa.txt 1s
//    writefile aa.txt 1s
//    createthread newname 1s
//    killthread newname 1s
//        usedevice printer 1s
    private List<List<String>> programs = new ArrayList<>();

    public Programs() {
        loadExamples();
    }

    private  List<String> parseProgram(String program){
        var result = new ArrayList<String>();
        for(var line:program.split("\n")){
            if(!line.trim().isEmpty()){
                result.add(line.trim());
            }
        }
        return result;
    }

    private void loadExamples() {
        var p1 = """
                malloc var 2 2s
                free var 2 2s
                """;
        var p2 = """              
                malloc var 2 2s
                free var 2 2s
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

//        flush into programs
//        split by \n and trim
        var p1List = parseProgram(p1);
        var p2List = parseProgram(p2);
        var p3List = parseProgram(p3);
        var p4List = parseProgram(p4);
        var p5List = parseProgram(p5);
        var p6List = parseProgram(p6);



        programs.add(p1List);
        programs.add(p2List);
        programs.add(p3List);
        programs.add(p4List);
        programs.add(p5List);
        programs.add(p6List);
    }

    public List<Process> parse(){
        var result = new ArrayList<Process>();
        var pids = new HashSet<Integer>();
        for (var program : programs) {
//             assemble a process
            Random random = new Random();
            var pid = random.nextInt(100);
            while(pids.contains(pid)){
                pid = random.nextInt(100);
            }
            pids.add(pid);
            var process = new Process(pid);
            process.setName("Process "+pid);
            List<Instuctions> insts = new ArrayList<Instuctions>();
            for(var line:program){
                if (line.trim().isEmpty()) {
                    continue;
                }
                var inst =new  Instuctions(line);
                insts.add(inst);
            }
            process.setInstructions(insts);
            result.add(process);
        }
        return result;
    }
}
