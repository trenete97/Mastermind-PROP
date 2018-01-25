package domini;

public class TestAlgorithms {

    public static Genetic G;
    public static FiveGuess F;
    public static String solution;
    public static PairInt answer = new PairInt();
    public static PairInt results = new PairInt();
    static PairInt score = new PairInt();
    public static String guess;
    static BallCombination aux;
    static boolean geneticPlaying = true;
    static boolean win;
    static int nRounds;
    static int n;
    static int c;
    static int f;

    public TestAlgorithms(int rows, int columns, int colors, int ngames) {
        f = rows;
        n = columns;
        c = colors;
        nRounds =(ngames+1)/2;
        score.setA(0);
        score.setB(0);
        results.setA(0);
        results.setB(0);
    }

    public void doTest(){

        aux = new BallCombination(n,c);

        while(nRounds > results.a && nRounds > results.b){
            G = new Genetic(n,c,f);
            F = new FiveGuess(n,c);
            aux.setRandomCombination();
            solution = aux.getCombValue();
            int n = 1;
            win = false;
            if(geneticPlaying)guess = G.guess.getCombValue();
            else guess = F.comb.getCombValue();
            System.out.println(guess);
            while(!win){
                compareComb();
                if(geneticPlaying)guess = G.calculateNewGuess().getCombValue();
                else guess = F.calculateNewGuess().getCombValue();
                n++;
                if(solution.equals(guess)){
                    win = true;
                    if(geneticPlaying)score.setA(n);
                    else score.setB(n);
                    geneticPlaying = !geneticPlaying;
                }
            }
            if(score.a != 0 && score.b != 0){
                if(score.a < score.b)results.setA(results.a+1);
                else if(score.a > score.b)results.setB(results.b+1);
                score.setA(0);
                score.setB(0);
            }
        }
    }

    public static void compareComb(){
        int colorOk = 0;
        int posOk = 0;
        int foundC1[] = new int[n];
        int foundC2[] = new int[n];
        for(int i = 0; i<n;i++) {
            if (solution.charAt(i) == guess.charAt(i)) {
                posOk++;
                foundC1[i] = 2;
                foundC2[i] = 2;
            }
        }
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                if (foundC1[i] != 0) break;
                if (solution.charAt(i) == guess.charAt(j) && foundC2[j]==0){colorOk++;foundC1[i] = 1; foundC2[j] = 1;break;}
            }
        }
        answer.setB(colorOk);
        answer.setA(posOk);
        if(geneticPlaying)G.setMatch(answer);
        else F.lastAnswer = answer;
    }
}