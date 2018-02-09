package com.bjjh.MessMan

import java.text.SimpleDateFormat

import com.bjjh.MessMan.model.TaskMess
import com.bjjh.MessMan.util.JdbcUtil
import java.util.Date

import com.bjjh.MessMan.main.start.configMess

object TestJdbc {

  val jdbcutil = new JdbcUtil

  val taskMess = new TaskMess

  val taskMessLog = new TaskMess

  val filename = (configMess.getTimestamp() + "_file.txt")
    .substring(1, (configMess.getTimestamp() + "_file.txt").size)

  def main(args: Array[String]): Unit = {

//    val sdf = new SimpleDateFormat("yyyyMMddHHmm").format(new Date)

//    jdbcutil.output(configMess.getDataFileOutputPath(),filename)
    jdbcutil.loadDataFile(configMess.getDataFileOutputPath(),filename)
  }
}
