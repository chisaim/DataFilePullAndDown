package com.bjjh.MessMan.model

import java.io.File

import com.bjjh.MessMan.util.{DownloadDataTransferListener, UploadDataTransferListener}
import it.sauronsoftware.ftp4j.{FTPClient, FTPFile}

/**
  * 自助封装了FTP工具的一部分方法。
  */
class Ftp4jFtpClient {
  /**
    * 创建FTP客户端实例
    *
    * @return FTPClient
    */
  def getClientInstance(): FTPClient = {
    new FTPClient
  }

  /**
    * 获取FTP链接
    *
    * @param FtpServer 链接服务地址
    * @param FTPPort   链接服务地址的端口号
    */
  def ftpConnect(FtpServer: String, FTPPort: Int): Unit = {
    getClientInstance().connect(FtpServer, FTPPort)
  }

  /**
    * 登录FTP服务器
    *
    * @param FtpUser   登录FTP服务器用户名
    * @param FtpPasswd 登录FTP服务器密码
    */
  def ftpLogin(FtpUser: String, FtpPasswd: String): Unit = {
    getClientInstance().login(FtpUser, FtpPasswd)
  }

  /**
    * 获取在FTP服务器登录后的当前位置
    *
    * @return
    */
  def getCurrentDirectory(): String = {
    getClientInstance().currentDirectory()
  }

  /**
    * 更改当前地址或访问其他地址
    *
    * @param path 输入地址
    */
  def ChangeDirectory(path: String): Unit = {
    getClientInstance().changeDirectory(path)
  }

  /**
    * 判断当前访问地址下是否为空
    * @param path 输入地址
    * @return
    */
  def setPathGetListIsEmpty(path: String): Boolean = {
    ChangeDirectory(path)
    getClientInstance().list().isEmpty
  }

  /**
    * 获取当前访问地址下的目录文档和链接
    *
    * @param path 输入地址
    * @return
    */
  def setPathGetFileList(path: String): Array[FTPFile] = {
    ChangeDirectory(path)
    getClientInstance().list()
  }

  /**
    * 上传文件
    * @param sourceFilePath 上传文件位置
    * @param FileName 上传文件名
    */
  def uploadFile(sourceFilePath: String, FileName: String): Unit = {
    getClientInstance().upload(new File(sourceFilePath + FileName), new UploadDataTransferListener())
  }

  /**
    * 下载文件
    * @param sourceFilePath 下载文件的路径
    * @param targetFilePath 下载文件到何路径
    * @param FileName 下载的文件名
    */
  def downloadFile(sourceFilePath: String, targetFilePath: String, FileName: String): Unit = {
    getClientInstance().download(sourceFilePath+FileName, new File(targetFilePath + FileName), new DownloadDataTransferListener)
  }

}
