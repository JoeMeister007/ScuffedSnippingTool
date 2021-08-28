package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.image.*;
import java.io.*;

public class GUI {
    JFrame f;
    JButton takeSS;
    JScrollPane imgContainer;
    JLabel capture;
    ImageIcon logo;
    Robot rodriguez;
    Owner o;


    public GUI(){
        //Robot
        try {
            rodriguez = new Robot();
        } catch(Exception e) {
            System.out.println(e);
        }
        //Make Clipboard Owner
        o = new Owner();

        //JFrame
        f = new JFrame("Scuffed Snipping Tool");
        logo = new ImageIcon("snippingToolLogo.png");
        f.setIconImage(logo.getImage());

        capture = new JLabel();

        imgContainer = new JScrollPane(capture);
        imgContainer.setVisible(false);
        f.add(imgContainer,BorderLayout.CENTER);

        //Screen Shot Button
        takeSS = new JButton("Click To Take A Screen Shot Of The Screen");
        takeSS.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage i = takeScreenShot();
                copyToClipboard(i);
                capture.setIcon(new ImageIcon(i));
                imgContainer.setVisible(true);
                f.revalidate(); //simple line of code that fixed error requiring you to update the window after the first snip to see it

            }
        });
        f.add(takeSS,BorderLayout.NORTH);

        //JFrame Config Stuff
        f.setSize(415,120);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public BufferedImage takeScreenShot(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rec = new Rectangle(screenSize);
        f.setVisible(false);
        BufferedImage img = rodriguez.createScreenCapture(rec);
        f.setVisible(true);
        return img;
    }

    public void copyToClipboard(BufferedImage bi){
        try{
            TransferableImage ti = new TransferableImage(bi);
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(ti, o);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
