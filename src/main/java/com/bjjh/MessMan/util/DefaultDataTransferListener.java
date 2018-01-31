package com.bjjh.MessMan.util;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import org.apache.log4j.Logger;

public class DefaultDataTransferListener implements FTPDataTransferListener {

    private Logger logger = Logger.getLogger(DefaultDataTransferListener.class);

    public void started() {
        System.out.println("start data file transfer");
        logger.info("start data file transfer");
    }

    public void transferred(int i) {
        System.out.println("starting transfer " + i + " bytes");
        logger.info("starting transfer " + i + " bytes");
    }

    public void completed() {
        System.out.println("transfer complete");
        logger.info("transfer complete");
    }

    public void aborted() {
        System.out.println("transfer abortes");
        logger.info("transfer abortes");
    }

    public void failed() {
        System.out.println("transfer failed");
        logger.info("transfer failed");
    }

}
