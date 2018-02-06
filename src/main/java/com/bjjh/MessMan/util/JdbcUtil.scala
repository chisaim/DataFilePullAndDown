package com.bjjh.MessMan.util

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}

import com.bjjh.MessMan.config.GetConfigMess
import com.bjjh.MessMan.model.TaskMess

/**
  * 这个类的目的是做传输前检查，主要检查一下几项：
  * 1.传输路径检查，需要修改到配置文件中写的路径，并切换路径。
  * 2.检查上述其路径下有无数据文件，有数据文件就下载下来，没有就返回日志。
  * 3.数据文件下载下来就记录数据库日志。
  */
class JdbcUtil {

  val configMess = new GetConfigMess

  val taskMess = new TaskMess

  Class.forName(configMess.getDriverClassName()).newInstance()

  def getConnection() : Connection = {
    DriverManager.getConnection(configMess.getDBUrl(),configMess.getDbUserName(),configMess.getDbPassword())
  }

  def insert() : Unit = {
    val exceSql = "insert into site.taskMessTable (taskID,keywordNum,messageNum,startTime,endTime,elapsedTime) values (?,?,?,?,?,?)"
    val ptst = getConnection().prepareStatement(exceSql)
    ptst.setNString(1,taskMess.getTaskTimeAndDate)
    ptst.setNString(2,"1")
    ptst.setNString(3,"1")
    ptst.setNString(4,"1")
    ptst.setNString(5,"1")
    ptst.setNString(6,"1")
    ptst.executeUpdate()
    ptst.close()
    getConnection().close()

  }

  def AutoCommitTransaction(AutoCommitFlag:Boolean):Unit = {
    getConnection().setAutoCommit(true)
  }
}
