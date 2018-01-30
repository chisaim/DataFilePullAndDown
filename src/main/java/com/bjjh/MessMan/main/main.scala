package com.bjjh.MessMan.main

import it.sauronsoftware.ftp4j.FTPClient

object main {

  def main(args: Array[String]): Unit = {

    val client = new FTPClient

    client.connect("192.168.102.117", 21)

    client.login("userftp", "userftp")

    println(client.currentDirectory())
  }

}
