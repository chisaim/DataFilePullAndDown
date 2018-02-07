package com.bjjh.MessMan.config

import java.io.File

import org.dom4j.io.SAXReader
import org.dom4j.{Document, Element}
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.Date

class GetConfigMess {

  val configFile = new GetConfigFile()

  def getConfigFileDocument(filePath: String): Document = {
    val reader = new SAXReader()
    reader.read(filePath)
  }

  def getRootElement(doc: Document): Element = {
    doc.getRootElement
  }

  def getTagNameInDoc(document: Document, tagName: String): Element = {
    getRootElement(document).element(tagName)
  }

  def getElementTextValue(document: Document, tagName: String): String = {
    getRootElement(document).elementText(tagName)
  }

  /*
  This place writes the key-value of the config configuration

    def getAttributeValueInTag(document: Document,tagName:String) :String = {
      getTagNameInDoc(document,"config").attribute("key1").getValue
    }
  */

  def getDBUrl(): String = {
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "dbUrl")
  }

  def getDriverClassName(): String = {
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "driverClassName")
  }

  def getDbUserName(): String = {
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "dbUserName")
  }

  def getDbPassword(): String = {
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "dbPassword")
  }

  def getDataFileOutputPath() : String = {
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "dataFileOutputPath")
  }

  def getFTPServer():String = {
    getElementTextValue(getConfigFileDocument(configFile.getFTPConfigFile()),"ftp-server")
  }

  def getFTPPort():Int = {
    Integer.parseInt(getElementTextValue(getConfigFileDocument(configFile.getFTPConfigFile()),"ftp-port"))
  }

  def getFTPUser():String = {
    getElementTextValue(getConfigFileDocument(configFile.getFTPConfigFile()),"ftp-user")
  }

  def getFTPPasswd():String = {
    getElementTextValue(getConfigFileDocument(configFile.getFTPConfigFile()),"ftp-passwd")
  }

  def getFTPFileSourceLocation():String = {
    getElementTextValue(getConfigFileDocument(configFile.getFTPConfigFile()),"data-file-source-location")
  }

  def getFTPFileTargetLocation():String = {
    getElementTextValue(getConfigFileDocument(configFile.getFTPConfigFile()),"data-file-target-location")
  }

  def getScanTime():Long = {
    Long.parseLong(getElementTextValue(getConfigFileDocument(configFile.getFTPConfigFile()),"scan-time"))
  }

  def getHDFSURI():String = {
    getElementTextValue(getConfigFileDocument(configFile.getHDFSConfigFile()),"uri")
  }

  def getHdfsUserName():String = {
    getElementTextValue(getConfigFileDocument(configFile.getHDFSConfigFile()),"hdfsUserName")
  }

  def uploadFileFromHDFS():String = {
    getElementTextValue(getConfigFileDocument(configFile.getHDFSConfigFile()),"data-file-source-location")
  }

  def downloadFileFromHDFS():String = {
    getElementTextValue(getConfigFileDocument(configFile.getHDFSConfigFile()),"data-file-target-location")
  }

  def getTimestamp():String = {
    File.separator + new SimpleDateFormat("yyyyMMddHHmm").format(new Date)
  }



}
