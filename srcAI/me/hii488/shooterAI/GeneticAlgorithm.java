package me.hii488.shooterAI;

import java.util.ArrayList;
import java.util.Arrays;

import me.hii488.other.FileHandling;
import me.hii488.shooterAI.NeuralNetwork.Child;

public class GeneticAlgorithm {	
	
	public static ArrayList<Child> children = new ArrayList<Child>();
	
	
	
	public static void newRandomGeneration(){
		
		ArrayList<Child> childPool = new ArrayList<Child>();
		NeuralNetwork h = new NeuralNetwork();
		for(int i = 0; i < GeneralVars.numberPerGeneration; i++){
			Child c = h.new Child();
			for(int j = 0; j < c.layers.length; j++){
				for(int k = 0; k < c.layers[j].nodes.length; k++){
					for(int l = 0; l < c.layers[j].nodes[k].weights.length; l++){
						c.layers[j].nodes[k].weights[l] = GeneralVars.rand.nextFloat() - GeneralVars.rand.nextFloat();
					}
				}
			}
			childPool.add(c);
		}
		children = childPool;
	}
	
	public static ArrayList<Child> nextGeneration(){
		ArrayList<Child> childPool = new ArrayList<Child>();
		
		for(int i = 0; i < GeneralVars.numberPerGeneration; i++){
			// Finals probably not needed, but they might and I'm too lazy to test ¯\_(ツ)_/¯
			final Child parentA = rouletteChoice(children);
			final Child parentB = rouletteChoice(children);
			
			Child child = spliceChildren(parentA, parentB);
			child = mutateChild(child);
			
		}
		 // RANDOM NOTES:
		// for fitness, use (1/closest a bullet got to the enemy) * x
		// win = 100
		// loss = 0
		
		
		return childPool;
	}
	
	public static Child rouletteChoice(ArrayList<Child> children){
		
		int totalFitness = 0;
		for(int i = 0; i < children.size(); i++){
			totalFitness += children.get(i).fitness;
		}
		
		double value = GeneralVars.rand.nextDouble() * totalFitness;
		
		for(int i = 0; i < children.size(); i++){
			value -= children.get(i).fitness;
			if(value <= 0) return children.get(i);
		}
		
		return children.get(children.size()-1);
	}
	
	public static Child spliceChildren(Child a, Child b){
		Child child = a;
		
		for(int i = 0; i < child.layers.length; i++){
			for(int j = 0; j < child.layers[i].nodes.length; j++){
				final int crosspoint = GeneralVars.rand.nextInt(i == 0 ? GeneralVars.inputs : GeneralVars.nodesPerLayer[i-1]);
				for(int k = 0; k < child.layers[i].nodes[j].weights.length; k++){
					if(k >= crosspoint){
						child.layers[i].nodes[j].weights[k] = b.layers[i].nodes[j].weights[k];
					}
				}
			}
		}
		
		return child;
	}
	
	public static Child mutateChild(Child child){
		for(int i = 0; i < child.layers.length; i++){
			for(int j = 0; j < child.layers[i].nodes.length; j++){
				for(int k = 0; k < child.layers[i].nodes[j].weights.length; k++){
					if(GeneralVars.rand.nextFloat() <= GeneralVars.mutationChance){
						child.layers[i].nodes[j].weights[k] += (GeneralVars.rand.nextBoolean() == true) ? (GeneralVars.rand.nextFloat()/2) * -1 : GeneralVars.rand.nextFloat()/2;
					}
				}
			}
		}
		return child;
	}
	
	// Probably will not be needed
	public static ArrayList<Child> fitnessSortedChildren(ArrayList<Child> children2) {
		ArrayList<Child> childPool = new ArrayList<Child>();
		int size = children2.size();
		for(int i = 0; i < size; i++){
			Child c = children2.get(0);
			int index = 0;
			for(int j = 0; j < children2.size(); j++){
				if(children2.get(j).fitness > c.fitness){
					index = j;
					c = children2.get(j);
				}
			}
			childPool.add(c);
			children2.remove(index);
		}
		return childPool;
	}

	
	
	public static String[] getOutputs(float[] inputs, int child){
		
		float[] midput = recurrentOutput(inputs, child, 1);
		
		String[] s = {};
		
		for(int i = 0; i < GeneralVars.outputs.length; i++){
			if(midput[i] == 1){
				s[s.length] = GeneralVars.outputs[i];
			}
		}
		
		
		return s;
	}
	
	public static float[] recurrentOutput(float[] inputs, int child, int layer){
		float[] output = new float[GeneralVars.nodesPerLayer[layer]];
		
		for(int i = 0; i < GeneralVars.nodesPerLayer[layer]; i++){
			output[i] = children.get(child).layers[layer].nodes[i].activated(inputs);
		}
		
		return layer < GeneralVars.nodesPerLayer.length-1 ? recurrentOutput(output, child, layer+1) : output;
	}
	
	
	public static void importGeneration(int runNumber, int generation){
		FileHandling.loadChromos(runNumber, generation);
	}
	
	public static void printChild(int index){
		Child c = children.get(index);
	//	for(int i = 0; i < c.layers.length; i++){
			for(int j = 0; j < c.layers[0].nodes.length; j++){
				System.out.println(Arrays.toString(c.layers[0].nodes[j].weights));
			}
	//	}
	}
	
}
