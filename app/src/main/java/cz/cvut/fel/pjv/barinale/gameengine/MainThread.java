package cz.cvut.fel.pjv.barinale.gameengine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import cz.cvut.fel.pjv.barinale.gameengine.view.GamePanel;

public class MainThread extends Thread{
    public static final int MAX_FPS = 30;
    //public double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel game_panel;
    private boolean running;
    private static Canvas canvas;
    private long total_time;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.game_panel = gamePanel;
    }

    @Override
    public void run(){
        long start_time, wait_time, time_millis;
        //int frame_count = 0;
        long target_time = 1000/MAX_FPS;
        while (running){
            start_time = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.game_panel.update();
                    this.game_panel.draw(canvas);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            time_millis = (System.nanoTime() - start_time)/1000000;
            wait_time = target_time - time_millis;
            try {
                if (wait_time > 0){
                    this.sleep(wait_time);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            total_time += (System.nanoTime() - start_time) / 1000000;
            /*
            frame_count++;
            if (frame_count == MAX_FPS){
                averageFPS = 1000/(total_time / frame_count)/1000000;
                frame_count = 0;
                total_time = 0;
                System.out.println(averageFPS);
            }*/
        }
    }
}
