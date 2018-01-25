package drivers;

import java.util.Scanner;

import domini.FiveGuess;

public class FiveGuessDriver {

	public static FiveGuess IA = null;
	
	public static void main(String[] args) {
		System.out.println("Escriu comandes per controlar la classe FiveGuess:\nGETCOMB , CREATEFIVEGUESS , CALCULATENEWGUESS");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		Boolean calculatAbans = false;
		while (!input.equals("EXIT")) {
			System.out.println("input: " + input);
			switch (input) {
				case "GETCOMB":
					if (IA != null) {
						System.out.println("Comb = " + IA.getComb().getCombValue());
					}
					else System.out.println("Fiveguess no esta creat encara");
					break;

				case "CREATEFIVEGUESS":
					System.out.println("Escriu nombre de files i de colors");
					IA = new FiveGuess(scanner.nextInt(), scanner.nextInt());
					calculatAbans = false;
					System.out.println("Fiveguess creat");
					break;

				case "CALCULATENEWGUESS":
					if (IA != null) {
						if (calculatAbans) System.out.println("No pots calcular seguent guess dos vegades");
						else {
							System.out.println(IA.calculateNewGuess().getCombValue());
							calculatAbans = true;
						}
					}
					else System.out.println("Fiveguess no esta creat encara");
					break;

				default:
					System.out.println("Entrada no valida");
					break;
			}
			input = scanner.next();
		}
	}
}