package domini;
import java.util.ArrayList;

import domini.PairInt;

public class FiveGuess{
	
	private ArrayList<BallCombination> P = new ArrayList<BallCombination>();
	private ArrayList<BallCombination> S;
	private BallCombination comb;
	private static int n;
	private int c;
	public PairInt lastAnswer;
	
	public FiveGuess(int n,int c) {
		this.n = n;
		this.c = c;
		lastAnswer = new PairInt();
		comb = new BallCombination(n,c);
		generateP(0,"");
		comb.setCombination(generateFirstGuess());
		S = new ArrayList<BallCombination>(P);
	}
	
	public BallCombination calculateNewGuess(){
		reducePopulationS(comb);		//OK
		System.out.println("Comb = " + scoreCombs());
		comb.setCombination(scoreCombs());
		P = new ArrayList<BallCombination>(S);
		return comb;
	}
	
	private static String generateFirstGuess(){
		int i = 0;
		int bola = 0;
		String guess = "";
		while(bola<n){
			guess+=i;
			if(bola%2 == 1)i++;
			bola++;
		}
		return guess;
	}

	private void generateP(int i,String guess){
		if(i==n){
			comb = new BallCombination(n,c);
			comb.setCombination(guess);
			P.add(comb);
		}
		else{
			for(int j = 0; j < c; j++){
				generateP(i+1,guess+j);
			}
		}
	}
	
	private String scoreCombs(){
		String nextGuess = "";
		for(int i = 0; i<P.size();i++){
			int maxHits = 0;
			int BW = 0;
			int WW = 0;
			int W = 0;
			int BWW = 0;
			int BBW = 0;
			int maxScore = 0;
			for(int j = 0; j < S.size(); j++){
				if("BW" == evalComb(P.get(i),S.get(j))) BW++;
				else if("BWW" == evalComb(P.get(i),S.get(j))) BWW++;
				else if("BBW" == evalComb(P.get(i),S.get(j))) BBW++;
				else if("WW" == evalComb(P.get(i),S.get(j))) WW++;
				else if("W" == evalComb(P.get(i),S.get(j))) W++;
			}
			maxHits = Math.max(Math.max(Math.max(Math.max(BW, WW), W), BBW), BWW);
			if((S.size() - maxHits) > maxScore && S.contains(P.get(i))){maxScore = S.size() - maxHits; nextGuess = P.get(i).getCombValue();}
		}
		return nextGuess;
	}
	
	private String evalComb(BallCombination guess, BallCombination last){
		int colorOk = 0;
		int posOk = 0;
		for(int i = 0; i<n;i++){
			if(guess.getBallColor(i) == last.getBallColor(i))posOk++;
			else{
				boolean trobat=false;
				for (int j = 0;j<n && !trobat;++j){
					if(guess.getBallColor(i) == last.getBallColor(j) && i != j){trobat = true; colorOk++;}
				}
			}
		}
		if(colorOk == 1 && posOk == 1)return "BW" ;
		else if(colorOk == 1 && posOk == 2)return "BWW";
		else if(colorOk == 2 && posOk == 1)return "BBW";
		else if(posOk == 2)return "WW";
		else if(posOk == 1)return "W";
		else return "";
	}
	
	private void reducePopulationS(BallCombination guess){
		int i = 0;
		while(i < S.size()){
			if (compareCombs(guess,S.get(i)))i++;
			else	S.remove(i);
		}
	}
	
	private boolean compareCombs(BallCombination guess, BallCombination last){
		int colorOk = 0;
		int posOk = 0;
		boolean equal = false;
		boolean found[] = new boolean[n];
		for(int i = 0; i<n;i++){
			if(guess.getBallColor(i) == last.getBallColor(i)){
				posOk++;
				if(found[i] == true)colorOk--;	//Ex 3231 // 4231 (el 3 el troba primer per color i despres per posicio)
				found[i] = true;
			}
			else{
				boolean trobat=false;
				for (int j = 0;j<n;++j){
					if(guess.getBallColor(i) == last.getBallColor(j) && !found[j]){trobat = true; colorOk++; found[j] = true;}
					if(trobat)break;
				}
			}
		}
		if(colorOk == lastAnswer.b && posOk == lastAnswer.a)equal = true;
		return equal;
	}
	public BallCombination getComb(){ return comb;}
}
