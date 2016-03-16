package me.hii488.shooterAI;

import java.util.HashMap;

import me.hii488.other.FileHandling;
import me.hii488.registries.StartingObjectRegistry;

public class AIController {
	
	public static int[] currentPositions;
	public static int amount;
	public static int generation = 0;
	public static int iteration = 0;
	
	public static void setupAI(int AIs){
		// TODO : Do all the stuff that's needed to remove this line
		if(AIs > 4) AIs = 4;
		
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
		iteration++;
		System.out.println("\n\tGeneration: " + generation + "\tIteration: " + iteration);
		currentPositions[currentPositions.length-1] += 1;
		
		// For correct incrementation of which Chromosomes will active at once
		// Incrementation is a word I swear
		for(int i = 0; i < amount-1; i++){
			if(currentPositions[currentPositions.length-1 - i] >= GeneticAlgorithm.children.size()-i){
				currentPositions[currentPositions.length-1 - i] = -1;
				currentPositions[currentPositions.length-2 - i] += 1;
			}
		}
		
		// Updates generation.
		if(currentPositions[0] == -1){
			FileHandling.saveGeneration(-1, generation, GeneticAlgorithm.children);
			GeneticAlgorithm.nextGeneration();
			generation++;
			return;
		}
		
		for(int i = 1; i < amount; i++){
			if(currentPositions[i] == -1){
				currentPositions[i] = currentPositions[i-1]+1;
			}
		}
		
	}
	
	public void printFitnessStandings(){
		HashMap<Integer, Integer> ranks = new HashMap<>();
		
		// TODO : This. Not necessary but would be nice.
	}
	
}
