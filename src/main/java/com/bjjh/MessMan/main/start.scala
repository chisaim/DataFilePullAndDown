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

  def main(args: Array[String]): Unit = {

    logger.info("mession start ...")

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

    logger.info("It has switched to position:" + client.changeDirectory(configMess.getFTPFileSourceLocation()) + configMess.getFTPFileSourceLocation())
    while (true) {
      logger.info("Start scanning the file under this path:" + client.currentDirectory())
      if (client.list(client.currentDirectory()).isEmpty) {
        logger.warn("The directory " + client.currentDirectory() + " no file in this directory, continue to scan...")
      } else {
        for (file <- client.list(client.currentDirectory())) {
          //扫描目录之后取文件类型
          if (file.getType == FTPFile.TYPE_FILE) {
            logger.info("This path ========>" + client.currentDirectory() + " and existing files ========>" + file.getName)

            val path = new File(configMess.getFTPFileTargetLocation() + File.separator + configMess.getTimestamp())
            if (!path.exists()) {
              path.mkdirs()
            }
            ftpClient.downloadFile(client, configMess.getFTPFileSourceLocation(), configMess.getFTPFileTargetLocation() + configMess.getTimestamp(), file.getName)
            logger.info("The file " + file.getName + " has been successfully downloaded.")

            util.insert()
            logger.info("The file transfer log has been recorded.")

            val fs = FileSystem.get(URI.create(configMess.getHDFSURI), new Configuration, configMess.getHdfsUserName)
            if (fs.exists(new Path(configMess.getTimestamp() + File.separator + file.getName))) {
              logger.error("There are already files " + configMess.getTimestamp() + File.separator + file.getName + " in HDFS.")
            } else {
              fs.mkdirs(new Path(configMess.getTimestamp()))
              logger.info("Successfully create a date directory in HDFS.")
              fs.copyFromLocalFile(new Path(configMess.getFTPFileTargetLocation()+ configMess.getTimestamp() + File.separator + file.getName), new Path(configMess.getTimestamp() + File.separator + file.getName))
              logger.info("The data has been uploaded to HDFS.")

            }


          }
        }
      }

      Thread.sleep(configMess.getScanTime())
    }

  }


}
