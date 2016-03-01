package me.hii488;

import me.hii488.gameWindow.Window;
import me.hii488.registries.StartingObjectRegistry;
import me.hii488.shooterAI.AIController;

public class Phys2D {
	
	public static Window window;
	
	public static void main(String[] args) {
		window = new Window("Phys2D", 1000, 800);
		
		AIController.setupAI(2);
		StartingObjectRegistry.initObjects();
		
		window.start();
	}

}
