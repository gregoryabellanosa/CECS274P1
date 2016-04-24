import java.util.*;
/**
 * Program that simulates the card game War using array lists as decks of cards
 * and different methods to perform different actions that establish decisions
 * using a repeating menu.
 * 
 * @author Gregory Abellanosa <gregoryabellanosa@gmail.com>
 *
 */
public class WarGame {

	public static void main(String[] args) {
		
		//creates 2 array lists to fill with values to represent cards
		ArrayList<Integer> compDeck = new ArrayList<Integer>();
		ArrayList<Integer> playerDeck = new ArrayList<Integer>();
		
	    //creates a deck of 52 cards ad stores it in an array list
		createDeck(compDeck);
		displayDeck(compDeck);
		
		//shuffles the values stored in the arraylist
		shuffleDeck(compDeck);
		shuffleDeck(compDeck);
		
		
		displayDeck(compDeck);
		
		//splits deck in half
		splitDeck(compDeck, playerDeck);
		System.out.println();
		System.out.println("Computer Deck: " + compDeck);
		System.out.println("User Deck: " + playerDeck);
		
		displayMenu(compDeck, playerDeck);
	}
	
	/**
	 * Fills an array list intended to simulate a deck of 52 cards. The array list is 
	 * filled with 4 sets of 13 values (13 cards). A = 1, J = 11, Q = 12, K = 13.
	 * 
	 * @param nums the array list that will be filled with the card values
	 */
	public static void createDeck(ArrayList<Integer> nums)
	{
		for (int i = 1; i <= 13; i++)
		{
			nums.add(i);
		}
		for (int i = 13; i >= 1; i--)
		{
			nums.add(i);
		}
		for (int i = 1; i <= 13; i++)
		{
			nums.add(i);
		}
		for (int i = 13; i >= 1; i--)
		{
			nums.add(i);
		}
	}
	
	/**
	 * When the decks are created the values are placed in a specific ordered manner. 
	 * The intention of this method is to mix around the order of these values within 
	 * the array list.
	 * 
	 * @param nums the array list containing the values to be shuffled
	 */
	public static void shuffleDeck(ArrayList<Integer> nums)
	{
		Random randNumGen = new Random();
		int num = randNumGen.nextInt(5);
		
		for(int i = num; i < nums.size() / 2; i+= 2)
		{
			int swap = nums.get(i);
			nums.set(i, nums.get(i + 3));
			nums.set(i + 3, swap);
		}
		for(int i = nums.size(); i < num; i--)
		{
			int swap = nums.get(i);
			nums.set(i, nums.get(i));
			nums.set(i, swap);
		}
		for(int i = num; i < nums.size() / 2; i++)
		{
			int swap = nums.get(i);
			nums.set(i, nums.get(i + 3));
			nums.set(i + 3, swap);
		}
	}
	
	/**
	 * Since war will be played as a 2 player game, the deck needs to be evenly split into
	 * half. This method takes the half of the values from one array list and copies them into 
	 * another array list. The values are then removed from the original array list so that the decks
	 * holds an even amount of cards.
	 * 
	 * @param comp the original deck that half the cards will be taken from
	 * @param playa the other deck that will receive half of the original deck
	 */
	public static void splitDeck(ArrayList<Integer> comp, ArrayList<Integer> playa)
	{
		for (int i = 0; i < comp.size(); i++)
		{
			int cardCopy = comp.get(i);
			playa.add(cardCopy);
			comp.remove(i);
		}
	}
	
	/**
	 * Method that displays a repeating menu of options when playing or starting the game.
	 * 
	 * @param comp the deck of the computer 
	 * @param playa the deck of the user
	 */
	public static void displayMenu(ArrayList<Integer> comp, ArrayList<Integer> playa)
	{
		Scanner input = new Scanner(System.in);
		
		double playerWinCounter = 0;
		double numberOfRounds = 0;
		
		boolean quit = false;
		
		do
		{
			System.out.println("What would you like to do? (Enter 1, 2, 3, 4, or 5)");
			System.out.println("1.) Play (One Round)");
			System.out.println("2.) Show Scores");
			System.out.println("3.) Peek at Your Cards");
			System.out.println("4.) Show Percentage");
			System.out.println("5.) Quit");
			
			int choice = 0;
			
			/*choice = input.nextInt();
			checkInput(choice);*/
			
			boolean inputChecker = true;
			
			while (inputChecker)
			{
				try {
					choice = input.nextInt();
					inputChecker = false;
				} catch ( InputMismatchException im ){
					input.next();
					System.out.println("Invalid Input.");
				}
			}
						
			while (choice < 1 || choice > 5)
			{
				System.out.println("That option is not available. Please pick an option provided on the menu.");
				choice = input.nextInt();
			}
			
			switch (choice)
			{
				case 1:
					if (battle(comp, playa) == 1)
					{
						playerWinCounter++;
					}
					else if (comp.size() == 0 || comp.size() == 0)
					{
						System.out.println("Game is over");
						if (comp.size() == 0)
						{
							System.out.println("You win.");
						}
						else if (playa.size() == 0)
						{
							System.out.println("You Lose.");
						}
					}
					numberOfRounds++;
					break;
				case 2:
					System.out.println("Computer's Score: " + displayScore(comp));
					System.out.println("Player's Score: " + displayScore(playa));
					System.out.println();
					break;
				case 3:
					ArrayList<Integer> sortedDeck = sortDeck(playa);
					System.out.println("Here are the cards you currently hold in ascending order: ");
					displayDeck(sortedDeck);
					System.out.println();
					break;
				case 4:
					displayStats(playerWinCounter, numberOfRounds);
					System.out.println();
					break;
				case 5:
					System.out.println("Goodbye.");
					quit = true;
					break;
				default:
					System.out.println("Invalid Input.");
					break;
					
			}
		} while (quit != true);
		
		input.close();
	}
	
	/**
	 * Method that, when called, plays one round of War. The values of the two top cards are 
	 * compared and the player with the winning card (the higher value) gets to add both cards
	 * to the end of their deck of cards. If there is a tie, the war() method is called to determine
	 * a winner.
	 * 
	 * @param comp the deck of the computer 
	 * @param playa the deck of the user
	 * @return the ArrayList that held the winning card (0 is computer and 1 is user)
	 */
	public static int battle(ArrayList<Integer> comp, ArrayList<Integer> playa)
	{
		System.out.println("Computer's card:" + comp.get(0));
		System.out.println("Player's card:" + playa.get(0));
		int winner = 0;
		if (comp.get(0) > playa.get(0))
		{
			int loserCard = playa.get(0);
			int winnerCard = comp.get(0);
			playa.remove(0);
			comp.remove(0);
			comp.add(loserCard);
			comp.add(winnerCard);
		}
		else if (comp.get(0) < playa.get(0))
		{
			int loserCard = comp.get(0);
			int winnerCard = playa.get(0);
			playa.remove(0);
			comp.remove(0);
			playa.add(loserCard);
			playa.add(winnerCard);
			
			winner = 1;
		}
		else /*if (comp.get(0) == playa.get(0))*/
		{
			war(comp, playa);
			if (war(comp, playa) == 1)
			{
				winner = 1;
			}
		}
		
		return winner;
	}
	
	/**
	 * If the battle() method encounters a tie between the two decks then this method is intended
	 * to resolve this tie. When a tie is encountered, the next three cards are played face-down
	 * (skipped) and the following (fourth) card after is played from both decks. Those values are 
	 * then compared to determine a winter. If another tie is encountered the process will repeat until
	 * a winning card is determined.
	 * 
	 * @param comp playa the deck of the computer
	 * @param playa playa the deck of the user
	 * @return the ArrayList holding the winning card (0 for computer, 1 for user)
	 */
	public static int war(ArrayList<Integer> comp, ArrayList<Integer> playa)
	{
		int index = 0;
		int winner = 0;
				if (comp.get(index + 4) > playa.get(index + 4))
				{
					for (int i = index; i <= index + 4; i++)
					{
						comp.add(playa.get(index));
						comp.add(comp.get(index));
					}
					for (int i = index; i <= index + 4; i++)
					{
						comp.remove(index);
						playa.remove(index);
					}
				}
				
				else if (comp.get(index + 4) < playa.get(index + 4))
				{
					for (int i = index; i <= index + 4; i++)
					{
						playa.add(comp.get(index));
						playa.add(playa.get(index));
					}
					for (int i = index; i <= index + 4; i++)
					{
						playa.remove(index);
						comp.remove(index);
					}
					winner = 1;
				}
				else /*if (comp.get(index + 4) == playa.get(index + 4))*/
				{
					index += 5;
				}
			
			if (playa.size() <= 0)
			{
				winner = 2;
			}
			else if (comp.size() <= 0)
			{
				winner = 3;
			}
		return winner;
	}
	
	/**
	 * Method that displays the current score of the player who holds the deck that is called
	 * in the parameters. The score is determined by the size of the array list holding the 
	 * values of the deck.
	 * 
	 * @param currentDeck the deck containing the the amount of values that determine the score
	 * @return the amount of values i the array / the size of the array
	 */
	public static int displayScore(ArrayList<Integer> currentDeck)
	{
		int score = currentDeck.size();
		return score;
	}
	
	/**
	 * Method that takes the values of an array list, copies those values into a newly
	 * created array list, then sorts those values into ascending order.
	 * 
	 * @param currentDeck the deck containing the values that will be copied
	 * @return the new array list containing a set of copied values ordered in ascending order
	 */
	public static ArrayList<Integer> sortDeck(ArrayList<Integer> currentDeck)
	{
		ArrayList<Integer> newDeck = new ArrayList<Integer>();
		
		for (int i = 0; i < currentDeck.size(); i++)
		{
			newDeck.add(i, currentDeck.get(i));
		}
		
		for (int i = 0; i < newDeck.size(); i++)
		{
			int lowest = i;
			for (int j = i + 1; j < newDeck.size(); j++)
			{
				if (newDeck.get(j) < newDeck.get(lowest))
				{
					lowest = j;
				}
			}
			int swap = newDeck.get(i);
			newDeck.set(i, newDeck.get(lowest));
			newDeck.set(lowest, swap);
		}
		
		return newDeck;
	}
	
	/**
	 * Displays the contents of the deck that is called in the parameters
	 * 
	 * @param currentDeck the deck containing the values that will be displayed
	 */
	public static void displayDeck(ArrayList<Integer> currentDeck)
	{
		System.out.println(currentDeck);
	}
	
	/**
	 * Method that checks the percent of wins a player has out of all the rounds played
	 * 
	 * @param wins the number of wins
	 * @param plays the total amount of plays
	 * @return the percent of wins out of plays
	 */
	public static int calcPercentage(double wins, double plays)
	{
		double percent = (wins / plays) * (100);
		return (int) percent;
	}
	
	/**
	 * Displays the percentage calculated from the calcPercentage() method
	 * @param wins the number of wins
	 * @param plays the number of rounds played
	 */
	public static void displayStats(double wins, double plays)
	{
		try
		{
			System.out.println(calcPercentage(wins, plays) + "% wins.");
		} catch (ArithmeticException e) {
			System.out.println("You have not played any rounds yet. Try one round first.\n");
		}
	}
	
	/**
	 * 
	 */
	public static int checkInput(int answer)
	{
		Scanner input = new Scanner(System.in);
		
		boolean inputChecker = true;
		
		while (inputChecker)
		{
			try {
				answer = input.nextInt();
				inputChecker = false;
			} catch ( InputMismatchException im ){
				input.next();
				System.out.println("Invalid Input.");
			}
		}
		input.close();
		return answer;		
	}
	
}
