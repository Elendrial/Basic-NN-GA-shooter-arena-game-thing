package me.hii488;

public class Settings {
	
	public static class WorldSettings{

		public static int TargetTPS = 1200;
		public static float currentSpeed = 1;
		
		public static int ticksPerRound = 1800;
	}
	
	
	
	// Currently unused
	public static class CameraSettings{
		
		public boolean movable = true;
		
		public static float minZoom = 0.01f;
		public static float maxZoom = 2f;
		
	}
	
}
