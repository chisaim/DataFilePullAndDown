package com.bjjh.MessMan.model

import java.io.File

import com.bjjh.MessMan.config.GetConfigMess
import com.bjjh.MessMan.util.DefaultDataTransferListener
import it.sauronsoftware.ftp4j.FTPClient

/**
  * 主要创建和设定ftp客户端配置相关信息，并附加上传和下载方法。
  */
class Ftp4jFtpClient {

  val client = new FTPClient

  val configMess = new GetConfigMess

  client.connect(configMess.getFTPServer(),configMess.getFTPPort())

  client.login(configMess.getFTPUser(),configMess.getFTPPasswd())

  client.setType(FTPClient.SECURITY_FTPS)

  println(client.currentDirectory())

  client.changeDirectory(configMess.getDataFileSourceLocation())

  println(client.currentDirectory())

  def unFile() : Unit = {
    client.upload(new File("D:\\Download\\data"),new DefaultDataTransferListener())

  }

}
