package me.hii488.objects;

import java.awt.Rectangle;
import java.util.ArrayList;

import me.hii488.auxilary.GenericVector;
import me.hii488.auxilary.Position;
import me.hii488.registries.RegisteredObjects;
import me.hii488.shooterAI.AIController;
import me.hii488.shooterAI.GeneralVars;
import me.hii488.shooterAI.GeneticAlgorithm;

public class AIObject extends PhysCircle{
	
	public final int aiNumber;
	public float closest = 10000;
	public int amountOnTarget = 0;
	public int amountOnPredTarget = 0;
	
	public int timesShot = 0;
	
	public int moveScale = 1;
	
	public AIObject(Position position, float mass, float radius, int aiNumber) {
		super(position, new GenericVector(0,0), new GenericVector(0,0), mass, radius);
		this.aiNumber = aiNumber;
	}

	public AIObject(int x, int y, float mass, float radius, int aiNumber){
		this(new Position(x, y), mass, radius, aiNumber);
	}
	
	public AIObject(int aiNumber) {
		this(0, 0, 0, 1, aiNumber);
	}
	
	@Override
	public PhysObject registerWithWindow(){
		RegisteredObjects.addToAiObjs(this);
		RegisteredObjects.addToMovableObjs(this);
		super.registerWithWindow();
		return this;
	}
	
	@Override
	public void updateOnTick(int[] unpassable){
		String[] outputs = GeneticAlgorithm.getOutputs(getAiInputs(), AIController.currentPositions[aiNumber]);
		
		int deltaX = 0, deltaY = 0, deltaR = 0;
		
		
		
		for(int i = 0; i < outputs.length; i++){
			switch(outputs[i]){
			case "ml":
				deltaX -= unpassable[0];
				break;
			case "mr":
				deltaX += unpassable[1];
				break;
			case "mu":
				deltaY += unpassable[2];
				break;
			case "md":
				deltaY -= unpassable[3];
				break;
			case "rr":
				deltaR ++;
				break;
			case "rl":
				deltaR --;
				break;
			case "s":
				new BulletObject(this).registerWithWindow();
				break;
			}
		}
		this.position.addToLocation(deltaX * moveScale, deltaY * moveScale);
		this.rotation =+ deltaR;
		if(rotation > 359) rotation -= 360;
	}
	
	@Override
	public void updateOnTick(){
		updateOnTick(new int[]{1,1,1,1});
	}
	
	public float[] getAiInputs(){
		ArrayList<PhysObject> objectsInArea = RegisteredObjects.getObjsInRect(this.position.getX()-50, this.position.getY()-50, 100, 100);
		float[] f = new float[GeneralVars.inputs];
		float side = (float) Math.sqrt(f.length);
		for(int i = 0; i < side; i++){
			for(int j = 0; j < side; j++){
				for(int k = 0; k < objectsInArea.size(); k++){
					Rectangle r = new Rectangle();
					r.setBounds((int)(this.position.getX()-50 + i*side), (int)(this.position.getY()-50 + j*side), (int)(side*side), (int)(side*side));
					if(r.intersects(objectsInArea.get(k).getRect())){
						f[(int) (i + j*side)] += 1;
					}
				}
			}
		}
		return f;
	}
	
	public void onBulletDestroyed(float closestDist, boolean onTarget, boolean onPredictiveTarget){
		if(closestDist < closest){
			closest = closestDist;
		}
		if(onTarget){
			amountOnTarget++;
		}
		if(onPredictiveTarget){
			amountOnPredTarget++;
		}
	}
}