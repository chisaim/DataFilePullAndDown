package com.bjjh.MessMan.model

import it.sauronsoftware.ftp4j.FTPClient

class Ftp4jFtpClient {

  val client = new FTPClient

  client.setCharset("utf-8")

  client.setType(FTPClient.TYPE_BINARY)
}
