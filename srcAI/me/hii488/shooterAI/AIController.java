package me.hii488.shooterAI;

import me.hii488.registries.StartingObjectRegistry;

public class AIController {
	
	public static int[] currentPositions;
	public static int amount;
	
	public static void setupAI(int AIs){
		GeneticAlgorithm.newRandomGeneration();
		
		StartingObjectRegistry.makeArena(AIs);
		
		currentPositions = new int[AIs];
		
		amount = AIs;
		
		// Setup initial positions, so they start in the right place.
		for(int i = 0; i < amount; i++){
			currentPositions[i] = i;
		}
	}
	
	public static void updateChildren(){
		currentPositions[currentPositions.length-1] += 1;
		
		// For correct incrementation of which Chromosomes will active at once
		// Incrementation is a word I swear
		for(int i = 0; i < amount-1; i++){
			if(currentPositions[currentPositions.length-1 - i] >= GeneticAlgorithm.children.size()-i){
				currentPositions[currentPositions.length-1 - i] = -1;
				currentPositions[currentPositions.length-2 - i] += 1;
			}
		}
		
		if(currentPositions[0] == -1){
			//TODO : This, get it done, do it, go on you know you want to.......
			//TODO : I can't remember what I'm supposed to do oh god what will I do? help ;-; (it's probably to do with fitness)
			
			GeneticAlgorithm.nextGeneration();
			
			return;
		}
		
		for(int i = 1; i < amount; i++){
			if(currentPositions[i] == -1){
				currentPositions[i] = currentPositions[i-1]+1;
			}
		}
		
	}
	
}
