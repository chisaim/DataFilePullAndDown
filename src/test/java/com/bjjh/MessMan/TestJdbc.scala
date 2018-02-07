package com.bjjh.MessMan

import java.text.SimpleDateFormat

import com.bjjh.MessMan.model.TaskMess
import com.bjjh.MessMan.util.JdbcUtil
import java.util.Date

object TestJdbc {

  val jdbcutil = new JdbcUtil

  val taskMess = new TaskMess

  val taskMessLog = new TaskMess

  def main(args: Array[String]): Unit = {

    val sdf = new SimpleDateFormat("yyyyMMddHHmm").format(new Date)

    println(sdf)
  }
}
