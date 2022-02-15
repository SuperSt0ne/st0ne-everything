package com.rango.common.lock;

public interface DistributeLock {

    void acquireLock();

    void acquireLock(String path);

    boolean releaseLock();

    boolean releaseLock(String path);
}
