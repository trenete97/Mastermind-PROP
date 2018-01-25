package domini;

import java.util.ArrayList;
import java.util.Random;

public class Genetic {
	private ArrayList<BallCombination> E;
	private ArrayList<BallCombination> pop = new ArrayList<BallCombination>();
	private PairInt[] matches;
	private BallCombination[] board;
	public BallCombination guess;
	private final int n;
	private final int c;
	private final int f;
	private PairInt p;
	private int height = 0;
	private final int max_gen = 100;
	private final int pSize = 150;
	private final int eSize = 60;

	public Genetic(int n, int c, int f){
		this.n = n;
		this.c = c;
		this.f = f;
		guess = new BallCombination(n,c);
		initializeBoard();
		initializeMatches();
		guess.setCombination(generateFirstGuess());
		setAnswer(guess);
		createPopBase();
	}

	public BallCombination calculateNewGuess(){
		height++;
		E = new ArrayList<BallCombination>();
		while(E.isEmpty())createElite();
		guess.setCombination(scoreCombs());
		setAnswer(guess);
		return guess;
	}

	private String generateFirstGuess(){
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

	private void createPopBase(){
		BallCombination aux = new BallCombination(n,c);
		for(int i=0; i<pSize;i++){
			aux.setRandomCombination();
			while(pop.contains(aux))aux.setRandomCombination();
			pop.add(new BallCombination(n,c));
			pop.get(i).setCombination(aux.getCombValue());
		}
	}

	private void createElite(){
		int gen = 0;
		boolean ok = false;
		BallCombination aux;
		int counter = 0;
		while(gen < max_gen && E.size()<eSize){
			evolvePopulation();
			for(int j = 0;j<pop.size();j++){
				for(int k = 0;k<height;k++){
					ok = compareCombs(pop.get(j),board[k],matches[k]);

					if (!ok)break;
				}
				aux = new BallCombination(n,c);
				aux.setCombination(pop.get(j).getCombValue());
				if(ok && !findElite(pop.get(j).getCombValue())){
					E.add(aux);
					counter = 0;
				}
				if(counter == 15){
					pop = new ArrayList<BallCombination>();
					createPopBase();
					counter = 0;
				}
				if(E.size() == pop.size())break;
			}
			counter++;
			gen++;
		}
	}

	private String scoreCombs(){
		String nextGuess = "";
		for(int i = 0; i<E.size();i++){
			int maxHits = 0;
			int BW = 0;
			int WW = 0;
			int W = 0;
			int BWW = 0;
			int BBW = 0;
			int maxScore = 0;
			for(int j = 0; j < E.size(); j++){
				if(E.get(i) != E.get(j)){
					if("BW" == evalComb(E.get(i),E.get(j))) BW++;
					else if("BWW" == evalComb(E.get(i),E.get(j))) BWW++;
					else if("BBW" == evalComb(E.get(i),E.get(j))) BBW++;
					else if("WW" == evalComb(E.get(i),E.get(j))) WW++;
					else if("W" == evalComb(E.get(i),E.get(j))) W++;
				}
			}
			maxHits = Math.max(Math.max(Math.max(Math.max(BW, WW), W), BBW), BWW);
			if((E.size() - maxHits) > maxScore && E.contains(E.get(i))){maxScore = E.size() - maxHits; nextGuess = E.get(i).getCombValue();}
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

	private boolean compareCombs(BallCombination guess, BallCombination last, PairInt answer){
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
		if(colorOk == answer.b && posOk == answer.a)equal = true;

		return equal;
	}

	private void evolvePopulation(){
		for(int i=0;i<pop.size();i=i+2){
			if(new java.util.Random().nextInt(5)==1)cross1(i);
			else cross2(i);
			if(new java.util.Random().nextInt(300)==1)permutation(i);
			if(new java.util.Random().nextInt(300)==1)mutation(i);
			if(new java.util.Random().nextInt(200)==1)inversion(i);
			if(i!=150){
				if(new java.util.Random().nextInt(300)==1)permutation(i+1);
				if(new java.util.Random().nextInt(300)==1)mutation(i+1);
				if(new java.util.Random().nextInt(200)==1)inversion(i+1);
			}
		}
	}

	private void cross1(int e){
		int p = new Random().nextInt(n);
		for(int i=0; i<=p;i++)swapBalls(e, e+1, i);
	}

	private void cross2(int e){
		int p1 = new Random().nextInt(n);
		int p2 = new Random().nextInt(n);
		p = new PairInt();
		while(p1 == p2)p2 = new Random().nextInt(n);
		p.a = p1;
		p.b = p2;
		if(p1 > p2)swapPos();
		for(int i=p.a; i<=p.b;i++)swapBalls(e, e+1, i);
	}

	private void inversion(int e){
		int p1 = new Random().nextInt(n);
		int p2 = new Random().nextInt(n);
		while(p1 == p2)p2 = new Random().nextInt(n);
		p.a = p1;
		p.b = p2;
		if(p1 > p2)swapPos();
		int[] aux = new int[p.b-p.a+1];
		for(int i=0; i<aux.length;i++)aux[i] = pop.get(e).getBallColor(i);
		for(int i=p.a; i<=p.b;i++)pop.get(e).setBallColor(p.b-i,aux[i-p.a]);
	}

	private void permutation(int e){
		int p1 = new Random().nextInt(n);
		int p2 = new Random().nextInt(n);
		while(p1 == p2)p2 = new Random().nextInt(n);
		int aux = 0;
		aux = pop.get(e).getBallColor(p1);
		pop.get(e).setBallColor(p1, pop.get(e).getBallColor(p2));
		pop.get(e).setBallColor(p2, aux);
	}

	private void mutation(int e){
		int p = new Random().nextInt(c);
		int pos = new Random().nextInt(n);
		while(p == pop.get(e).getBallColor(pos))p = new Random().nextInt(c);
		pop.get(e).setBallColor(pos,p);
	}

	private void swapPos(){
		int aux = p.a;
		p.a = p.b;
		p.b = aux;
	}

	private void swapBalls(int i1, int i2,int pos){
		int aux = pop.get(i1).getBallColor(pos);
		pop.get(i1).setBallColor(pos, pop.get(i2).getBallColor(pos));
		pop.get(i2).setBallColor(pos, aux);
	}

	private boolean findElite(String comb){
		for(int i = 0;i<E.size();i++){
			if(E.get(i).getCombValue().equals(comb))return true;
		}
		return false;
	}

	public void setMatch(PairInt match){
		matches[height].setA(match.a);
		matches[height].setB(match.b);
	}

	public void setAnswer(BallCombination answer){
		board[height].setCombination(answer.getCombValue());
	}

	public BallCombination getComb(){ return guess;}

	private void initializeMatches(){
		matches = new PairInt[f];
		for(int i = 0; i<f;i++){
			matches[i] = new PairInt();
		}
	}

	private void initializeBoard(){
		board = new BallCombination[f];
		for(int i = 0; i<f;i++){
			board[i] = new BallCombination(n,c);
		}
	}
}