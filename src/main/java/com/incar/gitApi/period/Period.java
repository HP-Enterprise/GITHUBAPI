package com.incar.gitApi.period;
import com.incar.gitApi.util.DateUtil;

import java.util.*;
/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class Period {
    private final int id;
    private final Date start;
    private final Date end;
    private final boolean isWorkTime; //是否为工作时间
    private boolean isInWork;         //是否处于工作状态

    public Period(int id, Date start, Date end, boolean isInWork, boolean isWorkTime) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.isInWork = isInWork;
        this.isWorkTime = isWorkTime;
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }

    public int id() {
        return id;
    }

    public boolean isWorkTime() {
        return isWorkTime;
    }

    public boolean getIsInWork() {
        return isInWork;
    }

    public void setIsInWork(boolean isInWork) {
        this.isInWork = isInWork;
    }

    @Override
    public String toString() {
        return "Period{" +
                "id=" + id +
                ", start=" + DateUtil.formatDate(start) +
                ", end=" +  DateUtil.formatDate(end) +
                ", isInWork=" + isInWork +
                ", isWorkTime=" + isWorkTime +
                "}\n";
    }

}
