package com.horstmann.violet.application.gui.util.chenzuo.Bean;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by geek on 2017/8/12.
 */
public class IPNode {
    private String ip;
    private String type;
    private boolean isBusy = false;

    private static ReentrantLock lock = new ReentrantLock();

    public IPNode(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        lock.tryLock();
        try{
            this.type = type;
        }finally {
            lock.unlock();
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        lock.tryLock();
        try {
            isBusy = busy;
        }finally {
            lock.unlock();
        }
    }

    public void reset(){
        type = "";
        isBusy = false;
    }
}
