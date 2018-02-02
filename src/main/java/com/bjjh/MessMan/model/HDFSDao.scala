package com.bjjh.MessMan.model

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.mapred.JobConf


class HDFSDao() {

//  final val HDFS = ""

//  val Hdfs : String

//  val configuration : Configuration

  def HDFSDao(configuration: Configuration) :Unit = {
//    this(HDFS,configuration)
  }

  def getJobConf(jobName:String):JobConf = {
    val jobConf = new JobConf()
    jobConf.setJobName(jobName)
    jobConf.addResource("classpath:core-site.xml")
    jobConf.addResource("classpath:hdfs-site.xml")
    jobConf.addResource("classpath:mapred-site.xml")
    jobConf
  }

  def mkdirs(path:String):Unit = {
//    FileSystem.get(new Path(path),getJobConf())
  }
}
