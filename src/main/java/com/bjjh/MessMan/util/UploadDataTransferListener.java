package com.bjjh.MessMan.util;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import org.apache.log4j.Logger;

public class UploadDataTransferListener implements FTPDataTransferListener {

    private Logger logger = Logger.getLogger(UploadDataTransferListener.class);

    public void started() {
        logger.info("start data file upload transfer");
    }

    public void transferred(int i) {
        logger.info("starting upload transfer " + i + " bytes");
    }

    public void completed() {
        logger.info("upload transfer complete");
    }

    public void aborted() {
        logger.info("upload transfer abortes");
    }

    public void failed() {
        logger.info("upload transfer failed");
    }

}
