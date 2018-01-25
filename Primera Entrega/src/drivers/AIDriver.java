package drivers;

import domini.AI;

import java.util.Scanner;

public class AIDriver {

	public static void main(String[] args) {

		System.out.println("Escriu comandes per controlar la classe AI:\nCREATEAI , PLAY , GETSOLUTION\n");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		AI ai = null;
		boolean jugat = false;
		while (!input.equals("EXIT")) {
			System.out.println("input: "+input);
			switch (input) {
				case "CREATEAI":
					System.out.println("Introdueix numero files i de colors");
					ai = new AI(scanner.nextInt(),scanner.nextInt());
					jugat = false;
					System.out.println("AI creat!");
					break;

				case "PLAY":
					if (ai!= null) {
						if (jugat) System.out.println("Ja has jugat abans");
						else{
							System.out.println(ai.play().getCombValue());
							jugat = true;
						}
					}
					else System.out.println("AI no esta creat encara");
					break;

				case "GETSOLUTION":
					if (ai!= null) {
						System.out.println(ai.getSolution().getCombValue());
					}
					else System.out.println("AI no esta creat encara");
					break;

				default:
					System.out.println("Entrada incorrecta");
					break;
			}
			input = scanner.next();
		}
	}
}
