package com.bjjh.MessMan.config

import java.io.File

class GetConfigFile {

  val configFilePath = new GetConfigFilePath()

  def getDbConfigFile() = {
    configFilePath.getMainPath() + File.separator + "db-config.xml"
    //    "classpath*:db-config.xml"
  }

  def getFTPConfigFile() = {
    configFilePath.getMainPath() + File.separator + "ftp-config.xml"
    //    "classpath*:ftp-config.xml"
  }

  def getHDFSConfigFile() = {
    configFilePath.getMainPath() + File.separator + "hadoop-config.xml"
    //    "classpath*:hadoop-config.xml"
  }

}
