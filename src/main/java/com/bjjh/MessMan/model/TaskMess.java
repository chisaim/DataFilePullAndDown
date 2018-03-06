package com.bjjh.MessMan.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务传输日志登记对象
 */
public class TaskMess {
    /* 传输文件名 */
    private String filename;
    /* 传输文件大小 */
    private Long fileSize;
    /* 文件最后修改时间 */
    private String modifyDate;
    /* 传输文件标志 */
    private String upOrDownloadFlag;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileSize() {
        return String.valueOf(fileSize);
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getTaskTimeAndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public String getUpOrDownloadFlag() {
        return upOrDownloadFlag;
    }

    public void setUpOrDownloadFlag(int upOrDownloadFlag) {
        if (1 == upOrDownloadFlag) {
            this.upOrDownloadFlag = "upload";
        }else if(2 == upOrDownloadFlag){
            this.upOrDownloadFlag = "download";
        }

    }
}
