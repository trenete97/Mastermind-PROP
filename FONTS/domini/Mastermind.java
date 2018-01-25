package domini;

import com.google.gson.Gson;
import dades.ControladorPersistencia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class Mastermind {
	private ControladorPersistencia cPersistencia = new ControladorPersistencia();

	private final int RETARDED = 0;
	private final int AMATEUR = 1;
	private final int KOREAN = 2;
	private final int PERSONALITZABLE = 3;
	private int MAX_COLORS = 8; //default 8
	private int MAX_ROWS = 11; //default 11
	private int MAX_COLUMNS = 6; //default 6

	public boolean humanPlaying = true;
	private int n;	//numero de bolas
	private int f;	//numero de filas
	private int nColors; //numero de colores diferentes

	public PairInt nGuesses;
	public PairInt score;
	public PairInt minGuesses;
	private Game currentGame;
	private TestAlgorithms test;

	public int difficulty = 0;
	public int i = 0;
	public int gamesNeeded = 0;
	private String comb;

	public Mastermind() {
		load_params_from_txt();
	}

	public Mastermind(int rows, int columns, int colors, int ngames) {
		f = rows;
		n = columns;
		nColors = colors;
		if (rows == MAX_COLUMNS-2 && columns == MAX_ROWS && colors == MAX_COLORS-2 ) difficulty = RETARDED;
		else if (rows == MAX_COLUMNS-1 && columns == MAX_ROWS-1 && colors == MAX_COLORS-1) difficulty = AMATEUR;
		else if (rows == MAX_COLUMNS && columns == MAX_ROWS-2 && colors == MAX_COLORS) difficulty = KOREAN;
		else difficulty = PERSONALITZABLE;
		i = 0;
		nGuesses = new PairInt();
		score = new PairInt();
		minGuesses = new PairInt();
		minGuesses.a = 100;
		minGuesses.b = 100;
		gamesNeeded =(ngames+1)/2;
	}

	public void setSolution(String comb){
		currentGame = new Game(n, f, nColors);
		if(humanPlaying)currentGame.setIASolution();
		else currentGame.setHumanComb(comb);
	}

	public String getSolution(){return currentGame.getSolution();}

	public void doPlay(){
		if(humanPlaying) currentGame.humanPlay(comb);
		else currentGame.AIPlay();
		currentGame.compareComb();
		currentGame.insertComb();
	}

	public boolean endedGame(){
		return currentGame.isFinished();
	}

	public void saveSetStats(){
		if(nGuesses.a < nGuesses.b){
			score.a++;
			if(nGuesses.a < minGuesses.a)minGuesses.setA(nGuesses.a);
		}
		else if(nGuesses.a > nGuesses.b){
			score.b++;
			if(nGuesses.b < minGuesses.b)minGuesses.setB(nGuesses.b);
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

	public void save_score_to_txt(String player){
		cPersistencia.addScore(player, Math.min(minGuesses.a,minGuesses.b), difficulty);
	}

	public void setGamesNeeded(int games){gamesNeeded = games;}
	public void setI(int i){this.i = i;}
	public int getGameHeight(){
		return currentGame.getHeight();
	}
	public String boardcomb(int h){
		return currentGame.boardcomb(h);
	}
	public int boardmatchPos(int h){
		return currentGame.boardmatch(h).a;
	}
	public int boardmatchCol(int h){ return currentGame.boardmatch(h).b; }
	public boolean isVictorious(){
		return currentGame.isVictorious();
	}
	public int getNumberRows() {return f;}
	public int getNumberColumns() {return n;}
	public int getNumberColors() {return nColors;}
	public String getRankingRow(int i) {
		return cPersistencia.getRankingRow(i);
	}
	public void saveSet(String stringJSON){
		cPersistencia.saveSet(stringJSON);
	}

	public String loadSet(){
        return cPersistencia.loadSet();
    }

	public void doTest(int rows, int columns, int colors, int ngames){
		test = new TestAlgorithms(rows,columns,colors,ngames);
		test.doTest();
	}
	public int getScoreGenetic() {return test.results.a;}
	public int getScoreFG() {return test.results.b;}
	public boolean savedGame () {return cPersistencia.savedGame();}
}