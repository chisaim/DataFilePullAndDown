package com.bjjh.MessMan.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskMess {

    private String taskTimeAndDate;

    public String getTaskTimeAndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        return sdf.format(new Date());
    }
}
