package me.hii488.shooterAI;

//Hierarchy : child -> layers -> nodes -> node-weights
public class NeuralNetwork {
	public class Child{
		public Layer[] layers;
		public int fitness = 0;
		
		public Child(){
			layers = new Layer[GeneralVars.nodesPerLayer.length];
			for(int i = 0; i < layers.length; i++){
				layers[i] = new Layer(i);
			}
		}
		
		public void calculateFitness(int bulletsOnTarget, float accuracy, int timeSurvived){
			
		}
		
	}
	
	public class Layer{
		public Node[] nodes;
		public final int layerLevel;
		
		public Layer(int level){
			layerLevel = level;
			
			nodes = new Node[GeneralVars.nodesPerLayer[level]];
			for(int i = 0; i < nodes.length; i++){
				nodes[i] = new Node(layerLevel, i);
			}
		}
		
	}
	
	public class Node{
		public float[] weights;
		public final int nodeNumber;
		
		public Node(int level, int number){
			
			nodeNumber = number;
			
			weights = new float[(level != 0 ? GeneralVars.nodesPerLayer[level-1] : 100) + 1];
		}
		
		
		// Checks if the node will output a 0 or 1
		public float activated(float[] inputs){
			int temp = 0;
			
			for(int i = 0; i < inputs.length; i++){
				temp += inputs[i] * weights[i];
			}
			
			// Sigmoid function
			return (float) (1/(1 + Math.pow(Math.E, -temp)));
		}
	}
}
