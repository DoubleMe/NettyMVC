package com.chm.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DaemonThreadFactory implements ThreadFactory{

	final ThreadGroup group;
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;
    final String nameSuffix = "]";

    public DaemonThreadFactory(String poolName) {
        SecurityManager s = System.getSecurityManager();
        this.group = s != null?s.getThreadGroup():Thread.currentThread().getThreadGroup();
        this.namePrefix = poolName + " Pool [Thread-";
    }

    public DaemonThreadFactory(String poolName, ThreadGroup threadGroup) {
        this.group = threadGroup;
        this.namePrefix = poolName + " Pool [Thread-";
    }

    public ThreadGroup getThreadGroup() {
        return this.group;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement() + "]", 0L);
        t.setDaemon(true);
        if(t.getPriority() != 5) {
            t.setPriority(5);
        }
        return t;
    }

	
}
