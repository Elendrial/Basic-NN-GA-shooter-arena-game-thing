package me.hii488.shooterAI;

import java.util.Random;

public class GeneralVars {
	
	//  --------   GENERAL   ---------- //
	public static Random rand = new Random();
	
	// Does NOT include input "layer" - must end with outputs.length or greater
	public static final int[] nodesPerLayer = {100, 50, 30, 30, 10, 7};
	
	// Must be a square number (aka x^2)
	public static final int inputs = 100;
	
	// m = move, r = rotate, l/r/u/d = directions, s = shoot
	public static final String[] outputs = {"ml", "mr", "mu", "md", "rl", "rr", "s"};
	
	
	
	
	
	//  -------  GENETIC VARS  ---------  //
	public static final int numberPerGeneration = 20;
	public static final int numberTopChromosome = 10;
	public static final float mutationChance = 0.1f;
	
	
}
