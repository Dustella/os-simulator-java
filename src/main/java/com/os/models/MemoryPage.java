package com.os.models;

public class MemoryPage {
    private final int pageNumber;
    private final int pageSize;
    private boolean occupied;

    public MemoryPage(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.occupied = false;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setOccupied() {
        this.occupied = true;
    }

    public void setFree() {
        this.occupied = false;
    }
}
