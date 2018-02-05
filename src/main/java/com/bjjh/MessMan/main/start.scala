package com.bjjh.MessMan.main

import com.bjjh.MessMan.config.GetConfigMess
import com.bjjh.MessMan.model.Ftp4jFtpClient
import org.apache.log4j.Logger

object start {

  val logger = Logger.getLogger(start.getClass.getName)

  val ftpClient = new Ftp4jFtpClient

  val configMess = new GetConfigMess

  def main(args: Array[String]): Unit = {

    logger.info("mession start ...")

    logger.info("The FTP tool starts to connect. ...")
    ftpClient.ftpConnect(configMess.getFTPServer(), configMess.getFTPPort())
    logger.info("FTP tool connection successful.")


    logger.info("The FTP tool login begins ...")
    ftpClient.ftpLogin(configMess.getFTPUser(), configMess.getFTPPasswd())
    logger.info("FTP tool login successful.")

    logger.info("It has switched to position:" + ftpClient.getCurrentDirectory())
    while (true) {
      logger.info("Start scanning the file under this path:" + ftpClient.getCurrentDirectory())
      if (ftpClient.setPathGetListIsEmpty(configMess.getFTPFileSourceLocation())) {
        logger.warn("The directory " + ftpClient.getCurrentDirectory() + " no file in this directory, continue to scan...")
      } else {
//        ftpClient.setPathGetFileList(configMess.getFTPFileSourceLocation())
        logger.info("this is file ...")
      }

      Thread.sleep(configMess.getScanTime())
    }

  }


}
