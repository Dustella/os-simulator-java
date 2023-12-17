package com.os.core;
import com.os.models.MemoryPage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MemoryManager {
    private final int totalMemorySize;
    private final int pageSize;
    private final List<MemoryPage> memoryPages;
    private final Queue<Integer> pageQueue;

    public MemoryManager(int totalMemorySize, int pageSize) {
        this.totalMemorySize = totalMemorySize;
        this.pageSize = pageSize;
        this.memoryPages = new ArrayList<>();
        this.pageQueue = new LinkedList<>();

        // initialize memory pages
        for (int i = 0; i < totalMemorySize / pageSize; i++) {
            memoryPages.add(new MemoryPage(i, pageSize));
        }
    }

    public int allocate(int size) {
        int numPagesRequired = (int) Math.ceil((double) size / pageSize);
//        if (numPagesRequired > getFreePageCount()) {
//            // Not enough free pages, perform page replacement
//            performPageReplacement(numPagesRequired - getFreePageCount());
//        }

        int startIndex = findContiguousPages(numPagesRequired);
        if (startIndex != -1) {
            // Allocate pages
            for (int i = startIndex; i < startIndex + numPagesRequired; i++) {
                memoryPages.get(i).setOccupied(true);
                pageQueue.add(i);
            }
            return startIndex * pageSize;
        }
        return -1; // Allocation failed
    }

    public void free(int address, int size) {
        int startIndex = address / pageSize;
        int numPagesToFree = (int) Math.ceil((double) size / pageSize);

        // Free pages
        for (int i = startIndex; i < startIndex + numPagesToFree; i++) {
            memoryPages.get(i).setOccupied(false);
        }
    }

    public List<MemoryPage> getMemoryPages() {
        return memoryPages;
    }

    private int findContiguousPages(int numPagesRequired) {
        int startIndex = -1;
        int count = 0;
        for (int i = 0; i < memoryPages.size(); i++) {
            if (!memoryPages.get(i).isOccupied()) {
                if (startIndex == -1) {
                    startIndex = i;
                }
                count++;
                if (count == numPagesRequired) {
                    return startIndex;
                }
            } else {
                startIndex = -1;
                count = 0;
            }
        }
        return -1;
    }

    private int getFreePageCount() {
        return totalMemorySize / pageSize - memoryPages.size();
    }

    private void performPageReplacement(int numPagesToReplace) {
        for (int i = 0; i < numPagesToReplace; i++) {
            int pageToRemove = pageQueue.poll();
            memoryPages.remove(pageToRemove);
        }
    }


//
}
