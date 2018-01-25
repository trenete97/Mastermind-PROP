package domini;

public class AI{
	public PairInt answer;
	private boolean firstGuess = true;
	private FiveGuess fiveGuess;
	private Genetic genetic;
	protected BallCombination guess;
	private int n;
	private boolean gen;
	
	public AI(int n, int nColors,int f,boolean gen){
		this.n = n;
		this.gen = gen;
		fiveGuess = new FiveGuess(n,nColors);
		guess = new BallCombination(n,nColors);
		genetic = new Genetic(n,nColors,f);
	}

	public BallCombination play() {
		if(!gen){
			if(firstGuess){guess = fiveGuess.getComb(); firstGuess = false;}
			else{
				fiveGuess.lastAnswer = answer;
				guess = fiveGuess.calculateNewGuess();
			}
			return guess;
		}
		else{
			if(firstGuess){guess = genetic.getComb(); firstGuess = false;}
			else{
				genetic.setMatch(answer);
				guess = genetic.calculateNewGuess();
			}
			return guess;
		}
	}
	
	public BallCombination getSolution(){
		guess.setRandomCombination();
		return guess;
	}
}