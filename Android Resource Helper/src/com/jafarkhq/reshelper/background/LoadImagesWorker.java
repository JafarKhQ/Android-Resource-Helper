/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jafarkhq.reshelper.background;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author mahdihijazi
 */
public class LoadImagesWorker {

    private static final int NUM_OF_THREADS = 5;
    private static final LoadImagesWorker INSTANCE = new LoadImagesWorker();
    private ExecutorService mExecutor;
    
    private LoadImagesWorker() {
         mExecutor = Executors.newFixedThreadPool(NUM_OF_THREADS);
         
    }
    
    public static LoadImagesWorker getInstance() {
        return INSTANCE;
        
    }
    
    public void loadImage(LoadImageRunnable loadImageRunnable) {
        mExecutor.execute(loadImageRunnable);
        
    }
    
   
    
    
}
