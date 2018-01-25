package dades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Ranking {
	private static final int MAXSCORES = 10;
	private ArrayList<String> players;
	private ArrayList<Integer> minScores;
	private ArrayList<Integer> diff;
	private File ranking;

	public Ranking(){
		players = new ArrayList<String>();
		minScores = new ArrayList<Integer>();
		diff = new ArrayList<Integer>();
		String score;
		ranking = new File("ranking.txt");
		String [] scoreInfo;
		if(ranking.exists()){
			try{
				FileReader fr = new FileReader(ranking);
				BufferedReader br = new BufferedReader(fr);
				score = br.readLine();
				for(int i = 0; i < MAXSCORES;i++){
					score = br.readLine();
					scoreInfo = score.split(" ");
					players.add(scoreInfo[0]);
					minScores.add(Integer.parseInt(scoreInfo[2]));
					diff.add(Integer.parseInt(scoreInfo[4]));

				}
				br.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		else{
			try{
				ranking.createNewFile();
				initNewRanking();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	private void initNewRanking() throws IOException{
		ranking = new File("ranking.txt");
		PrintWriter writer = new PrintWriter(ranking);
		writer.print("PLAYER" + " | " + "MIN.GUESSES" + " | " + "DIFF"+"\r\n");
		for(int i = 0; i < MAXSCORES; i++){
			writer.print((char)(i+65) + " | " + "11" + " | " + "0"+"\r\n");
		}
		writer.close();

		FileReader fr = new FileReader(ranking);
		BufferedReader br = new BufferedReader(fr);
		String score = br.readLine();
		String[] scoreInfo;
		for(int i = 0; i < MAXSCORES;i++){
			score = br.readLine();
			scoreInfo = score.split(" ");
			players.add(scoreInfo[0]);
			minScores.add(Integer.parseInt(scoreInfo[2]));
			diff.add(Integer.parseInt(scoreInfo[4]));
		}
		br.close();
	}

	private void fillRanking(){
		try{
			PrintWriter writer = new PrintWriter(ranking);
			writer.print("");
			writer.print("PLAYER" + " | " + "MIN.GUESSES" + " | " + "DIFF"+"\r\n");
			for(int i = 0; i < MAXSCORES; ++i){
				writer.print(players.get(i) + " | " + String.valueOf(minScores.get(i) + " | " + String.valueOf(diff.get(i)))+"\r\n");
			}
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void addScore(String name, int minScore, int difficulty){
		for(int i = 0; i < MAXSCORES;i++){
			if(minScores.get(i)>minScore){
				players.add(i,name); minScores.add(i,minScore); diff.add(i,difficulty);
				if(players.size() > MAXSCORES)players.remove(MAXSCORES-1); minScores.remove(MAXSCORES-1); diff.remove(MAXSCORES-1);
				break;
			}
		}
		fillRanking();
	}
	public String getRankingRow(int i){
		return players.get(i) + " " + minScores.get(i) + " " + diff.get(i);
	}
}