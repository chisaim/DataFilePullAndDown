package com.bjjh.MessMan;

import com.bjjh.MessMan.config.GetConfigMess;
import com.bjjh.MessMan.util.DownloadDataTransferListener;
import it.sauronsoftware.ftp4j.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestFtp {
    @Test
    public void testFtpList() {

        FTPClient client = new FTPClient();

        GetConfigMess configMess = new GetConfigMess();

        try {
            client.setType(FTPClient.TYPE_BINARY);
            client.connect(configMess.getFTPServer(), configMess.getFTPPort());
            client.login(configMess.getFTPUser(), configMess.getFTPPasswd());
            client.changeDirectory(configMess.getFTPFileSourceLocation());
            FTPFile[] list = client.list();
            for (FTPFile file : list) {
                System.out.println(file.getName());
                File Tpath = new File(configMess.getFTPFileTargetLocation() + File.separator + configMess.getToday());
                String Tfilename = Tpath + File.separator + file.getName() + ".data";
                if (Tpath.exists()) {
                    client.download(file.getName(), new File(Tfilename), new DownloadDataTransferListener());
//                    logger.info("The file ==>" + file.getName() + " has been successfully downloaded.");
                    client.deleteFile(file.getName());
//                    logger.info("The file ==>" + file.getName() + " has been successfully delete.");
                } else {
                    Tpath.mkdirs();
//                    logger.info("Successfully create a date directory " + Tpath);
                    client.download(file.getName(), new File(Tfilename), new DownloadDataTransferListener());
//                    logger.info("The file ==>" + file.getName() + " has been successfully downloaded.");
                    client.deleteFile(file.getName());
//                    logger.info("The file ==>" + file.getName() + " has been successfully delete.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
        } catch (FTPListParseException e) {
            e.printStackTrace();
        } catch (FTPAbortedException e) {
            e.printStackTrace();
        }

    }
}
