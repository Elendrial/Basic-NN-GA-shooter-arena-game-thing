package me.hii488;

import java.awt.Rectangle;
import java.util.ArrayList;

import me.hii488.objects.AIObject;
import me.hii488.objects.BulletObject;
import me.hii488.objects.PhysCircle;
import me.hii488.objects.PhysObject;
import me.hii488.objects.PhysWallObject;
import me.hii488.registries.RegisteredObjects;

public class ObjectHandler {
	
	public static ArrayList<PhysObject> update(ArrayList<PhysObject> objs){
		// NOTE #1 : This is messy and horrible, I apologise to anyone who ever thinks to read this code.
		// NOTE #2 : I know a lot of heavily repeated code could be taken out by using a new method or some loops, but I'm too tired, I'll do it some other time.
		for(int i = 0; i < objs.size(); i++){
			if(objs.get(i) instanceof AIObject){
				int[] sides = new int[4];
				int moveScale = ((AIObject)objs.get(i)).moveScale;
				Rectangle r = objs.get(i).getRect();
				
				// LEFT
				Rectangle rleft = new Rectangle(r.x - moveScale, r.y, r.width, r.height);
				boolean passable = false;
				int fortheside = 0;
				
				for(int j = 1; j < 5 && !passable; j++ ){
					fortheside = j;
					if(Math.floor(moveScale/j) != 0){
						boolean b = true;
						for(int k = 0; k < objs.size() && b; k++){
							if(k == i ) k++;
							if(k < objs.size()){
								if(rleft.intersects(objs.get(k).getRect())){
									b = false;
								}
							}
						}
						passable = b;
					}
					else{
						fortheside = 0;
						j = 6;
					}
				}
				
				sides[0] = fortheside;
				
				
				// RIGHT
				rleft = new Rectangle(r.x + moveScale, r.y, r.width, r.height);
				passable = false;
				fortheside = 0;
				
				for(int j = 1; j < 5 && !passable; j++ ){
					fortheside = j;
					if(Math.floor(moveScale/j) != 0){
						boolean b = true;
						for(int k = 0; k < objs.size() && b; k++){
							if(k == i) k++;
							if(k < objs.size()){
								if(rleft.intersects(objs.get(k).getRect())){
									b = false;
								}
							}
						}
						passable = b;
					}
					else{
						fortheside = 0;
						j = 6;
					}
				}
				
				sides[1] = fortheside;
				
				
				// UP
				rleft = new Rectangle(r.x, r.y + moveScale, r.width, r.height);
				passable = false;
				fortheside = 0;
				
				for(int j = 1; j < 5 && !passable; j++ ){
					fortheside = j;
					if(Math.floor(moveScale/j) != 0){
						boolean b = true;
						for(int k = 0; k < objs.size() && b; k++){
							if(k == i) k++;
							if(k < objs.size()){
								if(rleft.intersects(objs.get(k).getRect())){
									b = false;
								}
							}
						}
						passable = b;
					}
					else{
						fortheside = 0;
						j = 6;
					}
				}
				
				sides[2] = fortheside;
				
				
				// DOWN
				rleft = new Rectangle(r.x, r.y - moveScale, r.width, r.height);
				passable = false;
				fortheside = 0;
				
				for(int j = 1; j < 5 && !passable; j++ ){
					fortheside = j;
					if(Math.floor(moveScale/j) != 0){
						boolean b = true;
						for(int k = 0; k < objs.size() && b; k++){
							if(k == i) k++;
							if(k < objs.size()){
								if(rleft.intersects(objs.get(k).getRect())){
									b = false;
								}
							}
						}
						passable = b;
					}
					else{
						fortheside = 0;
						j = 6;
					}
				}
				
				sides[3] = fortheside;
				
				objs.get(i).updateOnTick(sides);
			}
			
			if(objs.get(i) instanceof BulletObject){
				@SuppressWarnings("unchecked")
				ArrayList<PhysObject> temp = (ArrayList<PhysObject>) objs.clone();
				temp.remove(i);
				PhysObject o = isColliding(temp, objs.get(i));
				if(o != null){
					((BulletObject)objs.get(i)).onDestroy();
					((AIObject)o).timesShot++;
				}
				else{
					objs.get(i).updateOnTick();
				}
			}
			
		}
		
		
		return objs;
	}
	
	public static PhysObject isColliding(ArrayList<PhysObject> objs, PhysObject object){
		// NOTE : Only supposed to work for Bullets colliding with AIOBjects, this will need updating if more shootables are used.
		if(object instanceof BulletObject){
			for(int i = 0; i < objs.size(); i++){
				if(objs.get(i) instanceof AIObject){
					int dx = object.getPosition().getX() - objs.get(i).getPosition().getX();
					int dy = object.getPosition().getY() - objs.get(i).getPosition().getY();
					double distance = Math.sqrt(dx * dy);
					if(distance < object.getRadius() + objs.get(i).getRadius()){
						return objs.get(i);
					}
				}
			}
		}
		
		return null;
	}
	
	
	// NOTE #1 : This will only work for the specific AI testing, any other simulations and it will break and be buggy
	// NOTE #2 : Don't use this! It doesn't work well as far as I can tell, use update() above ^^
    @SuppressWarnings("unused")
    @Deprecated
	private void tick(){
    	int size = RegisteredObjects.getObjs().size();
    	
    	/*
    	// Check for collisions
    	for(int i = 0; i < size; i++){
    		PhysObject iPhys = World.registeredObjects.getObjs().get(i);
    		iPhys.update = true;
    		for(int j = i+1; j < size; j++){
    			PhysObject jPhys = World.registeredObjects.getObjs().get(j);
        		jPhys.update = true;
    			if(iPhys instanceof PhysWallObject || jPhys instanceof PhysWallObject){
    				if(iPhys.getRect().intersects(jPhys.getRect())){
    					iPhys.update = false;
    					jPhys.update = false;
    				}
    			}
    		}
    		if(iPhys.update){
    			iPhys.updateOnTick();
    		}
    	}
    	*/
    	
    	for(int i = 0; i < size; i++){
    		PhysObject iPhys = RegisteredObjects.getObjs().get(i);
    		for(int j = i+1; j < size; j++){
    			PhysObject jPhys = RegisteredObjects.getObjs().get(j);
    			if(iPhys instanceof PhysWallObject || jPhys instanceof PhysWallObject){
    				if(iPhys.getRect().intersects(jPhys.getRect())){
    					
    					// Plans:
    					// Handle the non movement to the actual physObjects
    					// Input the side that the wall is in
    					// Maybe offset the rectangle to determine where it is?
    					// if wall in multiple places you could set a var in the physObject class
    					
    					int[] sides;
    					// side : 0 = left, 1 = right, 2 = up, 3 = down
    					
    					PhysObject nonWall;
    					PhysObject wall;
    					if(iPhys instanceof PhysWallObject){
    						nonWall = jPhys;
    						wall = iPhys;
    					}
    					else{
    						nonWall = jPhys;
    						wall = iPhys;
    					}
    					
    					
    				}
    			}
    		}
    	}
    	    	
    }
	
}
