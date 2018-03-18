package com.bjjh.MessMan.main
import java.util.concurrent.{Executors, TimeUnit}

import com.bjjh.MessMan.config.GetConfigMess
import com.bjjh.MessMan.model.{MainTimerTask, TaskMess}
import com.bjjh.MessMan.util.{JdbcUtil, PrimaryGenerater}
import it.sauronsoftware.ftp4j.{FTPClient}
import org.apache.log4j.Logger

object start {

  val logger = Logger.getLogger(start.getClass.getName)

  val configMess = new GetConfigMess

  val client = new FTPClient

  val util = new JdbcUtil

  val taskMessLog = new TaskMess

  val pg: PrimaryGenerater = PrimaryGenerater.getInstance

  val threadPool = Executors.newScheduledThreadPool(5)

  def main(args: Array[String]): Unit = {

    /**
      * 开始第一阶段执行，内容是到FTP服务器的固定路径下载数据文件放入固定位置然后再上传到hadoop的HDFS上面。
      */
    logger.info("====================mession start ...====================")

    val mainTask = new MainTimerTask

    //依靠配置时间来控制循环频率
    threadPool.scheduleAtFixedRate(mainTask, 0, configMess.getScanTime(), TimeUnit.MILLISECONDS)
    logger.info("====================Phase cycle start.====================")
  }

}

