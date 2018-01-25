package domini;
import domini.FiveGuess;



public class AI{
	public PairInt answer;
	private boolean firstGuess = true;
	private FiveGuess fiveGuess;
	protected BallCombination guess;
	
	public AI(int n, int nColors) {
		fiveGuess = new FiveGuess(n,nColors);
		guess = new BallCombination(n,nColors);	
	}

	public BallCombination play() {
		if(firstGuess){guess = fiveGuess.getComb() ;firstGuess = false;}
		else{
			fiveGuess.lastAnswer = answer;
			guess = fiveGuess.calculateNewGuess();
		}
		return guess;
	}
	
	public BallCombination getSolution(){
		guess.setRandomCombination();
		return guess;
	}
}