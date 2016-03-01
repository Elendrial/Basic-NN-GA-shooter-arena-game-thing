package me.hii488.gameWindow;

import java.awt.Canvas;
import java.awt.Graphics;

import me.hii488.registries.RegisteredObjects;

@SuppressWarnings("serial")
public class Display extends Canvas{

	public Display(Window window) {
		setBounds(0, 0, window.width, window.height);
	}
	
	public void render(Graphics g){
		for(int i = 0; i < RegisteredObjects.getObjs().size(); i++){
			RegisteredObjects.getObjs().get(i).getRender(g);
			
//			Rectangle r = World.registeredObjects.getObjs().get(i).getRect();
//			g.drawRect(r.x, r.y, r.width, r.height);
//			g.drawString("Mid", World.registeredObjects.getObjs().get(i).getPosition().getX(), World.registeredObjects.getObjs().get(i).getPosition().getY());
			
		}
	}
	
}
