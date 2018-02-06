package com.bjjh.MessMan.model

import java.io.File

import com.bjjh.MessMan.util.{DownloadDataTransferListener, UploadDataTransferListener}
import it.sauronsoftware.ftp4j.{FTPClient, FTPFile}

/**
  * 自助封装了FTP工具的一部分方法。
  */
class Ftp4jFtpClient {

  /**
    * 上传文件
    *
    * @param sourceFilePath 上传文件位置
    * @param FileName       上传文件名
    */
  def uploadFile(client: FTPClient, sourceFilePath: String, FileName: String): Boolean = {
    client.upload(new File(sourceFilePath + FileName), new UploadDataTransferListener())
    true
  }

  /**
    * 下载文件
    *
    * @param sourceFilePath 下载文件的路径
    * @param targetFilePath 下载文件到何路径
    * @param FileName       下载的文件名
    */
  def downloadFile(client: FTPClient, sourceFilePath: String, targetFilePath: String, FileName: String): Boolean = {
    client.download(sourceFilePath + "/" + FileName, new File(targetFilePath + File.separator + FileName), new DownloadDataTransferListener)
    true
  }

}
