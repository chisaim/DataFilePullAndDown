package com.bjjh.MessMan.config

import java.util.Properties

import org.dom4j.io.SAXReader
import org.dom4j.{Document, Element}

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

  def getMaster(): String = {
    getElementTextValue(getConfigFileDocument(configFile.getOperationFile()), "master")
  }

  def getAppName(): String = {
    getElementTextValue(getConfigFileDocument(configFile.getOperationFile()), "appName")
  }

  def getTabName1():String ={
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "StrategyTab")
  }

  def getTabName2():String ={
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "MessageTab")
  }

  def getTab1col():String = {
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "StrategyTab-col")
  }
  def getTab2col():String = {
    getElementTextValue(getConfigFileDocument(configFile.getDbConfigFile()), "MessageTab-col")
  }

  def getProp(): Properties = {
    val prop = new Properties()
    prop.setProperty("user",getDbUserName())
    prop.setProperty("password",getDbPassword())
    prop
  }

}
