package com.horstmann.violet.application.StepTwoExpand;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressUI extends BasicProgressBarUI {

    private JProgressBar jProgressBar;
//    private int progressvalue;
    private Color forecolor;

    public ProgressUI(JProgressBar jProgressBar,Color forecolor) {
        this.jProgressBar = jProgressBar;
        this.forecolor=forecolor;
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {

        this.jProgressBar.setBackground(new Color(255, 255, 255));
        this.jProgressBar.setForeground(forecolor);
        
//        progressvalue=this.jProgressBar.getValue();
//        
//        if(progressvalue<20){
//        	this.jProgressBar.setForeground(Color.BLUE);
//        }
//        else if(progressvalue<40){
//        	this.jProgressBar.setForeground(Color.YELLOW);
//        } 
//        else if(progressvalue<60){
//        	this.jProgressBar.setForeground(Color.RED);
//        } 
//        else if(progressvalue<80){
//        	this.jProgressBar.setForeground(Color.GREEN);
//        } 
//        else{
//        	this.jProgressBar.setForeground(Color.CYAN);
//        } 
        
        super.paintDeterminate(g, c);
    }

}
