package domini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class Mastermind {
	private final int RETARDED = 0;
	private final int AMATEUR = 1;
	private final int KOREAN = 2;
	private int MAX_COLORS = 9; //default 9
	private int MAX_ROWS = 11; //default 11
	private int MAX_COLUMNS = 6; //default 6
	
	public boolean humanPlaying = false;
	private int n;	//numero de bolas
	private int f;	//numero de filas
	private int nColors; //numero de colores diferentes

	public PairInt nGuesses;
	public PairInt score;
	public PairInt minGuesses;
	public Ranking ranking;
	public Game currentGame;
	
	public int difficulty = 0;
	private String comb;
	public String IAGuess;
	
	public Mastermind() {
		// TODO Auto-generated method stub
		//ranking = new Ranking();
//		load_params_from_txt();
	}

	public void setSolution(String comb){
		currentGame = new Game(n, f, nColors, humanPlaying);
		if(humanPlaying)currentGame.setIASolution();
		else currentGame.setHumanComb(comb);
	}
		
	public void doPlay(){	
		if(humanPlaying) currentGame.humanPlay(comb);
		else IAGuess = currentGame.AIPlay();
		currentGame.compareComb();
		currentGame.insertComb(); 
	}
	
	public boolean endedGame(){
		return currentGame.isFinished();
	}
	
	public void saveSetStats(){
		if(nGuesses.a < nGuesses.b){
			score.a++;
			if(nGuesses.a < minGuesses.a)minGuesses.a = nGuesses.a;
		}
		else if(nGuesses.a > nGuesses.b){
			score.b++;
			if(nGuesses.b < minGuesses.b)minGuesses.b = nGuesses.b;
		}
		nGuesses = new PairInt();
	}
	
	public void saveGameStats(){
		if(humanPlaying)nGuesses.a = currentGame.getHeight();
		else nGuesses.b = currentGame.getHeight();
	}

	public void setGameParams(int difficulty) {
		switch (difficulty) {
			case RETARDED:
				n = MAX_COLUMNS-2; f = MAX_ROWS; nColors = MAX_COLORS-2;
				break;
			case AMATEUR:
				n = MAX_COLUMNS-1; f = MAX_ROWS-1; nColors = MAX_COLORS-1;
				break;
			case KOREAN:
				n = MAX_COLUMNS; f = MAX_ROWS-2; nColors = MAX_COLORS;
				break;
		}
	}
	
	public void setComb(String comb){
		this.comb = comb;
	}
	
	private void load_params_from_txt() {
		File configtxt = new File("config.txt");
		if(configtxt.exists()){
             try{
            	 FileReader fr = new FileReader(configtxt);
		         BufferedReader br = new BufferedReader(fr);
                 String params = br.readLine();
                 String[]configGame = params.split(" ");
		         MAX_COLUMNS = Integer.parseInt(configGame[1]);
		         MAX_ROWS = Integer.parseInt(configGame[3]);
		         MAX_COLORS = Integer.parseInt(configGame[5]);	               
		         br.close();
		      }catch(IOException e){
		    	  e.printStackTrace();
	           }
	     }
	   	 else{
	   		 try{
	   			 configtxt.createNewFile();
	             PrintWriter writer = new PrintWriter(configtxt);
	             writer.print("BOARD_COLUMNS: " + MAX_COLUMNS + " BOARD_ROWS: " + 
	             MAX_ROWS + " BOARD_NCOLORS: " + MAX_COLORS + "\r\n");
	             writer.close();
	         }catch(IOException e){
	             e.printStackTrace();
	         }
	   	}
	}
	
	public void save_params_to_txt(String player){
		//ranking.addScore(player, Math.min(minGuesses.a,minGuesses.b), difficulty);
	}
}