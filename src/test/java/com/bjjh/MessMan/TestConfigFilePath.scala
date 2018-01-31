package com.bjjh.MessMan

import java.io.File

import com.bjjh.MessMan.config.{GetConfigFile, GetConfigFilePath, GetConfigMess}
import com.bjjh.MessMan.util.DefaultDataTransferListener
import it.sauronsoftware.ftp4j.FTPClient

object TestConfigFilePath {

  def main(args: Array[String]): Unit = {

    val client = new FTPClient

    val configMess = new GetConfigMess

    client.connect(configMess.getFTPServer(),configMess.getFTPPort())

    client.login(configMess.getFTPUser(),configMess.getFTPPasswd())

    client.setType(FTPClient.SECURITY_FTPS)

    println(client.currentDirectory())

    client.changeDirectory(configMess.getDataFileSourceLocation())

    println(client.currentDirectory())

    client.upload(new File("D:\\Download\\data"),new DefaultDataTransferListener)

  }

}
