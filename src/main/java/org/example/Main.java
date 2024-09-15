package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;

public class Main extends Frame implements ActionListener{
    Button bex=new Button("Exit");
    Button sea=new Button("Search");
    Button buttonOpen = new Button("Open");
    TextArea txa = new TextArea();
    String fileToOpen = "src/result.html";
    String pathToOpen= "E:/d disk/учёба/4 курс/АиПРП/lab1/";
    String path= "E:/d disk/учёба/4 курс/АиПРП/lab1/src/source_txt";
    public  Main()
    {
        super("my window");
        setLayout(null);
        setBackground(new Color(150,200,100));
        setSize(450,250);
        add(bex);
        add(sea);
        add(txa);
        add(buttonOpen);
        bex.setBounds(110,190,100,20);
        bex.addActionListener(this);
        sea.setBounds(110,165,100,20);
        sea.addActionListener(this);
        buttonOpen.setBounds(220,165,100,20);
        buttonOpen.addActionListener(this);
        txa.setBounds(20,50,300,100);

        this.show();
        this.setLocationRelativeTo(null);

    }

    public void actionPerformed(ActionEvent ae)
    {
        Map<String, Integer> fileNames = new HashMap<>();
        if(ae.getSource()==bex)
            System.exit(0);
        else
        if (ae.getSource()==sea)
        {
            String [] keywords=txa.getText().split(",");
            for (int j=0;j<keywords.length;j++)
            {
                System.out.println(keywords[j]);

            }
            File f = new File(path);
            ArrayList<File> files =
                    new ArrayList<File>(Arrays.asList(f.listFiles()));
            txa.setText("");
            int max = 0;
            for (File elem : files)
            {
                int zcoincidence = test_url(elem,keywords);
                if (zcoincidence > max){
                    max = zcoincidence;
                    f=elem;
                }
                txa.append("\n"+elem+"  :"+zcoincidence);
            }
            if (max==0){
                fileToOpen = "";
            }else{
                fileToOpen = "src/result.html";
                test_url(f,keywords);
            }
        }
        else
            if (ae.getSource()==buttonOpen){
                try {
                    if (fileToOpen.isEmpty()){
                        throw new Exception("Nothing to open");
                    }
                    File txtFile = new File(pathToOpen+fileToOpen);

                    // Check if Desktop is supported on the current platform
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();

                        // Open the .txt file
                        if (txtFile.exists()) {
                            desktop.browse(txtFile.toURI());
                        } else {
                            System.out.println(fileToOpen);
                            System.out.println("File does not exist");
                        }
                    } else {
                        System.out.println("Desktop is not supported on your platform.");
                    }

                } catch (Exception e) {
                    System.out.println("error "+e.getMessage());
                    txa.setText("");
                    txa.append("\n"+e);
                }
            }
    }

    public static int test_url(File elem, String [] keywords)
    {
        int res=0;
        URL url = null;
        URLConnection con = null;
        int i;
        try
        {
            String ffele=""+elem;
            url = new URL("file:/"+ffele.trim());
            con = url.openConnection();
            File file = new File("./src/result.html");
            BufferedInputStream bis = new BufferedInputStream(
                    con.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            String bhtml=""; //file content in byte array

            while ((i = bis.read()) != -1) {
                bos.write(i);
                bhtml+=(char)i;
            }
            bos.flush();
            bis.close();
            String htmlcontent=
                    (new String(bhtml)).toLowerCase(); //file content in string
            System.out.println("New url content is: "+htmlcontent);
            for (int j=0;j<keywords.length;j++)
            {
                if(htmlcontent.indexOf(keywords[j].trim().toLowerCase())>=0)
                    res++;
            }}
        catch (MalformedInputException malformedInputException)
        {
            System.out.println("error "+malformedInputException.getMessage());
            return -1;
        }
        catch (IOException ioException)
        {
            System.out.println("error "+ioException.getMessage());
            return -1;
        }
        catch(Exception e)
        {
            System.out.println("error "+e.getMessage());
            return -1;
        }
        return res;
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
