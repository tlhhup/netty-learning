package org.tlh.refreshtoken.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class RefreshTokenTask implements Delayed {

    private long executeTime;//时间间隔

    public RefreshTokenTask(long executeTime) {
        this.executeTime = System.nanoTime()+executeTime*1000000000;
    }

    @Override
    public long getDelay(TimeUnit unit) {//执行的时间间隔
        return unit.convert(executeTime-System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        RefreshTokenTask tokenTask= (RefreshTokenTask) o;
        return executeTime>tokenTask.executeTime?1:(executeTime<tokenTask.executeTime?-1:0);
    }

}
