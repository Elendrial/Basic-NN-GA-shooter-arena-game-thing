package me.hii488.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import me.hii488.shooterAI.AIController;
import me.hii488.shooterAI.GeneticAlgorithm;
import me.hii488.shooterAI.NeuralNetwork;
import me.hii488.shooterAI.NeuralNetwork.Child;

@SuppressWarnings("serial")
public class FileHandling implements Serializable{
	
	public void writeToFile(String fileName, Object o) throws Exception{
		FileOutputStream f = new FileOutputStream(fileName);
	    ObjectOutput s = new ObjectOutputStream(f);
	    
	    
	    s.writeObject(o);
	    
	    
	    f.close();
	    s.close();
		
	}
	
	public Object readFile(String filename) throws Exception{
		FileInputStream in = new FileInputStream(filename);
	    ObjectInputStream s = new ObjectInputStream(in);
		
	    Object o;
	    /*
	    try{
	    	for(int i = 0; i < 2 ; i++){
	    		o[i] = s.readObject();
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    */
	    
	    o = s.readObject();
	    s.close();
		return o;
	}

	public static void saveGeneration(int runNumber, int generation, ArrayList<Child> children) {
		String baseFilePath = System.getProperty("user.dir") + "\\StoredChromosomes\\";
		boolean found = false;
		int i = runNumber;
		
		if(!new File(baseFilePath).isDirectory()){
			File dir = new File(baseFilePath);
			dir.mkdir();
		}
		
		if(runNumber == -1){
			i = 0;
			while(!found){
				//System.out.println("in while");
				if(!new File(baseFilePath + i + "x" + generation).isDirectory()){
					found = true;
					new File(baseFilePath + i + "x" + generation).mkdir();
					AIController.runNumber = i;
				}
				else{
					i++;
				}
			}
		}
		
		if(!new File(baseFilePath + i + "x" + generation).isDirectory()){
			found = true;
			new File(baseFilePath + i + "x" + generation).mkdir();
			AIController.runNumber = i;
		}
		
		try {
			for(int j = 0; j < children.size(); j++){
				for(int k = 0; k < children.get(j).layers.length; k++){
					for(int l = 0; l < children.get(j).layers[k].nodes.length; l++){
						new FileHandling().writeToFile(baseFilePath + i + "x" + generation + "\\c" + j + "l" + k + "n" + l, children.get(j).layers[k].nodes[l].weights);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Saved Chromosomes");
		
	}
	
	
	public static ArrayList<Child> loadChromos(int runNumber, int generation){
		String baseFilePath = System.getProperty("user.dir") + "\\StoredChromosomes\\";
		ArrayList<Child> children = new ArrayList<Child>();
		
		if(runNumber == -1){
			boolean found = false;
			while(!found){
				if(!new File(baseFilePath + runNumber + "x" + generation).isDirectory()){
					found = true;
					new File(baseFilePath + runNumber + "x" + generation).mkdir();
					runNumber--;
				}
				else{
					runNumber++;
				}
			}
		}
		
		if(generation == -1){
			boolean found = false;
			while(!found){
				if(!new File(baseFilePath + runNumber + "x" + generation).isDirectory()){
					found = true;
					new File(baseFilePath + runNumber + "x" + generation).mkdir();
					generation--;
				}
				else{
					generation++;
				}
			}
		}
		
		
		
		try{
			
			for(int i = 0; i < GeneticAlgorithm.children.size(); i++){
				Child c = (new NeuralNetwork()).new Child();
				for(int j = 0; j < GeneticAlgorithm.children.get(i).layers.length; j++){
					for(int k = 0; k < GeneticAlgorithm.children.get(i).layers[j].nodes.length; k++){
						c.layers[j].nodes[k].weights = (float[]) (new FileHandling().readFile(baseFilePath + runNumber + "x" + generation + "\\c" + i + "l" + j + "n" + k));
					}
				}
				children.add(c);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		AIController.generation = generation;
		
		return children;
	}
	
}
