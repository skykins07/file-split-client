package com.filesplit.clientapplication.model;

import com.filesplit.clientapplication.components.Blocks;

import java.util.Comparator;
import java.util.function.Function;

public class BlockDetail implements Comparable<BlockDetail> {

    private String userName;
    private String fileName;
    private Integer blockNumber;
    private byte[] blockContent;
    private Integer portNumber;

    public BlockDetail() {
        super();
    }

    public BlockDetail(String userName, String fileName, Integer blockNumber, byte[] blockContent, Integer portNumber) {
        this.userName = userName;
        this.fileName = fileName;
        this.blockNumber = blockNumber;
        this.blockContent = blockContent;
        this.portNumber = portNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public byte[] getBlockContent() {
        return blockContent;
    }

    public void setBlockContent(byte[] blockContent) {
        this.blockContent = blockContent;
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public int compareTo(BlockDetail outerObj) {
        int b1 = this.blockNumber;
        int b2 = outerObj.blockNumber;
        return Integer.compare(b1, b2);
    }

}

