package drivers;

import java.util.Scanner;

import domini.Game;

public class GameDriver {

	public static void main(String[] args) {

		System.out.println("Escriu comandes per controlar la classe Game:\nCREATEGAME , SETIASOLUTION , SETHUMANCOMB , INSERTCOMB , AIPLAY , HUMANPLAY , COMPARECOMB , GETHEIGHT , ISVICTORIOUS , ISFINISHED\n");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		Game g = null;
		int f = 0;
		int h = 0;
		while (!input.equals("EXIT")) {
			System.out.println("input: "+input);
			switch (input) {
				case "CREATEGAME":
					System.out.println("Introdueix numero de boles, files, nombre de colors i si l'huma esta jugant(true/false)");
					int boles = scanner.nextInt();
					f = scanner.nextInt();
					g = new Game(boles,f,scanner.nextInt(),scanner.nextBoolean());
					h = 0;
					System.out.println("Game creat!");
					break;
				case "SETIASOLUTION":
					if (g!= null) {
						g.setIASolution();
						System.out.println("Solució posada");
					}
					else System.out.println("Game no esta creat encara");
					break;
				case "SETHUMANCOMB":
					if (g!= null) {
						System.out.println("Introdueix solucio (numeros sense espais)");
						g.setHumanComb(scanner.next());
						System.out.println("Solucio posada");
					}
					else System.out.println("Game no esta creat encara");
					break;
				case "INSERTCOMB":
					if (g!= null) {
						if (h < f) {
							g.insertComb();
							++h;
							System.out.println("Combinacio insertada");
						}
						else System.out.println("El taulell ja esta ple");
					}
					else System.out.println("Game no esta creat encara");
					break;
				case "AIPLAY":
					if (g!= null) {
						System.out.println("Guess de la IA: "+g.AIPlay());
					}
					else System.out.println("Game no esta creat encara");
					break;
				case "HUMANPLAY":
					if (g!= null) {
						System.out.println("Introdueix guess (numeros sense espais)");
						g.humanPlay(scanner.next());
						System.out.println("Guess feta");
					}
					else System.out.println("Game no esta creat encara");
					break;
				case "COMPARECOMB":
					if (g!= null) {
						if (h < f) {
							g.compareComb();
							System.out.println("Combinacio comparada");
						}
						else System.out.println("Posicio de la combinacio fora del taulell");
					}
					else System.out.println("Game no esta creat encara");
					break;

				case "GETHEIGHT":
					if (g!= null) {
						System.out.println("Height = "+g.getHeight());
					}
					else System.out.println("Game no esta creat encara");
					break;
				case "ISVICTORIOUS":
					if (g!= null) {
						System.out.println(g.isVictorious());
					}
					else System.out.println("Game no esta creat encara");
					break;
				case "ISFINISHED":
					if (g!= null) {
						System.out.println(g.isFinished());
					}
					else System.out.println("Game no esta creat encara");
					break;
				default:
					System.out.println("Entrada incorrecta");
					break;
			}
			input = scanner.next();
		}
	}
}