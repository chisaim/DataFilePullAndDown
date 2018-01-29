package com.bjjh.MessMan.config

class GetConfigFilePath {

  def getMainPath() = {

    System.getProperty("user.dir")
  }

  def getConfigPath() = {

    getMainPath() + "/config"
  }

  def getLogPath() = {
    getMainPath() + "/log"
  }
  /*
    def getLibPath() = {
      getMainPath + "\\lib"
    }

    def getBinPath() = {
      getMainPath() + "\\sbin"
    }
  */
}
