package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

import controller.TwoPlayersGameController;

public class Part {
	
	
	
	private int dimention;
	private String[][] matrix = new String[dimention][dimention];
	private boolean turnX;
	private Formatter x;
	private Scanner s;
	

	public Part() {
		
	}
	
	public Part(int dimention , String[][] matrix , boolean turnX) {
		
	     this.dimention = dimention;
	     this.matrix = matrix;
	
	}
	
	public boolean getTurnX(){
		return turnX;
	}
	
	public String[][] getMatrix(){
		return matrix;
	}
	
	public int getDimention(){
		return this.dimention;
	}
	/*--------methodes ajout d'une partie dans un fichier--------*/
	
	private void openFile(){
		try{
		   x = new Formatter("part.txt");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void addRecords(){
		x.format("%d ", dimention);
		x.format("%b ", TwoPlayersGameController.turnX);
		x.format("\n");
		
		for(int i=0 ; i<this.dimention ; i++){
			for(int j=0 ; j<this.dimention ; j++){
				x.format("%s ", matrix[j][i]);
			}
			x.format("\n");
		}
		
		
	
	}
	
	private void closeFile(){
		x.close();
	}
	
	// fonction appel les trois fonction précédante
	
	public void newPart(){
		openFile();
		addRecords();
		closeFile();
	}
	
	/*---------------------------------------*/
	
	
	/*--------methodes d'ajout d'une partie dans un fichier --------*/
	
	private void openFileInReading(){
		try {
			s = new Scanner(new File("part.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private void readFile(String tab[][]){
		
		     this.dimention = s.nextInt();
		     this.turnX = s.nextBoolean();
		
			for (int i = 0; i < tab.length; i++) {
				for (int j = 0; j < tab.length; j++) {
					tab[i][j]= s.next();
					
				}
		
			}
			   
	}
				 	
	
	private void closeFileInReading(){
		s.close();
	}
	
	// fonction appel les trois fonction précédante
	public void getPart(String tab[][]){
		openFileInReading();
		readFile(tab);
		closeFileInReading();
	}
	
	public void readDimention(){
		openFileInReading();
		this.dimention = s.nextInt();
		closeFileInReading();
	}
	
	/*-----------------------------------------------*/
	
/*	public static void main(String[] args) {
		String [][] yousra = new String[3][3] ;
		
		
	    
		
		//new Part(4 , yousra).newPart();
		new Part().getPart();
		
		 for (int i = 0; i < yousra.length; i++) {
			for (int j = 0; j < yousra.length; j++) {
				System.out.print(yousra[i][j] + " ");
			}
			System.out.println();
		}
		
	}
*/
}
