package com.bjjh.MessMan.util

import java.io.File
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

  Class.forName(configMess.getDriverClassName()).newInstance()

  def getConnection(): Connection = {
    DriverManager.getConnection(configMess.getDBUrl(), configMess.getDbUserName(), configMess.getDbPassword())
  }

  def insert(taskMess: TaskMess): Unit = {
    val exceSql = "insert into test.tranmessioninfo (filename,filesize,fileModifiedDate,exceTaskTimeAndDate,upOrDownloadFlag) values (?,?,?,?,?)"
    val ptst = getConnection().prepareStatement(exceSql)
    ptst.setNString(1, taskMess.getFilename)
    ptst.setNString(2, taskMess.getFileSize)
    ptst.setNString(3, taskMess.getModifyDate)
    ptst.setNString(4, taskMess.getTaskTimeAndDate)
    ptst.setNString(5, taskMess.getUpOrDownloadFlag)
    ptst.executeUpdate()
    ptst.close()
    getConnection().close()

  }

  def output(path: String, filename: String): Unit = {
    val sql = "SELECT bwnumber,expirytime,bwclass,optype INTO OUTFILE '" + path + "/" + filename + "' fields TERMINATED BY '\t' OPTIONALLY ENCLOSED BY '' lines TERMINATED BY '\n' FROM test.policy_bwlist"
    val ptst = getConnection().prepareStatement(sql)
    ptst.execute()
    ptst.close()
    getConnection().close()
  }

  def count(): Long = {
    val sql = "SELECT COUNT(1) FROM test.policy_bwlist"
    val ptst = getConnection().prepareStatement(sql)
    val set = ptst.executeQuery()
    var count = 0
    while (set.next()) {
      count = set.getInt(1)
    }
    count
  }

  def loadDataFile(path: String, filename: String): Unit = {
    val sql = "LOAD DATA INFILE '" + path + "/" + filename + "' INTO TABLE site.taskMessTable fields TERMINATED BY '\t' OPTIONALLY ENCLOSED BY '' LINES TERMINATED BY '\n'"
    val ptst = getConnection().prepareStatement(sql)
    ptst.execute()
    ptst.close()
    getConnection().close()
  }

  def updateCol(): Unit = {
    val sql = "UPDATE site.employee AS T,(SELECT dept_id FROM site.department) AS S SET T.email = '944125621@qq.com' WHERE S.dept_id = T.dept_id AND T.employee_id = '26'"
    val ptst = getConnection().prepareStatement(sql)
    ptst.executeUpdate()
    ptst.close()
    getConnection().close()
  }

  def AutoCommitTransaction(AutoCommitFlag: Boolean): Unit = {
    getConnection().setAutoCommit(true)
  }
}
