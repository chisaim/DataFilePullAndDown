package com.bjjh.MessMan.model;


import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.io.IOException;

public class HdfsDAO {

    private Logger logger = Logger.getLogger(HdfsDAO.class);

    public boolean createPath(FileSystem fs,String uri){

        boolean b = false;
        Path path = new Path(uri);

        try {
            b = fs.mkdirs(path);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }

        return b;
    }

    /**
     * 从本地上传文件到HDFS
     * @param fs
     * @param HDFSPath
     * @param localPath
     */
    public void uploadFileFromHDFS(FileSystem fs,String HDFSPath,String localPath){

        Path fsPath = new Path(HDFSPath);
        Path loPath = new Path(localPath);

        try {
            fs.copyFromLocalFile(loPath,fsPath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }

    }

    /**
     * 从HDFS中下载文件到本地
     * @param fs
     * @param HDFSPath
     * @param localPath
     */
    public void downloadFileFromHDFS(FileSystem fs,String HDFSPath,String localPath){

        Path fsPath = new Path(HDFSPath);
        Path loPath = new Path(localPath);

        try {
            fs.copyToLocalFile(fsPath,loPath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }

    }



}
