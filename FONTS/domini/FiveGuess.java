package domini;
import java.util.ArrayList;

public class FiveGuess{
	
	private ArrayList<BallCombination> P = new ArrayList<BallCombination>();
	private ArrayList<BallCombination> S;
	public BallCombination comb;
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
			BallCombination aux = new BallCombination(n,c);
			aux.setCombination(guess);
			P.add(aux);
		}
		else{
			for(int j = 0; j < c; j++){
				generateP(i+1,guess+j);
			}
		}
	}
	
	private String scoreCombs(){
		String nextGuess = "";
		for(int i = 0; i<S.size();i++) {
			int maxHits = 0;
			int BW = 0;
			int WW = 0;
			int W = 0;
			int BWW = 0;
			int BBW = 0;
			int maxScore = 0;
			for (int j = 0; j < S.size(); j++) {
				if ("BW" == evalComb(S.get(i), S.get(j))) BW++;
				else if ("BWW" == evalComb(S.get(i), S.get(j))) BWW++;
				else if ("BBW" == evalComb(S.get(i), S.get(j))) BBW++;
				else if ("WW" == evalComb(S.get(i), S.get(j))) WW++;
				else if ("W" == evalComb(S.get(i), S.get(j))) W++;
			}
			maxHits = Math.max(Math.max(Math.max(Math.max(BW, WW), W), BBW), BWW);
			if ((S.size() - maxHits) > maxScore) {
				maxScore = S.size() - maxHits;
				nextGuess = P.get(i).getCombValue();
			}
		}
		return nextGuess;
	}
	
	private String evalComb(BallCombination guess, BallCombination last){
		int colorOk = 0;
		int posOk = 0;
		int foundC1[] = new int[n];
		int foundC2[] = new int[n];
		for(int i = 0; i<n;i++) {
			if (guess.getBallColor(i) == last.getBallColor(i)) {
				posOk++;
				foundC1[i] = 2;
				foundC2[i] = 2;
			}
		}
		for(int i=0;i<n;i++){
			for (int j = 0;j<n;j++){
				if(foundC1[i] != 0)break;
				if(guess.getBallColor(i) == last.getBallColor(j) && foundC2[j]==0){colorOk++;foundC1[i] = 1; foundC2[j] = 1;break;}
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
		String values = combValues(guess.getCombValue());
		while(i < S.size()){
			if(lastAnswer.a == 0 && lastAnswer.b == 0){
				if(notFound(S.get(i).getCombValue(),values))S.remove(i);
				i++;
			}
			else{
				if (compareCombs(guess,S.get(i)))i++;
				else S.remove(i);
			}
		}
	}

	private boolean notFound(String comb, String values){
		boolean found = false;
		for(int i = 0;i<values.length();i++)if(comb.indexOf(values.charAt(i)) != -1)return true;
		return found;
	}

	private String combValues(String comb){
		String values = "";
		for(int i = 0;i < comb.length();i++){
			if (values.indexOf(comb.charAt(i))==-1)values = values + comb.charAt(i);
		}
		return values;
	}
	
	private boolean compareCombs(BallCombination guess, BallCombination last){
		int colorOk = 0;
		int posOk = 0;
		boolean equal = false;
		int foundC1[] = new int[n];
		int foundC2[] = new int[n];
		for(int i = 0; i<n;i++) {
			if (guess.getBallColor(i) == last.getBallColor(i)) {
				posOk++;
				foundC1[i] = 2;
				foundC2[i] = 2;
			}
		}
		for(int i=0;i<n;i++){
			for (int j = 0;j<n;j++){
				if(foundC1[i] != 0)break;
				if(guess.getBallColor(i) == last.getBallColor(j) && foundC2[j]==0){colorOk++;foundC1[i] = 1; foundC2[j] = 1;break;}
			}
		}

		if(colorOk == lastAnswer.b && posOk == lastAnswer.a)equal = true;
		return equal;
	}
	public BallCombination getComb(){ return comb;}

}