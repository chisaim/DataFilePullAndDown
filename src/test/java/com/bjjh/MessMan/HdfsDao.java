package com.bjjh.MessMan;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapred.JobConf;
import org.apache.log4j.Logger;

/**
 * 利用HDFS java API操作文件
 */
class HdfsDAO {

    public static void main(String[] args) {

        FileSystem fs = null;

        String hdfsUserName = "hadoop";

        Configuration configuration = new Configuration();

//        configuration.set("fs.defaultFS", "hdfs://192.168.102.75:9000");

        URI uri = null;

        try {
            uri = new URI("hdfs://192.168.102.75:9000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            fs = FileSystem.get(uri, configuration, hdfsUserName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Path path = new Path("/abc");

        try {
            fs.mkdirs(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

/*
        Path HDFSPath = new Path("/data/new.txt");
        Path LocalPath = new Path("D:\\Download\\SogouQ3.txt\\new.txt");

        try {
            fs.copyFromLocalFile(LocalPath,HDFSPath);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
*/


    }

}