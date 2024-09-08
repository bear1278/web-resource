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
    boolean CanBeOpened = false;
    String path= "E:/d disk/учёба/4 курс/АиПРП/lab1/src/source_txt/authors.txt";
    String HTMLpath= "E:/d disk/учёба/4 курс/АиПРП/lab1/src/result.html";
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
        int max=0;
        String resultAuthor="";
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
            File file = new File(path);
            txa.setText("");
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split(":");
                    if (parts.length < 2) continue; //

                    String author = parts[0].trim();
                    String[] books = parts[1].split(",");
                    int coincidence=test_url(books,keywords);
                    if (coincidence>max){
                        max=coincidence;
                        resultAuthor=author;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (max>0){
                writeToHtml(HTMLpath,resultAuthor);
                txa.setText(resultAuthor);
                CanBeOpened=true;
            }
            else{
                txa.setText("No such author");
                CanBeOpened=false;
            }
        }
        else
            if (ae.getSource()==buttonOpen){
                try {
                    if (!CanBeOpened){
                        throw new Exception("Nothing to open");
                    }

                    File file = new File(HTMLpath);

                    // Check if Desktop is supported on the current platform
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();

                        // Open the .txt file
                        if (file.exists()) {
                            desktop.browse(file.toURI());
                        } else {
                            System.out.println("File does not exist");
                        }
                    } else {
                        System.out.println("Desktop is not supported on your platform.");
                    }
                } catch (Exception e) {
                    System.out.println("error "+e.getMessage());

                }
            }
    }



    public static void writeToHtml(String path,String author){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path,false))) {

                writer.write(author);
                writer.newLine();
            System.out.println("File written successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static int test_url(String [] books, String [] keywords)
    {
        int res =0;
        try
        {
            String htmlcontent= String.join(" ",books).toLowerCase();
            for (int j=0;j<keywords.length;j++)
            {
                if(htmlcontent.indexOf(keywords[j].trim().toLowerCase())>=0)
                    res++;
            }}
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
