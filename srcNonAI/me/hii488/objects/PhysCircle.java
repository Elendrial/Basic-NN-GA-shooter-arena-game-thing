package me.hii488.objects;

import java.awt.Graphics;

import me.hii488.auxilary.GenericVector;
import me.hii488.auxilary.Position;
import me.hii488.registries.RegisteredObjects;

public class PhysCircle extends PhysObject{
	
	public PhysCircle(Position position, GenericVector velocity, GenericVector acceleration, float mass, float radius) {
		super(position, velocity, acceleration, mass, 0, radius);
	}
	
	public PhysCircle(Position position, float mass, float radius){
		this(position, new GenericVector(0,0), new GenericVector(0,0), mass, radius);
	}

	public PhysCircle(int x, int y, float mass, float radius){
		this(new Position(x, y), mass, radius);
	}
	
	public PhysCircle() {
		this(0,0,0,1);
	}
	
	@Override
	public PhysObject setSides(int sides) {
		System.err.println("Cannot change amount of PhysCircle sides");
		return this;
	}
	
	@Override
	public void getRender(Graphics g){
		g.drawOval((int)(position.getX()-radius),(int)( position.getY()-radius), (int)Math.floor(radius*2), (int)Math.floor(radius*2));
	}
	
	@Override
	public PhysObject registerWithWindow(){
		objectNum = RegisteredObjects.addToObjs(this);
		return this;
	}
	
}
