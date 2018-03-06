package com.bjjh.MessMan.main

import java.io.File
import java.net.URI
import java.text.SimpleDateFormat

import com.bjjh.MessMan.config.GetConfigMess
import com.bjjh.MessMan.model.{Ftp4jFtpClient, HdfsDAO, TaskMess}
import com.bjjh.MessMan.util.{DownloadDataTransferListener, JdbcUtil, PrimaryGenerater, UploadDataTransferListener}
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

  val pg: PrimaryGenerater = PrimaryGenerater.getInstance

  def main(args: Array[String]): Unit = {

    /**
      * 开始第一阶段执行，内容是到FTP服务器的固定路径下载数据文件放入固定位置然后再上传到hadoop的HDFS上面。
      */
    logger.info("====================mession start ...====================")

    while (true) {
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
      logger.info("Transmission mode is TYPE_BINARY")

      /**
        * 开始第一阶段执行，内容是到FTP服务器的固定路径下载数据文件放入固定位置然后再上传到hadoop的HDFS上面。
        */
      logger.info("====================mession start ...====================")

      client.changeDirectory(configMess.getFTPFileSourceLocation())
      val list = client.list()
      logger.info("Start scanning the file under this path:" + client.currentDirectory())
      //扫描当前路径下的文件，空就继续扫描。
      if (list.isEmpty) {
        logger.info("The directory ==>" + client.currentDirectory() + " no file in this directory, continue to scan...")
      } else {
        for (file <- list) {
          logger.info(file)

          /** 接口文档中这个位置可能需要对文件进行校验，其规则是
            * 1.检查数据文件名称是否遵守规范中的命名规范。
            * 2.验证数据文件中的数据日志是否正确合理。
            * 3.数据文件的生成日期是否正确合理。
            * 4.校验文件中的大小与实际文件大小是否一致。
            */
          /**
            * if(){
            * 校验部分
            * } */


          val Tpath = new File(configMess.getFTPFileTargetLocation() + File.separator + configMess.getToday())
          val Tfilename = Tpath + File.separator + file.getName + ".data"
          //数据文件下载时目标路径存在则直接放置，否则新建目标路径再放置其中，之后删除服务器端的数据文件
          if (Tpath.exists()) {
            client.download(file.getName, new File(Tfilename), new DownloadDataTransferListener)
            logger.info("The file ==>" + file.getName + " has been successfully downloaded.")
            client.deleteFile(file.getName)
            logger.info("The file ==>" + file.getName + " has been successfully delete.")
          } else {
            Tpath.mkdirs()
            logger.info("Successfully create a date directory " + Tpath)
            client.download(file.getName, new File(Tfilename), new DownloadDataTransferListener)
            logger.info("The file ==>" + file.getName + " has been successfully downloaded.")
            client.deleteFile(file.getName)
            logger.info("The file ==>" + file.getName + " has been successfully delete.")
          }
          //日志记录设定值
          taskMessLog.setFilename(file.getName)
          taskMessLog.setModifyDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.getModifiedDate))
          taskMessLog.setFileSize(file.getSize)
          taskMessLog.setUpOrDownloadFlag(2)
          util.insert(taskMessLog)
          logger.info("The file transfer log has been recorded.")


          //建立hadoop的HDFS链接
          val fs = FileSystem.get(URI.create(configMess.getHdfsURI), new Configuration, configMess.getHdfsUserName)

          val fspath = new Path(configMess.getHdfsPath() + configMess.getToday())
          //          val fsfilename = new Path(configMess.getToday() + File.separator + file.getName)
          val hdfsfilename = new Path(fspath + File.separator + file.getName + "." + configMess.getTimestamp() + ".data")
          val localfilename = new Path(configMess.getFTPFileTargetLocation() + File.separator + configMess.getToday() + File.separator + file.getName + ".data")

          if (fs.exists(fspath)) {
            //将文件上传到HDFS上面。
            fs.copyFromLocalFile(localfilename, hdfsfilename)
            logger.info("The data has been uploaded to HDFS.")
          } else {
            //在HDFS上创建时间戳路径
            fs.mkdirs(fspath)
            logger.info("Successfully create a date directory in HDFS.")
            //将文件上传到HDFS上面。
            fs.copyFromLocalFile(localfilename, hdfsfilename)
            logger.info("The data has been uploaded to HDFS.")
          }
          fs.close()
          logger.info("HDFS closed.")
          logger.info("====================The first phase is over.====================")
        }
      }

      /** 第一阶段结束 */

      /**
        * 开始第二阶段执行，内容是将配置好的表数据导出到数据文件中，然后将其上传搭配FTP服务器的固定路径上。
        */
      //导出数据文件到指定目录
      val filename = "MPM_FILE_" + configMess.getTimestamp() + "_" + pg.generaterNextNumber("0000")

      val dataFilePath = new File(configMess.getDataFileOutputPath() + File.separator + configMess.getToday())
      //在简短时间下检查SQL导出数据文件目录中是否存在相同文件
      if (dataFilePath.exists()) {
        util.output(configMess.getDataFileOutputPath() + "/" + configMess.getToday(), filename)
        logger.info("Successfully export data files ==> " + filename + " to ==> " + configMess.getDataFileOutputPath() + File.separator + configMess.getToday())
      } else {
        dataFilePath.mkdirs()
        logger.info("Successfully create a date directory in mysqlpath.")
        util.output(configMess.getDataFileOutputPath() + "/" + configMess.getToday(), filename)
        logger.info("Successfully export data files ==> " + filename + " to ==> " + configMess.getDataFileOutputPath() + File.separator + configMess.getToday())
      }
      //切换目录之后将数据库导出的数据文件上传FTP服务器
      client.changeDirectory(configMess.getDataFileOutputPath())
      logger.info("It has switched to position ==> " + configMess.getDataFileOutputPath() + File.separator + configMess.getToday())
      val sqllist = client.list()
      logger.info("Start scanning the file under this path:" + client.currentDirectory())
      for (file <- sqllist) {
        logger.info(file)

        //        ftpClient.uploadFile(client, configMess.getDataFileOutputPath(), filename)
        client.upload(new File(filename + "_" + file.getSize + ".data"), new UploadDataTransferListener())
        logger.info("The file ==> " + filename + "_" + file.getSize + ".data" + " has been successfully uploaded.")

        //记录文件上传日志
        taskMessLog.setFilename(file.getName)
        taskMessLog.setModifyDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.getModifiedDate))
        taskMessLog.setFileSize(file.getSize)
        taskMessLog.setUpOrDownloadFlag(1)
        util.insert(taskMessLog)
        logger.info("The file transfer log has been recorded.")
      }

      //FTP传输完毕之后关闭连接
      client.disconnect(false) //安全退出
      logger.info("The FTP tool link has been safely logged out.")
      logger.info("====================The second phase is over.====================")
      System.exit(1)
      /** 第二阶段结束 */

      /**
        * 到指定位置下去加载数据文件到数据库，然后根据加载的数据更新表内容
        */
      //    数据库到指定位置去加载数据文件
      util.loadDataFile(configMess.getDataFileOutputPath(), filename)
      logger.info("Database load data file " + filename + " complete.")

      //将数据内容加载到新表，关联相关ID，更新其数据状态
      util.updateCol()
      logger.info("Updating the data table state has been completed.")
      logger.info("====================The third phase is over.====================")

      /** 第三阶段结束 */
      //依靠配置时间来控制循环频率
      Thread.sleep(configMess.getScanTime())
      logger.info("====================Phase cycle start.====================")
    }
  }


}

