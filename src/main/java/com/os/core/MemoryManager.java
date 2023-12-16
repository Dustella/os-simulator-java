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
    }

    public int allocate(int size) {
        int numPagesRequired = (int) Math.ceil((double) size / pageSize);
        if (numPagesRequired > getFreePageCount()) {
            // Not enough free pages, perform page replacement
            performPageReplacement(numPagesRequired - getFreePageCount());
        }

        int startIndex = findContiguousPages(numPagesRequired);
        if (startIndex != -1) {
            // Allocate pages
            for (int i = startIndex; i < startIndex + numPagesRequired; i++) {
                MemoryPage page = new MemoryPage(i, pageSize);
                memoryPages.add(i, page);
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
            memoryPages.remove(i);
            pageQueue.remove(i);
        }
    }

    private int findContiguousPages(int numPagesRequired) {
        int consecutiveFreePages = 0;
        for (int i = 0; i < memoryPages.size(); i++) {
            if (!memoryPages.get(i).isOccupied()) {
                consecutiveFreePages++;
                if (consecutiveFreePages == numPagesRequired) {
                    return i - numPagesRequired + 1;
                }
            } else {
                consecutiveFreePages = 0;
            }
        }
        return -1; // No contiguous pages found
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
