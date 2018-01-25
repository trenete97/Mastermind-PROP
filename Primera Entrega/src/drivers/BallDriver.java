package drivers;

import java.util.Scanner;

import domini.Ball;
import domini.BallCombination;

public class BallDriver {

	public static Ball b = null;
	public static BallCombination bc = null;
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Escriu comandes per controlar la classe Ball i BallCombination:"
				+ "\nCREATEBALL , CHANGEBALLCOLOR , GETBALLCOLOR\nCREATECOMBINATION ,"
				+ " CHANGECOMBINATIONBALLCOLOR , GETCOMBINATIONBALLCOLOR , GETCOMBVALUE , SETRANDOMCOMBINATION , SETCOMBINATION");
		System.out.println("La posicio de les boles en una combinacio va de 0 a n-1, on n es el numero de boles que te la combinacio");
		String input = scanner.next();
		System.out.println("input: "+input);

		while (!input.equals("EXIT")) {
			switch (input) {
				case "CREATEBALL":
					b = new Ball();
					System.out.println("Ball creada!");
					break;
				case "CHANGEBALLCOLOR":
					if (b != null) {
						System.out.println("Introdueix color (integer)");
						b.setColor(scanner.nextInt());
						System.out.println("Color canviat");
					}
					else
						System.out.println("No s'ha creat la bola encara");
					break;
				case "GETBALLCOLOR":
					if (b != null) {
						System.out.println("Color = "+String.valueOf(b.getColor()));
					}
					else
						System.out.println("No s'ha creat la bola encara");
					break;

				case "CREATECOMBINATION":
					System.out.println("Introdueix numero de boles i colors (integer)");
					bc = new BallCombination(scanner.nextInt(),scanner.nextInt());
					System.out.println("Combinacio creada");
					break;

				case "CHANGECOMBINATIONBALLCOLOR":
					if (bc != null) {
						System.out.println("Introdueix posicio de bola (integer)");
						System.out.println("Introdueix numero de color (integer)");
						bc.setBallColor(scanner.nextInt(),scanner.nextInt());
						System.out.println("Canvi fet");
					}
					else
						System.out.println("No s'ha creat la combinacio de boles encara");
					break;

				case "GETCOMBINATIONBALLCOLOR":
					if (bc != null) {
						System.out.println("Introdueix posicio de bola (integer)");
						System.out.println(bc.getBallColor(scanner.nextInt()));
					}
					else
						System.out.println("No s'ha creat la combinacio de boles encara");
					break;

				case "GETCOMBVALUE":
					if (bc != null) {
						System.out.println(bc.getCombValue());
					}
					else
						System.out.println("No s'ha creat la combinacio de boles encara");
					break;

				case "SETRANDOMCOMBINATION":
					if (bc != null) {
						bc.setRandomCombination();
						System.out.println("Combinacio de boles creada aleatoriament");
					}
					else
						System.out.println("No s'ha creat la combinacio de boles encara");
					break;

				case "SETCOMBINATION":
					if (bc != null) {
						System.out.println("Introdueix combinacio de colors (integers tot junts)");
						bc.setCombination(scanner.next());
						System.out.println("Combinacio posada");
					}
					else
						System.out.println("No s'ha creat la combinacio de boles encara");
					break;

				default:
					System.out.println("Bad entry");
					break;
			}
			input = scanner.next();
		}
	}
}
