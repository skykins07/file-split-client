package com.filesplit.clientapplication.components;

import com.filesplit.clientapplication.model.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;

public class DownloadFile extends JFrame
{
    JLabel l1;
    JButton b1;
    JComboBox c1;
    String user;

    public DownloadFile(String usr){
        user=usr;
        setTitle("Download File");
        getContentPane().setBackground(Color.white);
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

        l1 = new JLabel("File Name");
        getContentPane().add(l1);

        c1 = new JComboBox();
        getContentPane().add(c1);

        b1 = new JButton("Download");
        getContentPane().add(b1);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    String file = c1.getSelectedItem().toString();
                    File del_file = new File("Downloads/".concat(file));
                    if(del_file.exists())
                        del_file.delete();

                    Response respObj;
                    RestTemplate template=new RestTemplate();

                    Map<String,String> headers=new HashMap<>();
                    headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    BlockDetail inputBD = new BlockDetail(user,file,null,null,null);
                    GetBlockRequest getBlockRequest=new GetBlockRequest();
                    getBlockRequest.setBlockDetail(inputBD);

                    // Invoke multiple clients running on different ports
                    String client1BaseUrl="http://localhost:1111/cloudserver1";
                    respObj= template.postForEntity(client1BaseUrl.concat("/getBlockContent"),
                            getBlockRequest,Response.class,headers).getBody();
                    List<BlockDetail> list=new ArrayList<>();
                    if(Objects.nonNull(respObj) &&!CollectionUtils.isEmpty(respObj.getBlockDetailList())){
                        list.addAll(respObj.getBlockDetailList());
                    }

//                    String client2BaseUrl="http://localhost:2222/cloudserver2";
//                    respObj= template.postForEntity(client2BaseUrl.concat("/getBlockContent"),
//                            getBlockRequest,Response.class,headers).getBody();
//                    if(Objects.nonNull(respObj) &&!CollectionUtils.isEmpty(respObj.getBlockDetailList())){
//                        list.addAll(respObj.getBlockDetailList());
//                    }

//                    String client3BaseUrl="http://localhost:3333/cloudserver3";
//                    respObj= template.postForEntity(client3BaseUrl.concat("/getBlockContent"),
//                            getBlockRequest,Response.class,headers).getBody();
//                    if(Objects.nonNull(respObj) &&!CollectionUtils.isEmpty(respObj.getBlockDetailList())){
//                        list.addAll(respObj.getBlockDetailList());
//                    }


                    Collections.sort(list);
                    ResponseEntity<Response> downLoadRespObj;
                    if (!del_file.exists()) {
                        del_file.getParentFile().mkdirs();
                        del_file.createNewFile();
                    }

                    FileOutputStream fout=new FileOutputStream(del_file,true);
                    for (BlockDetail bdObj : list) {
//                        client1BaseUrl=client1BaseUrl.replace("1111",String.valueOf(bdObj.getPortNumber()));
                        BlockDetail downloadBlockObj=new BlockDetail(bdObj.getUserName(),bdObj.getFileName(),
                                bdObj.getBlockNumber(),null,null);
                        downLoadRespObj = template.postForEntity(client1BaseUrl.concat("/download"), downloadBlockObj,
                                Response.class, headers);
                        if(Objects.nonNull(downLoadRespObj.getBody()) && downLoadRespObj.getBody().getDownloadBlockContent().length > 0){
                            byte[] data= downLoadRespObj.getBody().getDownloadBlockContent();
                            fout.write(data,0,data.length);
                        }
                    }
                    fout.close();
                    JOptionPane.showMessageDialog(DownloadFile.this,"File downloaded at file-split-client/Downloads directory");
                    setVisible(false);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void getFileNameFromServer(){
        try{
            c1.removeAllItems();
            Response respObj;
            RestTemplate template=new RestTemplate();

            String client1BaseUrl="http://localhost:1111/cloudserver1/retrieveFileName?userName=".concat(user);
            respObj= template.getForObject(client1BaseUrl,Response.class);
            Set<String> displayFile = new HashSet<>(respObj.getFileList());

//            String client2BaseUrl="http://localhost:2222/cloudserver2/retrieveFileName?userName=".concat(user);
//            respObj= template.getForObject(client2BaseUrl,Response.class);
//            Set<String> displayFile = new HashSet<>(respObj.getFileList());
//
//            String client3BaseUrl="http://localhost:3333/cloudserver3/retrieveFileName?userName=".concat(user);
//            respObj= template.getForObject(client3BaseUrl,Response.class);
//            Set<String> displayFile = new HashSet<>(respObj.getFileList());

            // Populate the display list from all the 3 servers
            if(!CollectionUtils.isEmpty(displayFile)){
                for (String s : displayFile) {
                    c1.addItem(s);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
