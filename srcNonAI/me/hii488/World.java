package me.hii488;

public class World {
	
	// I don't know why this was here, moved to separate class now. Can be deleted (probably)
	
/*	public static class registeredObjects{
		private static ArrayList<PhysObject> objs = new ArrayList<>();

		public static ArrayList<PhysObject> getObjs() {
			return objs;
		}
	
		public static void setObjs(ArrayList<PhysObject> objs) {
			World.registeredObjects.objs = objs;
		}
		
		public static int addToObjs(PhysObject objs) {
			World.registeredObjects.objs.add(objs);
			return World.registeredObjects.objs.indexOf(objs);
		}
		
		public static void removeAt(int i){
			World.registeredObjects.objs.remove(i);
		}
		
		public static void removeAt(int[] i){
			for(int j = 0; j < i.length; j++){
				World.registeredObjects.objs.remove(i[j]);
			}
		}
		
		public static int findExactMatch(PhysObject p){
			return objs.indexOf(p);
		}
	}
	*/
	
	
	public static class Camera{
		public static int zoom = 1;
		
		public static int x = 0;
		public static int y = 0;
		
		public static void left(){
			x -= 10 * (1/zoom);
		}
		
		public static void right(){
			x += 10 * (1/zoom);
		}
		
	}
	
}
