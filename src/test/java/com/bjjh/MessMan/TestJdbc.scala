package com.bjjh.MessMan

import com.bjjh.MessMan.model.TaskMess
import com.bjjh.MessMan.util.JdbcUtil

object TestJdbc {

  val jdbcutil = new JdbcUtil

  val taskMess = new TaskMess

  def main(args: Array[String]): Unit = {

    jdbcutil.insert()

    jdbcutil.AutoCommitTransaction(true)
  }
}
