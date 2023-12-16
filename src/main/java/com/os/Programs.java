package com.os;

import com.os.models.Instuctions;
import com.os.models.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private void loadExamples() {
        var p1 = """
                malloc var 100 2s
                free var 100 2s
                """;
        var p2 = """              
                malloc var 100 2s
                free var 100 2s
                """;
        var p3 = """
                usedevice printer 2s
                createthread newname 2s
                killthread newname 2s
                releasedevice printer 2s
                """;

//        flush into programs
//        split by \n and trim
        var p1List = new ArrayList<String>();
        var p2List = new ArrayList<String>();
        var p3List = new ArrayList<String>();
        for (var line : p1.split("\n")) {
            //if is not "" empty
            if (!line.trim().isEmpty()) {
                p1List.add(line.trim());
            }
        }
        for (var line : p2.split("\n")) {
            if (!line.trim().isEmpty()) {
                p2List.add(line.trim());
            }
//            p2List.add(line.trim());
        }
        for (var line : p3.split("\n")) {

            if (!line.trim().isEmpty()) {
                p3List.add(line.trim());
            }
//            p3List.add(line.trim());
        }
        programs.add(p1List);
        programs.add(p2List);
        programs.add(p3List);
    }

    public List<Process> parse(){
        var result = new ArrayList<Process>();
        for (var program : programs) {
//             assemble a process
            Random random = new Random();
            var pid = random.nextInt(100);

            var process = new Process(pid);
            process.setName("Process "+pid);
            List<Instuctions> insts = new ArrayList<Instuctions>();
            for(var line:program){
                var inst =new  Instuctions(line);
                insts.add(inst);
            }
            process.setInstructions(insts);
            result.add(process);
        }
        return result;
    }
}
