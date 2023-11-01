package com.filesplit.clientapplication.components;

import com.filesplit.clientapplication.model.BlockDetail;
import com.filesplit.clientapplication.model.Response;
import org.jfree.ui.RefineryUtilities;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ClientScreen extends JFrame {

    JPanel p1,p2;
    JLabel l1,l2,l3;
    JTextField tf1,tf2;
    JButton b1,b2,b3,b4,b5,b6;
    Font f1,f2;
    UserScreen user;
    JFileChooser chooser;
    String username;
    File file;
    RandomAccessFile raf;
    int tot_blocks;
    Object[][] blocks;
    long size;
    static long propose,existing;
    public long calculateBlock(){
        long length = file.length();
        tot_blocks=0;
        long size = 0;
        if(length >= 1000){
            size = length/10;
            tot_blocks = 10;
        }
        if(length < 1000 && length > 500){
            size = length/5;
            tot_blocks = 5;
        }
        if(length < 500 && length > 1){
            size = length/3;
            tot_blocks = 3;
        }
        return size;
    }
    public Object[][] getBlocks(){
        Object row[][]=new Object[tot_blocks][2];
        try{
            raf = new RandomAccessFile(file,"r");
            for(int i=0;i<tot_blocks;i++){
                byte b[]=new byte[(int)size];
                raf.read(b);
                raf.seek(raf.getFilePointer());
                row[i][0]=i+"";
                row[i][1]=b;
            }
            raf.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return row;
    }
    public void send(){
        try{
            int port = 1111;
            RestTemplate template=new RestTemplate();
            Map<String,String> headers=new HashMap<>();
            headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            String client1BaseUrl="http://localhost:1111/cloudserver1";
            String client2BaseUrl="http://localhost:2222/cloudserver2";
            String client3BaseUrl="http://localhost:3333/cloudserver3";
            String serverUrl=client1BaseUrl;
            long start = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder();
            for (Object[] block : blocks) {
                int b_num=Integer.parseInt(block[0].toString());
                byte[] b_content = (byte[]) block[1];
                BlockDetail inputBD = new BlockDetail(username, file.getName(), b_num, b_content,null);
                ResponseEntity<Response> respObj = template.postForEntity(serverUrl.concat("/storeBlockContent"),
                        inputBD, Response.class, headers);
                String response = Objects.requireNonNull(respObj.getBody()).getReturnMessage();
                sb.append(response).append("\n");
//                if (serverUrl.contains("1111"))
//                    serverUrl = client2BaseUrl;
//                else if (serverUrl.contains("2222"))
//                    serverUrl = client3BaseUrl;
//                else
//                    serverUrl = client1BaseUrl;
            }
            long end = System.currentTimeMillis();
            propose = end - start;
            existing = propose * 3;
            JOptionPane.showMessageDialog(ClientScreen.this,sb.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ClientScreen(UserScreen usr,String uname){
        user = usr;
        username = uname;
        setTitle("Cloud User Screen");
        f1 = new Font("Courier New",Font.BOLD+Font.ITALIC,18);
        p1 = new JPanel();
        l1 = new JLabel("<HTML><BODY><CENTER>User Screen</CENTER></BODY></HTML>".toUpperCase());
        l1.setFont(this.f1);
        l1.setForeground(new Color(125,254,120));
        p1.add(l1);
        p1.setBackground(new Color(100,30,40));

        f2 = new Font("Courier New",Font.BOLD,14);

        p2 = new JPanel();
        p2.setLayout(null);
        b1 = new JButton("Upload File");
        b1.setFont(f2);
        b1.setBounds(200,50,400,30);
        p2.add(b1);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                int option = chooser.showOpenDialog(ClientScreen.this);
                if(option == chooser.APPROVE_OPTION){
                    file = chooser.getSelectedFile();
                    JOptionPane.showMessageDialog(ClientScreen.this,"Choosen file : "+file.getName());
                }
            }
        });

        b2 = new JButton("Generate Fragments");
        b2.setFont(f2);
        b2.setBounds(200,100,400,30);
        p2.add(b2);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                size = calculateBlock();
                blocks = getBlocks();
                JOptionPane.showMessageDialog(ClientScreen.this,"Fragments generated successfully ".concat(String.valueOf(tot_blocks)));
            }
        });

        b3 = new JButton("Replicate Fragments");
        b3.setFont(f2);
        b3.setBounds(200,150,400,30);
        p2.add(b3);
        b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                send();
            }
        });

        b4 = new JButton("Download Fragments");
        b4.setFont(f2);
        b4.setBounds(200,200,400,30);
        p2.add(b4);
        b4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                DownloadFile df = new DownloadFile(username);
                df.setVisible(true);
                df.setLocationRelativeTo(null);
                df.setSize(300,200);
                df.getFileNameFromServer();
            }
        });

        b5 = new JButton("RC versus File Fragments Graph");
        b5.setFont(f2);
        b5.setBounds(200,250,400,30);
        p2.add(b5);
        b5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Chart chart1 = new Chart("RC versus File Fragments Graph");
                chart1.pack();
                RefineryUtilities.centerFrameOnScreen(chart1);
                chart1.setVisible(true);
            }
        });

        b6 = new JButton("Logout");
        b6.setFont(f2);
        b6.setBounds(200,300,400,30);
        p2.add(b6);
        b6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                setVisible(false);
                user.setVisible(true);
            }
        });

        chooser = new JFileChooser(new File("."));

        getContentPane().add(p1, "North");
        getContentPane().add(p2, "Center");
    }
}
