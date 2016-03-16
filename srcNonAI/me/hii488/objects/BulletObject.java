package me.hii488.objects;

import java.util.ArrayList;

import me.hii488.ObjectHandler;
import me.hii488.auxilary.GenericVector;
import me.hii488.auxilary.Position;
import me.hii488.registries.RegisteredObjects;

public class BulletObject extends PhysCircle{
	
	final public PhysObject shooter;
	protected float closestToEnemy = 10000f;
	protected boolean onTarget;
	protected boolean onPredictiveTarget;
	protected float speedConstant = 3f;
	protected float timeAlive = 0f;
	
	
	public BulletObject(Position position, float mass, float radius, PhysObject shooter){
		this.acceleration = new GenericVector(0f,0f);
		this.mass = mass;
		this.radius = radius;
		this.shooter = shooter;
		
		workOutVelocity();
		isOnTarget();
	//	isOnPredictiveTarget();

		this.position = new Position(position.getX() + velocity.getX()*shooter.radius*1.1f/speedConstant, position.getY() + velocity.getY()*shooter.radius*1.1f/speedConstant);
	}
	
	public BulletObject(int x, int y, float mass, float radius, PhysObject shooter){
		this(new Position(x, y), mass, radius, shooter);
	}
	
	public BulletObject(PhysObject shooter){
		this(shooter.position.getX() , shooter.position.getY(),0,1f, shooter);
	}
		
	public void onDestroy(){
		((AIObject) shooter).onBulletDestroyed(closestToEnemy, onTarget, onPredictiveTarget);
		RegisteredObjects.removeObject(this);
		RegisteredObjects.removeMovableObject(this);
	}
	
	@Override
	public void updateOnTick(){
		timeAlive++;
		if(timeAlive < 500){
			position.addToLocation(velocity.getX(), velocity.getY());
			
			
			PhysObject i = RegisteredObjects.findClostestToPoint(this.position, (AIObject) shooter);
			float dist = i.distToPoint(this.position) - i.radius;
	
			if(closestToEnemy > i.distToPoint(this.position)){
				closestToEnemy = dist;
			}
		}
		else{
			onDestroy();
		}
	}
	
	
	
	public void workOutVelocity(){
		GenericVector gv = new GenericVector();
		gv.setX((float) Math.cos(Math.toRadians(shooter.rotation)) * speedConstant);
		gv.setY((float) Math.sin(Math.toRadians(shooter.rotation)) * speedConstant);
		velocity = gv;
	}
	
	
	public void isOnTarget(){
		ArrayList<PhysObject> objs = RegisteredObjects.getMovableObjs();
		objs.add(this);
		int number = objs.size()-1;
		int count = 0;
		
		while(ObjectHandler.isColliding(objs, objs.get(number))!=null || count <= 50){
			objs.get(number).updateOnTick();
			count++;
		}
		
		onTarget = ObjectHandler.isColliding(objs, objs.get(number)) != null;
		
	}
	
	// I *THINK* this is done ( I hope ;-; )
	// It's not ;-; - causes infinite loop somehow, which eventually crashes the entire thing .-.
	// TODO : this
	public void isOnPredictiveTarget(){
		ArrayList<PhysObject> objs = RegisteredObjects.getMovableObjs();
		objs.add(this);
		int number = objs.size()-1;
		int count = 0;
		
		while(ObjectHandler.isColliding(objs, objs.get(number)) != null || count <= 50){
			ObjectHandler.update(objs);
			count++;
		}
		
		onTarget = ObjectHandler.isColliding(objs, objs.get(number)) != null;
		
	}
}
