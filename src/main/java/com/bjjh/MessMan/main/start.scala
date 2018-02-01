package com.bjjh.MessMan.main

import it.sauronsoftware.ftp4j.{FTPClient, FTPFile}
import org.apache.log4j.Logger

object start {

  val logger = Logger.getLogger(start.getClass.getName)

  def main(args: Array[String]): Unit = {

    val client = new FTPClient

    client.connect("192.168.102.117", 21)

    client.login("userftp", "userftp")

    println(client.currentDirectory())

    val list = client.list()
  }



}
