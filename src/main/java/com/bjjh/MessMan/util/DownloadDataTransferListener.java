package com.bjjh.MessMan.util;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import org.apache.log4j.Logger;

public class DownloadDataTransferListener implements FTPDataTransferListener {

    private Logger logger = Logger.getLogger(DownloadDataTransferListener.class);

    public void started() {
        logger.info("start data file download transfer");
    }

    public void transferred(int i) {
        logger.info("starting download transfer " + i + " bytes");
    }

    public void completed() {
        logger.info("download transfer complete");
    }

    public void aborted() {
        logger.info("download transfer abortes");
    }

    public void failed() {
        logger.info("download transfer failed");
    }
}
