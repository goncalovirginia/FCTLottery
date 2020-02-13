
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Manager manager = new Manager();
		Scanner in = new Scanner(System.in);
		String option;
		
		do {
			prompt(manager);
			option = readOption(in);
			executeOption(in, option, manager);
		} while (!option.equals("SAIR"));
		
	}
	
	private static void prompt(Manager manager) {
		if (manager.gameStatus()) {
			System.out.print("FCTMILHOES> ");
		}
		else {
			System.out.print("> ");
		}
	}
	
	private static String readOption(Scanner in) {
		return in.next().toUpperCase();
	}
	
	private static void executeOption(Scanner in, String option, Manager manager) {
		if (!manager.gameStatus()) {
			switch(option) {
			case "NOVO":
				newGame(in, manager);
				break;
			case "SAI":
				exit(manager);
				break;
			case "AJUDA":
				help(manager);
				break;
			default:
				System.out.println("Comando inexistente.");
				in.nextLine();
				break;
			}
		}
		else {
			switch(option) {
			case "JOGA":
				play(in, manager);
				break;
			case "FIM":
				end(manager);
				break;
			case "AJUDA":
				help(manager);
				break;
			default:
				System.out.println("Comando inexistente.");
				in.nextLine();
				break;
			}
		}

	}
	
	private static void help(Manager manager) {
		if (!manager.gameStatus()) {
			System.out.println("novo - Novo jogo dando um valor inicial");
			System.out.println("sai - Termina a execucao do programa");
			System.out.println("ajuda - Mostra os comandos existentes");
		}
		else {
			System.out.println("joga - Simula uma aposta, dando uma chave");
			System.out.println("fim - Termina o jogo em curso");
			System.out.println("ajuda - Mostra os comandos existentes");
		}
	}
	
	private static void exit(Manager manager) {
		System.out.printf("Valor acumulado: %.2f Euros. Ate a proxima.\n", manager.getTotalPrize());
		manager.endGame();
	}
	
	private static void newGame(Scanner in, Manager manager) {
		double amount = in.nextDouble();
		in.nextLine();
		
		if (amount > 0) {
			manager.startGame(amount);
			System.out.printf("Jogo iniciado. Valor do premio: %.2f Euros.\n", manager.getTotalPrize());
		}
		else {
			System.out.println("Valor incorrecto.");
		}
	}
	
	private static void play(Scanner in, Manager manager) {
		for (int i = 0; i < 5; i++) {
			manager.registerNumber(i, in.nextInt());
		}
		
		for (int i = 0; i < 2; i++) {
			manager.registerStar(i, in.nextInt());
		}
		
		if (!(manager.checkNumbers() && manager.checkStars())) {
			System.out.println("Chave incorrecta.");
		}
		else if (manager.prizeLevel() == 0) {
			System.out.println("Obrigado pela aposta.");
		}
		else {
			manager.attributePrize();
			System.out.println("Obrigado pela aposta. Premio de nivel: " + manager.prizeLevel());
		}
	}
	
	private static void end(Manager manager) {
		manager.givePrizes();
		
		for (int i = 0; i < 13; i++) {
			if (manager.getPrizeLevelUsers(i) > 0) {
				System.out.printf("Nivel: " + (i+1) + " Jogadores: " + manager.getPrizeLevelUsers(i) + " Valor premio: %.2f Euros\n", manager.getPrizePerUser(i));
			}
			else {
				System.out.println("Nivel: " + (i+1) + " Jogadores: 0");
			}
		}
		
		System.out.printf("Valor acumulado: %.2f Euros\n", manager.getTotalPrize());
		
		manager.endGame();
	}

}
