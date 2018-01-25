package drivers;

import java.util.Scanner;

import domini.Game;
import domini.Ranking;

public class RankingDriver {

	public static void main(String[] args) {

		System.out.println("Escriu comandes per controlar la classe Ranking:\nCREATERANKING, ADDSCORE, PRINTRANKING\n");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		Ranking ranking = null;
		while (!input.equals("EXIT")) {
			System.out.println("input: "+input);
			switch (input) {
				case "CREATERANKING":
					ranking = new Ranking();
					System.out.println("Ranking creat!");
					break;
				case "ADDSCORE":
					if (ranking != null) {
						System.out.println("Introdueix usuari, puntació en tirades i difucltat amb valor 0-2 per guardar al ranking");
						System.out.println("La insercio nomes es fara si la puntuacio es millor que al menys una de les que hi ha al ranking");
						ranking.addScore(scanner.next(), scanner.nextInt(), scanner.nextInt());
						System.out.println("Puntuacio afegida");
					}
					else System.out.println("No hi ha cap ranking creat");
					break;
				case "PRINTRANKING":
					if (ranking != null) {
						for(int i = 0; i<10;i++){
							System.out.println(ranking.getRankingRow(i));
						}
					}
					else System.out.println("No hi ha cap ranking creat");
					break;

				
				default:
					System.out.println("Bad entry");
					break;
			}
			input = scanner.next();
		}
	}
}