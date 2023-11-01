package com.filesplit.clientapplication.model;

public class DownloadFileResponse {

    private byte[] blockContent;

    public byte[] getBlockContent() {
        return blockContent;
    }

    public void setBlockContent(byte[] blockContent) {
        this.blockContent = blockContent;
    }
}
