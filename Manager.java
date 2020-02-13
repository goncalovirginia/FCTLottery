
public class Manager {

	private boolean status;
	private double totalPrize;
	private double[] prizes;
	private static final double[] prizeLevelPercentage = {0.432, 0.0415, 0.0192, 0.0145, 0.0148, 0.0167, 0.0138, 0.0175, 0.0285, 0.041, 0.0495, 0.1385, 0.1725};
	private int[] userNumbers, userStars, prizeLevelUsers;
	private Key key;
	
	public Manager() {
		status = false;
		totalPrize = 0;
		userNumbers = new int[5];
		userStars = new int[2];
		prizeLevelUsers = new int[13];
		prizes = new double[13];
	}
	
	public void startGame(double amount) {
		status = true;
		totalPrize += amount;
		key = new Key();
		
		for (int i = 0; i < 13; i++) {
			prizes[i] = 0;
			prizeLevelUsers[i] = 0;
		}
	}
	
	public void endGame() {
		status = false;
	}
	
	public boolean gameStatus() {
		return status;
	}
	
	public double getTotalPrize() {
		return totalPrize;
	}
	
	public int getPrizeLevelUsers(int pos) {
		return prizeLevelUsers[pos];
	}
	
	public double getPrizePerUser(int pos) {
		return prizes[pos];
	}
	
	public void registerNumber(int pos, int number) {
		userNumbers[pos] = number;
	}
	
	public void registerStar(int pos, int number) {
		userStars[pos] = number;
	}
	
	private boolean checkInput(int[] key, int max, int min) {
		int i = 0;
		boolean keyOk = true;
		
		while (i < key.length && keyOk) {
			if (key[i] < min || key [i] > max) {
				keyOk = false;
			}
			else {
				int j = 0;
				while(j < key.length && keyOk) {
					if (i != j && key[i] == key[j]) {
						keyOk = false;
					}
					j++;
				}
			}
			i++;
		}
		
		return keyOk;
	}
	
	public boolean checkNumbers() {
		return checkInput(userNumbers, 50, 1);
	}
	
	public boolean checkStars() {
		return checkInput(userStars, 12, 1);
	}
	
	private int compareNumbers() {
		IteratorInt keyNumbers = key.criaIteratorNumbers();
		int correctNumbers = 0;
		
		for (int i = 0; i < userNumbers.length; i++) {
			while (keyNumbers.hasNextInt()) {
				if (userNumbers[i] == keyNumbers.nextInt()) {
					correctNumbers++;
				}
			}
			keyNumbers.reinitialize();
		}
		
		return correctNumbers;
	}
	
	private int compareStars() {
		IteratorInt keyStars = key.criaIteratorStars();
		int correctStars = 0;
		
		for (int i = 0; i < userStars.length; i++) {
			while (keyStars.hasNextInt()) {
				if (userStars[i] == keyStars.nextInt()) {
					correctStars++;
				}
			}
			keyStars.reinitialize();
		}
		
		return correctStars;
	}
	
	public int prizeLevel() {
		int prizeLevel = 0;
		int correctStars = compareStars(), correctNumbers = compareNumbers();
		switch(correctStars) {
		case 0:
			switch(correctNumbers) {
			case 2:
				prizeLevel = 13;
				break;
			case 3:
				prizeLevel = 10;
				break;
			case 4:
				prizeLevel = 7;
				break;
			case 5:
				prizeLevel = 3;
				break;
			}
			break;
		case 1:
			switch(correctNumbers) {
			case 2:
				prizeLevel = 12;
				break;
			case 3:
				prizeLevel = 9;
				break;
			case 4:
				prizeLevel = 5;
				break;
			case 5:
				prizeLevel = 2;
				break;
			}
			break;
		case 2:
			switch(correctNumbers) {
			case 1:
				prizeLevel = 11;
				break;
			case 2:
				prizeLevel = 8;
				break;
			case 3:
				prizeLevel = 6;
				break;
			case 4:
				prizeLevel = 4;
				break;
			case 5:
				prizeLevel = 1;
				break;
			}
			break;
		}
		
		return prizeLevel;
	}
	
	public void attributePrize() {
		if (prizeLevel() != 0) {
			prizeLevelUsers[prizeLevel()-1]++;
		}
		
	}
	
	public void givePrizes() {
		double totalWon = 0;
		
		for (int i = 0; i < prizes.length; i++) {
			if (prizeLevelUsers[i] > 0) {
				prizes[i] = (totalPrize * prizeLevelPercentage[i])/prizeLevelUsers[i];
				totalWon += totalPrize * prizeLevelPercentage[i];
			}
		}
		
		totalPrize -= totalWon;
	}
	
}
