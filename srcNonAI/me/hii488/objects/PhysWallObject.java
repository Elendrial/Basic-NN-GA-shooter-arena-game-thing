package me.hii488.objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import me.hii488.auxilary.GenericVector;
import me.hii488.auxilary.Position;

public class PhysWallObject extends PhysObject{
	
	public int height;
	public int width;
	
	public PhysWallObject(Position position, float mass, int width, int height){
		this.position = position;
		this.velocity = new GenericVector(0f,0f);
		this.acceleration = new GenericVector(0f,0f);
		this.mass = mass;
		this.width = width;
		this.height = height;
		
		
		this.sides = 4;
		this.radius = 0;
	}
	
	public PhysWallObject(Position position, int width, int height){
		this(position, 0, width, height);
	}
	
	public PhysWallObject(int x, int y, int width, int height){
		this(new Position(x, y), width, height);
	}
	
	public PhysWallObject(){
		this(0,0,1,1);
	}
	
	
	@Override
	public Rectangle getRect(){
		return new Rectangle((int) (position.getX()), (int) (position.getY()), width, height);
	}
	
	@Override
	public void getRender(Graphics g){
		g.drawRect((int) (position.getX()), (int) (position.getY()), width, height);
	}
	
	
}
