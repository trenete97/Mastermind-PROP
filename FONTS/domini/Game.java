package domini;

public class Game {

	private int height = 0;
	private final int f;
	private final int n;
	private final int nColors;
	private boolean finished;
	private boolean victory;
	private BallCombination[] board;
	private PairInt[] matches;
	private BallCombination answer;
	private BallCombination solution;
	private AI ai;

	public Game(int n, int f, int nColors) {
		boolean gen = false;
		this.n = n;
		this.f = f;
		this.nColors = nColors;
		if(n>=5)gen = true;
		ai = new AI(n,nColors,f,gen);
		initializeBoard();
		initializeMatches();
		solution = new BallCombination(n,nColors);
		answer = new BallCombination(n,nColors);
	}

	public void setIASolution(){
		solution = ai.getSolution();
	}

	public void setHumanComb(String comb){
		solution.setCombination(comb);
	}

	public void insertComb(){
		board[height].setCombination(answer.getCombValue());
		height++;
	}

	public void AIPlay(){
		answer = ai.play();
	}

	public void humanPlay(String comb){
		answer.setCombination(comb);
	}

	private void initializeBoard(){
		board = new BallCombination[f];
		for(int i = 0; i<f;i++){
			board[i] = new BallCombination(n,nColors);

		}
	}

	private void initializeMatches(){
		matches = new PairInt[f];
		for(int i = 0; i<f;i++){
			matches[i] = new PairInt();
		}
	}

	public void compareComb(){
		int colorOk = 0;
		int posOk = 0;
		boolean equal = false;
		int foundC1[] = new int[n];
		int foundC2[] = new int[n];
		for(int i = 0; i<n;i++) {
			if (solution.getBallColor(i) == answer.getBallColor(i)) {
				posOk++;
				foundC1[i] = 2;
				foundC2[i] = 2;
			}
		}
		for(int i=0;i<n;i++){
			for (int j = 0;j<n;j++){
				if(foundC1[i] != 0)break;
				if(solution.getBallColor(i) == answer.getBallColor(j) && foundC2[j]==0){colorOk++;foundC1[i] = 1; foundC2[j] = 1;break;}
			}
		}
		if(posOk == n){victory = true;finished = true;}
		else if(height+1 == f)finished = true;
		matches[height].b = colorOk;
		matches[height].a = posOk;
		ai.answer = matches[height];
	}

	private int getColorAnswer(int n){
		return matches[n].b;
	}

	private int getPosAnswer(int n){
		return matches[n].a;
	}

	public int getHeight(){
		return height;
	}

	public String getSolution(){return solution.getCombValue();}

	public boolean isFinished(){
		return finished;
	}

	public boolean isVictorious(){
		return victory;
	}

	public String boardcomb (int h){
		return board[h].getCombValue();
	}
	public PairInt boardmatch (int h){
		return matches[h];
	}
}