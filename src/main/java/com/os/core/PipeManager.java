package com.os.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.os.Programs;

import javafx.util.Pair;

public class PipeManager {

    private List<String> allPipes = new ArrayList<>();

    private Map<String, Pair<List<String>, List<String>>> pipeMapping = new HashMap<>();

    public Map<String, Pair<List<String>, List<String>>> getPipeMapping() {
        return pipeMapping;
    }

    public PipeManager() {
        var programs = Programs.getInstance();
        allPipes = programs.getAllPipes();
    }

    public void createPipe(String name) {
        Pair<List<String>, List<String>> mapper = new Pair<>(new ArrayList<String>(), new ArrayList<String>());
        pipeMapping.put(name, mapper);
    }

    public void writePipe(String processName) {
        Pair<List<String>, List<String>> mapper = pipeMapping.get(processName);
        mapper.getValue().add(processName);

    }

    public void readPipe(String processName) {
        Pair<List<String>, List<String>> mapper = pipeMapping.get(processName);
        mapper.getKey().add(processName);
    }

    public void setAllPipes(List<String> allPipes) {
        this.allPipes = allPipes;
    }

    public List<String> getStatus() {
        List<String> result = new ArrayList<>();
        for (var pipe : allPipes) {
            // if pipe is not created
            if (!pipeMapping.containsKey(pipe)) {
                result.add(pipe + " is not created");
            } else {
                var mapper = pipeMapping.get(pipe);
                var writer = mapper.getValue();
                var reader = mapper.getKey();
                if (writer.size() == 0 && reader.size() == 0) {
                    result.add(pipe + " is not used");
                } else {
                    result.add(pipe + " is used by " + writer + " and " + reader);
                }
            }
        }
        return result;
    }

}
