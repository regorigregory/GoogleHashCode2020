/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rrrrr.hashcode.entities;

/**
 *
 * @author Madero Padero
 */
public class SimpleTimer {
    private long startTime = 0l;
    private long endTime = 0l;
    private double timeDelta = 0.0;
    
    public  void timeIt(){
        if(startTime == 0){
            startTime = System.currentTimeMillis();
            System.out.println("Timing the process has started.");

        }else {
            endTime = System.currentTimeMillis();
            timeDelta = (endTime-startTime);
            double timeDeltaPrecise = timeDelta/1000.0;
            startTime = 0l;
            System.out.println("The elapsed time between start and end: " + timeDeltaPrecise +" s.");
            
        }
        
    }
}
