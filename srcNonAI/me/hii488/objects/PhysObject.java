package me.hii488.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;

import me.hii488.ObjectHandler;
import me.hii488.auxilary.GenericVector;
import me.hii488.auxilary.Position;
import me.hii488.registries.RegisteredObjects;

public class PhysObject {
	protected Position position;
	protected GenericVector velocity;
	protected GenericVector acceleration;
	protected float mass;
	protected float radius;
	protected int sides;
	protected Color color;
	protected float rotation;
	protected float rotationRate;
	protected int objectNum;
	
	// This is used by other classes
	public boolean update;

	public PhysObject(Position position, GenericVector velocity, GenericVector acceleration, float mass, int sides, float radius){
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
		this.sides = sides;
		this.radius = radius;
	}
	
	public PhysObject(Position position, float mass, int sides, float radius){
		this(position, new GenericVector(0f,0f), new GenericVector(0f,0f), mass, sides, radius);
	}
	
	public PhysObject(int x, int y, float mass, int sides, float radius){
		this(new Position(x, y), mass, sides, radius);
	}
	
	public PhysObject(){
		this(0,0,0,4,0.5f);
	}
	
	
	
	
	public void onCollision(Position pointOfContact){
		
	}
	
	public boolean checkCollision(PhysObject pO){
		return ObjectHandler.isColliding(RegisteredObjects.getObjs(), this) != null;
	}
	
	public void updateOnTick(){
		// side : 0 = left, 1 = right, 2 = up, 3 = down
		
		//	System.out.println("pos:" + position.getAbsX() + "; vel:" + velocity.getX());
		
		
		position.addToLocation(velocity.getX(), velocity.getY());
				
				
		velocity.addX(acceleration.getX());
		velocity.addY(acceleration.getY());
		rotation += rotationRate;
		if(rotation > 359) rotation -= 360;
	}
	
	public void updateOnTick(int[] side){
		// side : 0 = left, 1 = right, 2 = up, 3 = down
		
		//	System.out.println("pos:" + position.getAbsX() + "; vel:" + velocity.getX());
		
		for(int i = 0; i < side.length; i++){
			switch(side[i]){
			case 0 :
				if(velocity.getX() < 0){
					velocity.setX(0);
				}
				break;
			case 1 :
				if(velocity.getX() > 0){
					velocity.setX(0);
				}
				break;
			case 2 :
				if(velocity.getY() < 0){
					velocity.setY(0);
				}
				break;
			case 3 :
				if(velocity.getY() > 0){
					velocity.setY(0);
				}
				break;
			}
		}
		
		position.addToLocation(velocity.getX(), velocity.getY());
				
		velocity.addX(acceleration.getX());
		velocity.addY(acceleration.getY());
		rotation += rotationRate;
		if(rotation > 359) rotation -= 360;
	}
	
	public void addConstantForce(GenericVector force){
		acceleration.addX(force.getX()/mass);
		acceleration.addY(force.getY()/mass);
	}
	
	public void getRender(Graphics g){
		g.drawPolygon(getPolygon());
	}
	
	public Rectangle getRect(){
		return new Rectangle((int) (position.getX()-radius), (int) (position.getY()-radius), (int)radius*2, (int)radius*2);
	}
	
	public Area getArea(){
		return new Area(getPolygon());
	}
	
	public Polygon getPolygon(){
		boolean flag = true;
		if(sides > 20) flag = false;
		if(sides > 2 && flag){
			
			int[] x = new int[sides];
			int[] y = new int[sides];
			
			float radianRotation = (float) (rotation * Math.PI / 180);
			float deltaAngle = (float) (Math.PI * 2 / sides);
			float angle = 0;
			
			for(int i = 0; i < sides; i++){
				angle = deltaAngle * i; 
				x[i] = (int) ((int) position.getX() + (radius * Math.cos(angle-radianRotation)));
				y[i] = (int) ((int) position.getY() + (radius * Math.sin(angle-radianRotation)));
			}
			
			return new Polygon(x,y,sides);
			
		}
		else if(!flag){
			int[] x = new int[20];
			int[] y = new int[20];
			
			float radianRotation = (float) (rotation * Math.PI / 180);
			float deltaAngle = (float) (Math.PI * 2 / 20);
			float angle = 0;
			
			for(int i = 0; i < 20; i++){
				angle = deltaAngle * i; 
				x[i] = (int) ((int) position.getX() + (radius * Math.cos(angle-radianRotation)));
				y[i] = (int) ((int) position.getY() + (radius * Math.sin(angle-radianRotation)));
			}
			
			return new Polygon(x, y, 20);			
		}
		return null;
	}
	
	public PhysObject registerWithWindow(){
		if(sides <= 2) System.err.println("A PhysObject must have more than 2 sides");
		else objectNum = RegisteredObjects.addToObjs(this);
		return this;
	}
	
	// Just normal pythag
	public float distToPoint(Position p){
		return (float) Math.sqrt((this.position.getX() - p.getX()) * (this.position.getX() - p.getX()) + (this.position.getY() - p.getY()) * (this.position.getY() - p.getY()));
	}
	
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public PhysObject setAcceleration(GenericVector acceleration){
		this.acceleration = acceleration;
		return this;
	}
	
	public PhysObject setVelocity(GenericVector velocity){
		this.velocity = velocity;
		return this;
	}

	public Position getPosition() {
		return position;
	}

	public PhysObject setPosition(Position position) {
		this.position = position;
		return this;
	}

	public float getMass() {
		return mass;
	}

	public PhysObject setMass(float mass) {
		this.mass = mass;
		return this;
	}

	public float getRadius() {
		return radius;
	}

	public PhysObject setRadius(float radius) {
		this.radius = radius;
		return this;
	}

	public int getSides() {
		return sides;
	}

	public PhysObject setSides(int sides) {
		this.sides = sides;
		return this;
	}

	public float getRotation() {
		return rotation;
	}

	public PhysObject setRotation(int rotation) {
		this.rotation = rotation;
		return this;
	}

	public GenericVector getVelocity() {
		return velocity;
	}

	public GenericVector getAcceleration() {
		return acceleration;
	}

	public float getRotationRate() {
		return rotationRate;
	}

	public PhysObject setRotationRate(float rotationRate) {
		this.rotationRate = rotationRate;
		return this;
	}
	
	
}
