package domini;
import java.util.Scanner;
public class Main {

	private static String name;
	public static void main(String[] args) {
		System.out.println("Escriu comandes:\n INTRODUEIXNOM,  CREARSETJOCS , GUARDARJOC , CONTINUARJOC , CONSULTARRANKING");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		while (!input.equals("EXIT")) {
			System.out.println("input: " + input);
			switch (input) {
				case "INTRODUEIXNOM":
					System.out.println("Introdueix nom");
					name = scanner.next();
					System.out.println("Nom registrat");
					break;
				case "CREARSETJOCS": //NOM CREAT
					System.out.println("Introdueix nombre de partides i dificultat");
					playSet(scanner.nextInt(), scanner.nextInt());
					System.out.println("Set jugat");
					break;
				case "GUARDARJOC":
					//guardar el joc
					break;
				case "CONTINUARJOC":
					//carregar partida
					break;
				case "CONSULTARRANKING":
					//Fer coses amb el ranking
					break;
				default:
					System.out.println("Entrada incorrecta");
					break;
			}
			input = scanner.next();
		}
	}
	public static void playSet(int nGames, int diff) {
		Mastermind m = new Mastermind();
		Scanner scanner = new Scanner(System.in);
		m.difficulty = diff;
		m.setGameParams(diff);
		int i = 0;
		m.nGuesses = new PairInt();
		m.score = new PairInt();
		m.minGuesses = new PairInt();
		m.minGuesses.a = 100;
		m.minGuesses.b = 100;
		String input = "";
		int gamesNeeded = (nGames+1)/2;
		while(m.score.a != gamesNeeded && m.score.b != gamesNeeded){
			m.humanPlaying = !m.humanPlaying;
			if(!m.humanPlaying){
				System.out.println("Escriu la solucio del joc");
				input = scanner.next();
			}
			m.setSolution(input);
			playGame(m);
			i++;
			if(i%2 == 0)m.saveSetStats();
			System.out.println(m.score.a + " "+ m.score.b);
		}
		m.save_params_to_txt(name);
	}
	
	private static void playGame(Mastermind m){
		Scanner scanner = new Scanner(System.in);
		while(m.endedGame() == false){
			if(m.humanPlaying){
				System.out.println("Escriu la teva guess");//test 1213
				m.setComb(scanner.next());
			}
			m.doPlay();
		}
		m.saveGameStats();
	}
	
}