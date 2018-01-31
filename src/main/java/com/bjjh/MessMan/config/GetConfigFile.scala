package com.bjjh.MessMan.config

class GetConfigFile {

  val configFilePath = new GetConfigFilePath()

  def getDbConfigFile() = {
    configFilePath.getConfigPath() + "/db-config.xml"
  }

  def getFTPConfigFile() = {
    configFilePath.getConfigPath() + "/ftp-config.xml"
  }

}
