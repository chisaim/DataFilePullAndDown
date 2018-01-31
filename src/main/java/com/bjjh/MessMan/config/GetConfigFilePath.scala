package com.bjjh.MessMan.config

class GetConfigFilePath {

  def getMainPath() = {

    System.getProperty("user.dir") + "/src/main/resources"
  }

  def getConfigPath() = {

    getMainPath() + "/config"
  }
}
