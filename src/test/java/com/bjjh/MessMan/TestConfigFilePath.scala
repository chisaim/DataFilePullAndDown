package com.bjjh.MessMan

import java.io.File

import com.bjjh.MessMan.config.GetConfigMess
import com.bjjh.MessMan.model.Ftp4jFtpClient
import com.bjjh.MessMan.util.{DownloadDataTransferListener, UploadDataTransferListener}
import it.sauronsoftware.ftp4j.FTPClient

object TestConfigFilePath {

  def main(args: Array[String]): Unit = {

    val client = new FTPClient

    val configMess = new GetConfigMess

    val ftpClient = new Ftp4jFtpClient

    client.connect(configMess.getFTPServer(),configMess.getFTPPort())

    client.login(configMess.getFTPUser(),configMess.getFTPPasswd())

    client.setType(FTPClient.SECURITY_FTPS)

    for(list <- client.list(configMess.getFTPFileSourceLocation())){
      list.getType
    }

    client.changeDirectory(configMess.getFTPFileSourceLocation())

    println(client.currentDirectory())

    ftpClient.downloadFile(client,"123.txt",configMess.getFTPFileTargetLocation(),"123.txt")

//    client.upload(new File("D:\\Download\\data"),new UploadDataTransferListener)

//    client.download("/home/userftp/data/123.txt",new File("D:\\Tools\\123.txt"),new DownloadDataTransferListener)

  }

}
