package me.hii488.objects;

import me.hii488.auxilary.Position;

public class PhysHoriWallObject extends PhysWallObject{
	
	public PhysHoriWallObject(Position p, int width){
		super(p, width, 5);
	}
	
	public PhysHoriWallObject(int x, int y, int width){
		super(x, y, width, 5);
	}
	
}
