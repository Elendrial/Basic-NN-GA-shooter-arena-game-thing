package me.hii488.registries;

import me.hii488.Phys2D;
import me.hii488.objects.AIObject;
import me.hii488.objects.PhysHoriWallObject;
import me.hii488.objects.PhysObject;
import me.hii488.objects.PhysVertWallObject;

public class StartingObjectRegistry {
	
	public static PhysObject wall;
	public static PhysObject testObj;
	
	public static void initObjects(){
		
		//wall = new PhysWallObject(Phys2D.window.width/2, 0, 10, Phys2D.window.height-1).registerWithWindow();
		
		// int x, int y, float mass, int sides, float radius
		//testObj = new PhysObject(10, 200, 0, 4, 10).setVelocity(new GenericVector(5,0)).setRotation(45).registerWithWindow();
			
	}
	
	public static void makeArena(int AIs){
		new PhysHoriWallObject(-1, 0, Phys2D.window.width+1).registerWithWindow();
		new PhysHoriWallObject(-1, Phys2D.window.height-5, Phys2D.window.width+1).registerWithWindow();
		new PhysVertWallObject(0, 0, Phys2D.window.height).registerWithWindow();
		new PhysVertWallObject(Phys2D.window.width-5, 0, Phys2D.window.height).registerWithWindow();
		
		//TODO : allow for configurations of more than 4 AI's.
		
		new PhysHoriWallObject(-1, Phys2D.window.height/2-5, Phys2D.window.width).registerWithWindow();
		new PhysVertWallObject(Phys2D.window.width/2-5, 0, Phys2D.window.height).registerWithWindow();
		
		new AIObject(Phys2D.window.width/4, Phys2D.window.height/4, 0).registerWithWindow();
		new AIObject(Phys2D.window.width * 3/4, Phys2D.window.height/4, 1).registerWithWindow();
		if(AIs > 2)new AIObject(Phys2D.window.width/4, Phys2D.window.height * 3/4, 2).registerWithWindow();
		if(AIs > 3)new AIObject(Phys2D.window.width*3/4, Phys2D.window.height*3/4, 3).registerWithWindow();
	}
	
}
