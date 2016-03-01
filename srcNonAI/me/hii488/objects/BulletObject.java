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
	protected float speedConstant = 10f;
	
	public BulletObject(Position position, float mass, float radius, PhysObject shooter){
		this.position = position;
		this.acceleration = new GenericVector(0f,0f);
		this.mass = mass;
		this.radius = radius;
		this.shooter = shooter;
		
		workOutVelocity();
		isOnTarget();
		isOnPredictiveTarget();
	}
	
	public BulletObject(int x, int y, float mass, float radius, PhysObject shooter){
		this(new Position(x, y), mass, radius, shooter);
	}
	
	public BulletObject(PhysObject shooter){
		this(shooter.position.getX() , shooter.position.getY(),0,0.5f, shooter);
	}
		
	public void onDestroy(){
		((AIObject) shooter).onBulletDestroyed(closestToEnemy, onTarget, onPredictiveTarget);
	}
	
	@Override
	public void updateOnTick(){
		position.addToLocation(velocity.getX(), velocity.getY());
		
		
		PhysObject i = RegisteredObjects.findClostestToPoint(this.position);
		float dist = i.distToPoint(this.position) - i.radius;
		if(closestToEnemy > i.distToPoint(this.position)){
			closestToEnemy = dist;
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
