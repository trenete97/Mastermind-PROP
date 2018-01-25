package domini;
import domini.AI;
import domini.BallCombination;

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
	private boolean humanPlaying;
	private AI ai;
	
	public Game(int n, int f, int nColors, boolean humanPlaying) {
		this.n = n;
		this.f = f;
		this.nColors = nColors;
		this.humanPlaying = humanPlaying;
		ai = new AI(n,nColors);
		initializeBoard();
		initializeMatches();
		solution = new BallCombination(n,nColors);
		answer = new BallCombination(n,nColors);
	}

	public void setIASolution(){
		 solution = ai.getSolution();
	}
	
	public void setHumanComb(String comb){
		 this.solution.setCombination(comb);
	}
	
	public void insertComb(){
		board[height] = solution;
		height++;
	}
	
	public String AIPlay(){
		answer = ai.play();
		return answer.getCombValue();
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
		boolean found[] = new boolean[n];
		for(int i = 0; i<n;i++){
			if(solution.getBallColor(i) == answer.getBallColor(i)){
				posOk++;
				if(found[i] == true)colorOk--;	//Ex 3231 // 4231 (el 3 el troba primer per color i despres per posicio)
				found[i] = true;
				}
			else{
                boolean trobat=false;
                for (int j = 0;j<n;++j){
                    if(solution.getBallColor(i) == answer.getBallColor(j) && !found[j]){trobat = true; colorOk++; found[j] = true;}
                    if(trobat)break;
                }
			}
		}
		if(posOk == n){victory = true;finished = true;}
		else if(height+1 == f)finished = true;
		matches[height].b = colorOk;
		matches[height].a = posOk;
		ai.answer = matches[height];
		System.out.println("Blanques: " + getPosAnswer(height) + " Negres:" + getColorAnswer(height));
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
	
	public boolean isFinished(){
		return finished;
	}
	
	public boolean isVictorious(){
		return victory;
	}
}