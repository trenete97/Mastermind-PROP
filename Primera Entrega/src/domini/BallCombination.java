package domini;

import domini.Ball;
import java.util.Random;

public class BallCombination {

	private int n;
	private int nColors;
	private Ball[] combination;
	
	public BallCombination(int n, int nColors) {
		this.n = n;
		this.nColors = nColors;
		combination = new Ball[n];
		for(int i = 0;i<n;i++)combination[i] = new Ball();
	}
	
	public int getBallColor(int pos) {
		return combination[pos].getColor();
	}
	
	public void setBallColor(int pos, int col) {
		this.combination[pos].setColor(col);
	}
	
	public void setCombination(String comb){	//pre: setN executed
		for(int i = 0 ;i < n;i++){
			setBallColor(i,Character.getNumericValue(comb.charAt(i)));
		}
	}
	
	public String getCombValue(){
		String value = "";
		for(int i = 0; i < n;i++){
			value+= getBallColor(i);
		}
		return value;
	}
	
	public Ball[] getCombination() {
		return combination.clone();
	}

	public void setRandomCombination() {
		for (int i = 0; i < n; ++i) {
			combination[i].setColor((new Random()).nextInt(nColors));
		}
	}
}