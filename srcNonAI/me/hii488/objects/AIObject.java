package me.hii488.objects;

import java.awt.Color;
import java.awt.Graphics;
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
	public final Position startingPosition;
	
	public float closest = 10000;
	
	public int amountOnTarget = 0;
	public int amountOnPredTarget = 0;
	public int timesShot = 0;
	public int shootCooldown = 0;
	public int shootCounter = 0;
	
	public int totalDistanceMoved = 0;
	public int moveScale = 1;
	public int inputRectSideLength = 500;
	
	
	public AIObject(Position position, int aiNumber) {
		super(position, new GenericVector(0,0), new GenericVector(0,0), 0, 10);
		this.aiNumber = aiNumber;
		
		this.startingPosition = position.getPositionClone();
	}

	public AIObject(int x, int y, int aiNumber){
		this(new Position(x, y), aiNumber);
	}
	
	public AIObject(int aiNumber) {
		this(0, 0, aiNumber);
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
		
		//System.out.println(Arrays.toString(outputs));
		
		if(shootCooldown > 0)shootCooldown -= 1;
		
		for(int i = 0; i < outputs.length && outputs[i] != null; i++){
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
				if(shootCooldown <=0){
					new BulletObject(this).registerWithWindow();
					shootCounter++;
					shootCooldown += 5;
				}
				break;
			}
		}
		this.position.addToLocation(deltaX * moveScale, deltaY * moveScale);
		this.totalDistanceMoved += Math.abs(deltaX)+Math.abs(deltaY);
		this.rotation += deltaR;
		if(rotation > 359) rotation -= 360;
	}
	
	@Override
	public void updateOnTick(){
		updateOnTick(new int[]{1,1,1,1});
	}
	
	public float[] getAiInputs(){
		ArrayList<PhysObject> objectsInArea = RegisteredObjects.getObjsInRect(this.position.getX()-inputRectSideLength/2, this.position.getY()-inputRectSideLength/2, inputRectSideLength, inputRectSideLength);
		float[] f = new float[GeneralVars.inputs];
		float side = (float) (inputRectSideLength/Math.sqrt(f.length-1));
		float tempNameBecauseICantThinkOfAnythingElse = (float) Math.sqrt(f.length-1);
		for(int i = 0; i < tempNameBecauseICantThinkOfAnythingElse; i++){
			for(int j = 0; j < tempNameBecauseICantThinkOfAnythingElse; j++){
				for(int k = 0; k < objectsInArea.size(); k++){
					Rectangle r = new Rectangle((int)(this.position.getX()-inputRectSideLength/2 + i*side), (int)(this.position.getY()-inputRectSideLength/2 + j*side), (int)(side), (int)(side));
				//	r.setBounds((int)(this.position.getX()-50 + i*side), (int)(this.position.getY()-50 + j*side), (int)(side*side), (int)(side*side));
					if(r.intersects(objectsInArea.get(k).getRect())){
						// To change/add weights of different objects, change these values/add more.
						if(objectsInArea.get(k) instanceof PhysWallObject){
							f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] -= 5;
						}
						if(objectsInArea.get(k) instanceof BulletObject && ((AIObject)((BulletObject)objectsInArea.get(k)).shooter).aiNumber != this.aiNumber){
							f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] -= 1;
						}
						if(objectsInArea.get(k) instanceof AIObject && ((AIObject)objectsInArea.get(k)).aiNumber != this.aiNumber){
							f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] += 5;
						}
					}
				}
			}
		}
		//System.out.println(Arrays.toString(f));
		f[f.length-1] = (float) Math.toRadians(this.getRotation());
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
	
	@Override
	public void getRender(Graphics g){
		super.getRender(g);
		g.drawLine(this.position.getX(), this.position.getY(), this.position.getX()+(int)(Math.cos(Math.toRadians(this.rotation))*radius), this.position.getY()+(int)(Math.sin(Math.toRadians(this.rotation))*radius));
	//	g.drawRect(this.position.getX()-500, this.position.getY()-500, 1000, 1000);
	//	g.drawString(getString(), (int)this.position.getAbsX(), (int)this.position.getAbsY());
	//	renderInputs(g);
	}
	
	// WARNING : Do not use this unless debugging, it'll probably slow down the program quite a bit.
	// NOTE    : Keep this code the same as the getAiInputs(), to make sure what is visualised is actually what is happening.
	public void renderInputs(Graphics g){
		Color c = g.getColor();
		ArrayList<PhysObject> objectsInArea = RegisteredObjects.getObjsInRect(this.position.getX()-inputRectSideLength/2, this.position.getY()-inputRectSideLength/2, inputRectSideLength, inputRectSideLength);
		float[] f = new float[GeneralVars.inputs];
		float side = (float) (inputRectSideLength/Math.sqrt(f.length-1));
		float tempNameBecauseICantThinkOfAnythingElse = (float) Math.sqrt(f.length-1);
		for(int i = 0; i < tempNameBecauseICantThinkOfAnythingElse; i++){
			for(int j = 0; j < tempNameBecauseICantThinkOfAnythingElse; j++){
				for(int k = 0; k < objectsInArea.size(); k++){
					Rectangle r = new Rectangle((int)(this.position.getX()-inputRectSideLength/2 + i*side), (int)(this.position.getY()-inputRectSideLength/2 + j*side), (int)(side), (int)(side));
				//	r.setBounds((int)(this.position.getX()-50 + i*side), (int)(this.position.getY()-50 + j*side), (int)(side*side), (int)(side*side));
					if(r.intersects(objectsInArea.get(k).getRect())){
						// To change/add weights of different objects, change these values/add more.
						if(objectsInArea.get(k) instanceof PhysWallObject){
							f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] -= 5;
						}
						if(objectsInArea.get(k) instanceof BulletObject && ((AIObject)((BulletObject)objectsInArea.get(k)).shooter).aiNumber != this.aiNumber){
							f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] -= 1;
						}
						if(objectsInArea.get(k) instanceof AIObject && ((AIObject)objectsInArea.get(k)).aiNumber != this.aiNumber){
							f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] += 5;
						}
					}
				}
			//	System.out.println(i + j * side);
				if(f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] > 0)g.setColor(Color.green);
				if(f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] < 0)g.setColor(Color.red);
				if(f[(int) (i + j*tempNameBecauseICantThinkOfAnythingElse)] == 0)g.setColor(c);
				/*if(i + j*side < 2 )*/g.drawRect((int)(this.position.getX()-inputRectSideLength/2 + i*side), (int)(this.position.getY()-inputRectSideLength/2 + j*side), (int)(side), (int)(side));
				//g.drawString(""+f[(int) (i + j*side)], (int)(this.position.getX()-inputRectSideLength + i*side*2), (int)(this.position.getY()-inputRectSideLength + j*side*2));
			}
		}
		g.setColor(c);
	}
	
	//TODO : Tune fitness algorithm.
	public void calculateAndSendFitness(){
		int fitness = 0;
		int accuracy = (shootCounter ==0) ? -1 :(amountOnTarget+amountOnPredTarget) / shootCounter;
		
		if(accuracy != -1){
			fitness += 20;
			if(closest != 0){
				fitness += Math.floor((1/Math.abs(closest/2))*100D);
			}
			fitness += accuracy * 10;
			fitness += amountOnTarget * 5;
			fitness -= timesShot > 10 ? 50 : timesShot * 5;
			fitness += this.totalDistanceMoved / 20;
		}
		if(fitness < 0) fitness = 0;

		System.out.print("AI Number : " + AIController.currentPositions[aiNumber] + "\tLocal Fitness : " + fitness + "\t");
		GeneticAlgorithm.children.get(AIController.currentPositions[aiNumber]).fitness += fitness;
		System.out.println("Total Fitness : "+GeneticAlgorithm.children.get(AIController.currentPositions[aiNumber]).fitness);
	}
	
	public void resetAIObj(){
		this.position = this.startingPosition.getPositionClone();
		this.rotation = 0f;
		this.amountOnPredTarget = 0;
		this.amountOnTarget = 0;
		this.closest = 10000f;
		this.shootCooldown = 0;
		this.shootCounter = 0;
		this.timesShot = 0;
		this.totalDistanceMoved = 0;
	}
	
	// NOTE : I may give each object a proper ID at some point, although depends on how much more work I put into this
	public String getString(){
		return aiNumber + "";
	}
	
}
