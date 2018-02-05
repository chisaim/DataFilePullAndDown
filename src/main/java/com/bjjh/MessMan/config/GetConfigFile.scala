package com.bjjh.MessMan.config

class GetConfigFile {

  val configFilePath = new GetConfigFilePath()

  def getDbConfigFile() = {
    configFilePath.getMainPath() + "/db-config.xml"
//    "classpath*:db-config.xml"
  }

  def getFTPConfigFile() = {
    configFilePath.getMainPath() + "/ftp-config.xml"
//    "classpath*:ftp-config.xml"
  }

  def getHDFSConfigFile() = {
    configFilePath.getMainPath() + "/hadoop-config.xml"
//    "classpath*:hadoop-config.xml"
  }

}
