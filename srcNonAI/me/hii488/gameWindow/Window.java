package me.hii488.gameWindow;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import me.hii488.ObjectHandler;
import me.hii488.Settings;
import me.hii488.objects.AIObject;
import me.hii488.registries.RegisteredObjects;
import me.hii488.shooterAI.AIController;

public class Window implements Runnable{
	
	// Actual window
    private JFrame frame;
    private Display display;
    
    public int width, height;
    public String title;

    // How often we want the game to tick per second
    public int targetTPS;

    public boolean isRunning;
    
    
    public Window(String title, int width, int height){
        // Set the variables
        this.title = title;
        this.width = width;
        this.height = height;

        // Set the target TPS
        this.targetTPS = (int) (Settings.WorldSettings.TargetTPS * Settings.WorldSettings.currentSpeed);

        // Setup Window
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setPreferredSize(new Dimension(width, height));
        this.frame.setResizable(false);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        // Create the display
        this.display = new Display(this);
        this.frame.add(this.display);
    }

    public void start(){
        isRunning = true;
        new Thread(this).start();
    }

    public void stop(){
        isRunning = false;
    }

    
    // NOTE : this will only work for the specific AI testing, any other simulations and it will break and be buggy
    private void tick(){
    	ObjectHandler.update(RegisteredObjects.getObjs());
    }

	private void render(){
		// Buffer Strategy
        BufferStrategy bs = this.display.getBufferStrategy();
        if(bs == null){
        	this.display.createBufferStrategy(2);
            this.display.requestFocus();
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        // Clear the graphics
        g.clearRect(0, 0, width, height);
        
        // Draw the display
        this.display.render(g);
        
        g.dispose();
        bs.show();
    }

    
    // Tick is deliberately throttled, it should only happen every 'x' ms, as it applies game logic
    // FPS should happen as fast as it can, since it renders (only important if the field of vision can change)
    
    @Override
    public void run() {
        int fps = 0, tick = 0, totalTick = 0;
        
        double fpsTimer = System.currentTimeMillis();
        double secondsPerTick = 1D / targetTPS;
        double nsPerTick = secondsPerTick * 1000000000D;
        double then = System.nanoTime();
        double now;
        double unprocessed = 0;

        while(isRunning){
            now = System.nanoTime();
            unprocessed += (now - then) / nsPerTick;
            then = now;
            while(unprocessed >= 1){
                tick();
                tick++;
                totalTick++;
                unprocessed--;
            }

            // This is NOT to sleep, but to limit the game loop
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            render();
            fps++;

            // If the current time is 1 second greater than the last time we printed
            if(System.currentTimeMillis() - fpsTimer >= 1000){
                System.out.printf("FPS: %d, TPS: %d%n", fps, tick);
                fps = 0; tick = 0;
                fpsTimer += 1000;
            }
            
            if(totalTick % Settings.WorldSettings.ticksPerRound == 0){
            	ArrayList<AIObject> objs = RegisteredObjects.getAiObjs();
            	for(int i = 0; i < objs.size(); i++){
            		objs.get(i).calculateAndSendFitness();
            		objs.get(i).resetPosition();
            		RegisteredObjects.wipeBullets();
            		try {
		//				Thread.sleep(1000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	// Means it goes up once too often, but who really cares that much?
            	totalTick++;
            	AIController.updateChildren();	
            }
        }

        // When the gameloop is finished running, close the program
        this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
	
    }
}
