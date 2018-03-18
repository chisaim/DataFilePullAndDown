package com.bjjh.MessMan;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestHdfs {


    public static void main(String[] args) {
        FileSystem fs = null;
        String hdfsUserName = "hadoop";
        Configuration configuration = new Configuration();
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
/*

        Path path = new Path("/abc");

        try {
            fs.mkdirs(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        Path HDFSPath = new Path("/data/new.txt");
        Path LocalPath = new Path("D:\\Download\\SogouQ3.txt\\new.txt");
        try {
            fs.copyFromLocalFile(LocalPath, HDFSPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
