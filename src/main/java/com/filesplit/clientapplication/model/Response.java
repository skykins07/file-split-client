package com.filesplit.clientapplication.model;

import java.util.List;

public class Response {
    private String returnMessage;
    private List<String> fileList;
    private List<BlockDetail> blockDetailList;
    private byte[] downloadBlockContent;

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public List<BlockDetail> getBlockDetailList() {
        return blockDetailList;
    }

    public void setBlockDetailList(List<BlockDetail> blockDetailList) {
        this.blockDetailList = blockDetailList;
    }

    public byte[] getDownloadBlockContent() {
        return downloadBlockContent;
    }

    public void setDownloadBlockContent(byte[] downloadBlockContent) {
        this.downloadBlockContent = downloadBlockContent;
    }
}
