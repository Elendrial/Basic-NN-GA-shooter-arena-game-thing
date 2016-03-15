package me.hii488.registries;

import java.awt.Rectangle;
import java.util.ArrayList;

import me.hii488.auxilary.Position;
import me.hii488.objects.AIObject;
import me.hii488.objects.BulletObject;
import me.hii488.objects.PhysObject;

public class RegisteredObjects {
	
	private static ArrayList<PhysObject> objs = new ArrayList<>();
	private static ArrayList<AIObject> aiObjs = new ArrayList<>();
	private static ArrayList<PhysObject> movableObjs = new ArrayList<>();

	
	public static ArrayList<PhysObject> getObjs() {
		return objs;
	}

	public static void setObjs(ArrayList<PhysObject> objs) {
		RegisteredObjects.objs = objs;
	}
	
	public static int addToObjs(PhysObject objs) {
		RegisteredObjects.objs.add(objs);
		return RegisteredObjects.objs.indexOf(objs);
	}
	
	public static void removeAtObjs(int i){
		RegisteredObjects.objs.remove(i);
	}
	
	public static void removeAtObjs(int[] i){
		for(int j = 0; j < i.length; j++){
			RegisteredObjects.objs.remove(i[j]);
		}
	}
	
	public static int findExactMatchObjs(PhysObject p){
		return objs.indexOf(p);
	}
	
	public static void removeObject(PhysObject obj){
		objs.remove(obj);
	}
	
	// Left in because I might need it one day, who knows?
	public static ArrayList<PhysObject> getObjsFullyInRect(int x, int y, int width, int height){
		ArrayList<PhysObject> matchingObjs = new ArrayList<PhysObject>();
		for(int i = 0; i < objs.size(); i++){
			if(objs.get(i).getPosition().getX() >= x && objs.get(i).getPosition().getX() <= width && objs.get(i).getPosition().getX() <= height && objs.get(i).getPosition().getY() >= y){
				matchingObjs.add(objs.get(i));
			}
		}
		return matchingObjs;
	}
	
	public static ArrayList<PhysObject> getObjsInRect(int x, int y, int width, int height){
		return getObjsInRect(new Rectangle(x,y,width,height));
	}

	public static ArrayList<PhysObject> getObjsInRect(Rectangle r){
		ArrayList<PhysObject> matchingObjs = new ArrayList<PhysObject>();
		for(int i = 0; i < objs.size(); i++){
			if(objs.get(i).getRect().intersects(r)){
				matchingObjs.add(objs.get(i));
				if(objs.get(i) instanceof BulletObject){
					System.out.print("");
				}
			}
			else if(objs.get(i) instanceof BulletObject){
				if(Math.abs((double)(objs.get(i).getPosition().getAbsX() - r.getX())) < r.getWidth() && Math.abs((double)(objs.get(i).getPosition().getAbsY() - r.getY())) < r.getHeight()){
					matchingObjs.add(objs.get(i));
				}
			}
		}
		return matchingObjs;
	}
	
	
	
	public static ArrayList<AIObject> getAiObjs() {
		return aiObjs;
	}
	
	public static void setAiObjs(ArrayList<AIObject> aiObjs) {
		RegisteredObjects.aiObjs = aiObjs;
	}
	
	public static int addToAiObjs(AIObject objs) {
		RegisteredObjects.aiObjs.add(objs);
		return RegisteredObjects.aiObjs.indexOf(objs);
	}
	
	public static void removeAtAiObjs(int i){
		RegisteredObjects.aiObjs.remove(i);
	}
	
	public static void removeAtAiObjs(int[] i){
		for(int j = 0; j < i.length; j++){
			RegisteredObjects.aiObjs.remove(i[j]);
		}
	}
	
	public static int findExactMatchAiObjs(PhysObject p){
		return aiObjs.indexOf(p);
	}
	
	public static void removeAIObject(PhysObject obj){
		aiObjs.remove(obj);
	}
	
	
	public static ArrayList<PhysObject> getMovableObjs() {
		return movableObjs;
	}
	
	public static int findExactMatchMovableObjsObjs(PhysObject p){
		return movableObjs.indexOf(p);
	}
	
	public static void setMovableObjs(ArrayList<PhysObject> aiObjs) {
		RegisteredObjects.movableObjs = aiObjs;
	}
	
	public static int addToMovableObjs(AIObject objs) {
		RegisteredObjects.movableObjs.add(objs);
		return RegisteredObjects.movableObjs.indexOf(objs);
	}
	
	public static void removeAtMovableObjs(int i){
		RegisteredObjects.movableObjs.remove(i);
	}
	
	public static void removeAtMovableObjs(int[] i){
		for(int j = 0; j < i.length; j++){
			RegisteredObjects.movableObjs.remove(i[j]);
		}
	}
	
	public static void removeMovableObject(PhysObject obj){
		movableObjs.remove(obj);
	}
	
	
	
	public static PhysObject findClostestToPoint(Position p){
		float closestDist = 10000;
		int objectNumber = 0;
		for(int i = 0; i < objs.size(); i++){
			float j = objs.get(i).distToPoint(p) ;
			if(j < closestDist){
				closestDist = j;
				objectNumber = i;
			}
		}
		
		return objs.get(objectNumber);
	}

	
	public static void wipeBullets(){
		ArrayList<PhysObject> temp = getObjs();
		for(int i = temp.size()-1; i >= 0; i--){
			if(temp.get(i) instanceof BulletObject){
				temp.remove(i);
			}
		}
	}
	
}
