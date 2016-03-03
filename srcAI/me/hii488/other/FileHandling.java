package me.hii488.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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
		int i = 0;
		
		if(!new File(baseFilePath).isDirectory()){
			File dir = new File(baseFilePath);
			dir.mkdir();
		}
		
		if(runNumber == -1){
			while(!found){
				if(!new File(baseFilePath + i + generation).isFile()){
					found = true;
				}
				else{
					i++;
				}
			}
		}
		
		try {
			new FileHandling().writeToFile(baseFilePath + i + generation + "n", children);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Saved Chromosomes");
		
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Child> loadChromos(int storage, int generation){
		String baseFilePath = System.getProperty("user.dir") + "\\StoredChromosomes\\";
		try{
			return (ArrayList<Child>) new FileHandling().readFile(baseFilePath + storage + generation + "n");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
