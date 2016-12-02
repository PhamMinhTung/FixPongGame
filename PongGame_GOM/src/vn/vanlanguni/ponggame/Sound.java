package vn.vanlanguni.ponggame;


import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * 
 * @author Của em
 *
 */
public class Sound {
	
    public static synchronized void play(final String fileMusic) 
    {
        // Note: use .wav files             
        new Thread(new Runnable() { 
            public void run() {
                try {
                    Clip clp = AudioSystem.getClip();
                    AudioInputStream inStream = AudioSystem.getAudioInputStream(new File(fileMusic));
                    clp.open(inStream);
                    clp.start(); 
                } catch (Exception e) {
                    System.out.println("play sound error: " + e.getMessage() + " for " + fileMusic);
                }
            }
        }).start();
    }
}