package me.hii488.auxilary;

public class GenericVector {
	private float dx;
	private float dy;
	
	public GenericVector(){
		this(0,0);
	}
	
	public GenericVector(float dx, float dy){
		this.setX(dx);
		this.setY(dy);
	}

	public float getY() {
		return dy;
	}

	public void setY(float dy) {
		this.dy = dy;
	}

	public float getX() {
		return dx;
	}

	public void setX(float dx) {
		this.dx = dx;
	}
	
	
	public void addX(float f){
		this.dx += f;
	}
	
	public void addY(float y){
		this.dy += y;
	}
	
	
}
