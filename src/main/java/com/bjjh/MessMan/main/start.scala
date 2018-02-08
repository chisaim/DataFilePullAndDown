package com.bjjh.MessMan.main

import java.io.File
import java.net.URI

import com.bjjh.MessMan.config.GetConfigMess
import com.bjjh.MessMan.model.{Ftp4jFtpClient, HdfsDAO, TaskMess}
import com.bjjh.MessMan.util.JdbcUtil
import it.sauronsoftware.ftp4j.{FTPClient, FTPFile}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.log4j.Logger

object start {

  val logger = Logger.getLogger(start.getClass.getName)

  val configMess = new GetConfigMess

  val client = new FTPClient

  val ftpClient = new Ftp4jFtpClient

  val util = new JdbcUtil

  val hdfs = new HdfsDAO

  val taskMessLog = new TaskMess

  def main(args: Array[String]): Unit = {

    //    开始第一阶段执行，内容是到FTP服务器的固定路径下载数据文件放入固定位置然后再上传到hadoop的HDFS上面。
    logger.info("====================mession start ...====================")

    logger.info("The FTP tool starts to connect. ...")
    client.connect(configMess.getFTPServer(), configMess.getFTPPort())
    logger.info("FTP tool connection successful.")

    logger.info("The FTP tool login begins ...")
    client.login(configMess.getFTPUser(), configMess.getFTPPasswd())
    logger.info("FTP tool login successful.")

    logger.info("The database connection begins to connect...")
    util.getConnection()
    logger.info("The database is successfully connected.")

    client.setType(FTPClient.TYPE_BINARY)

    excefirst()

    excesecond()

    excethrid()

  }

  /**
    * 开始第一阶段执行，内容是到FTP服务器的固定路径下载数据文件放入固定位置然后再上传到hadoop的HDFS上面。
    */
  def excefirst(): Unit = {

    logger.info(
      "It has switched to position ==>" + client.changeDirectory(
        configMess.getFTPFileSourceLocation()) + configMess
        .getFTPFileSourceLocation())
    while (true) {
      //扫描当前路径下的文件，空就继续扫描。
      logger.info(
        "Start scanning the file under this path:" + client.currentDirectory())
      if (client.list(client.currentDirectory()).isEmpty) {
        logger.warn(
          "The directory ==>" + client
            .currentDirectory() + " no file in this directory, continue to scan...")
      } else {
        for (file <- client.list(client.currentDirectory())) {
          //扫描目录之后取文件类型
          if (file.getType == FTPFile.TYPE_FILE) {
            logger.info(
              "This path ==>" + client
                .currentDirectory() + " and existing files ==>" + file.getName)

            val path = new File(
              configMess
                .getFTPFileTargetLocation() + File.separator + configMess
                .getTimestamp())
            if (!path.exists()) {
              path.mkdirs()
            }
            //将文件下载到配置位置
            ftpClient.downloadFile(
              client,
              configMess.getFTPFileSourceLocation(),
              configMess.getFTPFileTargetLocation() + configMess.getTimestamp(),
              file.getName)
            logger.info(
              "The file ==>" + file.getName + " has been successfully downloaded.")
            //数据库记录文件下载日志
            taskMessLog.setFilename(file.getName)
            taskMessLog.setFileSize(file.getSize())
            taskMessLog.setUpOrDownloadFlag(2)
            util.insert(taskMessLog)
            logger.info("The file transfer log has been recorded.")
            //建立hadoop的HDFS链接
            val fs = FileSystem.get(URI.create(configMess.getHDFSURI),
              new Configuration,
              configMess.getHdfsUserName)
            if (fs.exists(new Path(
              configMess.getTimestamp() + File.separator + file.getName))) {
              logger.error(
                "There are already files ==>" + configMess
                  .getTimestamp() + File.separator + file.getName + " in HDFS.")
            } else {
              //在HDFS上创建时间戳路径
              fs.mkdirs(new Path(configMess.getTimestamp()))
              logger.info("Successfully create a date directory in HDFS.")
              //将文件上传到HDFS上面。
              fs.copyFromLocalFile(
                new Path(
                  configMess.getFTPFileTargetLocation() + configMess
                    .getTimestamp() + File.separator + file.getName),
                new Path(
                  configMess.getTimestamp() + File.separator + file.getName)
              )
              logger.info("The data has been uploaded to HDFS.")
            }
          }
        }
      }
      //依靠配置时间来控制循环频率
      Thread.sleep(configMess.getScanTime())
      logger.info(
        "====================The first phase is over.====================")
    }
  }

  /*第一阶段结束*/
  /**
    * 开始第二阶段执行，内容是将配置好的表数据导出到数据文件中，然后将其上传搭配FTP服务器的固定路径上。
    */
  def excesecond(): Unit = {
    //导出数据文件到指定目录
    val filename = (configMess.getTimestamp() + "_file.txt")
      .substring(1, (configMess.getTimestamp() + "_file.txt").size)
    if (new File(configMess.getDataFileOutputPath() + filename).exists()) {
      new File(configMess.getDataFileOutputPath() + filename).delete()
    } else {
      util.output(configMess.getDataFileOutputPath(), filename)
    }
    logger.info(
      "Successfully export data files ==> " + filename + " to ==> " + configMess
        .getDataFileOutputPath() + ". ")

    //切换目录之后将数据库导出的数据文件上传FTP服务器
    client.changeDirectory(configMess.getFTPFileSourceLocation())
    logger.info(
      "It has switched to position ==> " + client.changeDirectory(
        configMess.getFTPFileSourceLocation()) + configMess
        .getFTPFileSourceLocation())
    //    ftpClient.uploadFile(client,configMess.getDataFileOutputPath(),filename)
    logger.info("The file ==> " + filename + " has been successfully uploaded.")

    //记录文件上传日志
    taskMessLog.setFilename(filename)
    taskMessLog.setFileSize(
      new File(configMess.getFTPFileSourceLocation() + filename).getUsableSpace)
    taskMessLog.setUpOrDownloadFlag(1) //文件上传
    util.insert(taskMessLog)
    logger.info("The file transfer log has been recorded.")

  }

  /*第二阶段结束*/
  /**
    * 到指定位置下去加载数据文件到数据库，然后根据加载的数据更新表内容
    */
  def excethrid(): Unit = {
    //    数据库到指定位置去加载数据文件
    val filename = (configMess.getTimestamp() + "_file.txt")
      .substring(1, (configMess.getTimestamp() + "_file.txt").size)
    util.loadDataFile(configMess.getDataFileOutputPath(), filename)
    logger.info("Database load data file " + filename + " complete.")

    //将数据内容加载到新表，关联相关ID，更新其数据状态
    util.updateCol()
    logger.info("Updating the data table state has been completed.")
  }

  /*第三阶段结束*/
}
