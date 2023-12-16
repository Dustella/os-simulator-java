package com.os.utils;

import com.os.core.ProcessQueue;

public interface SchedulingAlgorithm {
    void Schedule(ProcessQueue queue);
}
