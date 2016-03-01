package me.hii488.objects;

import me.hii488.auxilary.Position;

public class PhysVertWallObject extends PhysWallObject{
	
	public PhysVertWallObject(Position p, int height){
		super(p, 5, height);
	}
	
	public PhysVertWallObject(int x, int y, int height){
		super(x, y, 5, height);
	}
	
}
