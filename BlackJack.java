/*
 * Programmer: Snegirev, Domentyan
 * Chemeketa Community College
 * Created: Friday, February 5, 2016
 * Class: CIS133J
 * Assignment: BlackJack
 * File Name: BlackJack.java
 * Description: A Black Jack card game.
 */


import java.io.*;
import java.util.Scanner;

class BlackJack
{
	Scanner scan = new Scanner(System.in);
	Deck deck;		// An instance of Deck class to create the deck.
	BlackJackHand dealerHand;	// An instance of BlackJackHand class to create
								// dealer's hand.	
	
	BlackJackHand userHand;	// An instance of BlackJackHand class to create
						    // user's hand.
	
	// Declare variables.
	String dealerHit;
	String userHit;
	String rematch;
	
	int money;
	int bet;
	int checkBet;
	int count;
	int dealerCount;
	
	boolean bust;
	boolean winner;
	
	/**
	* Shuffles the deck then deals two cards to each player(dealer and user).
	*/
	public void dealCards()
	{
		deck.shuffle();
		
		dealerHand.addCard(deck.dealCard());
		userHand.addCard(deck.dealCard());
		dealerHand.addCard(deck.dealCard());
		userHand.addCard(deck.dealCard());
	}	
	
	/**
	* Displays the cards dealt to each player.
	*/
	public void getHand()
	{
		System.out.println();
		System.out.println("-----------------------------------------------");
		
		System.out.println("Dealer has the " + dealerHand.getCard(0)
			+ " and the " + dealerHand.getCard(1) + ".");
		System.out.println("Dealer has " + dealerHand.getBlackjackValue());
		System.out.println();
		
		System.out.println("You have the " + userHand.getCard(0)
			+ " and the " + userHand.getCard(1) + ".");
		System.out.println("You have " + userHand.getBlackjackValue());
		
		System.out.println("-----------------------------------------------");
		System.out.println();
	}
	
	/**
	* Checks for and gets a valid bet from the user.
	*/
	public void bet()
	{
		do{
			System.out.println();
			System.out.print("Enter amount to bet: $");
			checkBet = scan.nextInt();
			
			if (checkBet > money)
			{
			    System.out.println("You don't have enough money to bet " +
					"that amount");
			}
			if (checkBet <= 0)
			{
			    System.out.println("You need to bet something.");
			}
			else
				bet = checkBet;			
		}while((checkBet > money) || (checkBet <= 0));
	}
	
	/**
	* Checks to see how much money the user has. If the user has money,
	* the game continues.
	*/
	public void money()
	{
		// User has no money, ends the game.
		if (money == 0)
		{
		    System.out.println();
			System.out.println("You lost all of your money!");
			rematch = "n";			
		}
		// User still has money, asks them if they want to continue.
		else
		{
			System.out.println();
			System.out.println("You have $" + money);
			System.out.print("Do you want to Play Again? Y/N: ");
			rematch = scan.next();
		}
	}
	
	/**
	* Dealer choses whether to hit or stay.
	*/
	public void dealerTurn()
	{
		// Rule: Dealer must hit when less than 17.
		if (dealerHand.getBlackjackValue() < 17)
		{
			dealerCount++;
			dealerHand.addCard(deck.dealCard());
			System.out.println("Dealer hits.");
			System.out.println("Dealer got the "
				+ dealerHand.getCard(dealerCount));
			System.out.println();
		    System.out.println("Dealer stays.");
			System.out.println();
		}
		// Dealer stays if >= 17.
		else
		{
			System.out.println("Dealer stays.");
			System.out.println();			
		}
	}
	
	/**
	* User decides whether to hit or stay. Tells user the results,
	* especially if they busted.
	*/
	public void userTurn()
	{
		// Continues until a winner or bust is found.
		if ((winner == false) && (bust == false))
		{
			// Asks user if they want to Hit or Stay.
			System.out.print("Hit or Stay? H/S: ");
			userHit = scan.next();
			
			// User hits, prints the results and the dealer takes a turn.
			if (userHit.equalsIgnoreCase("h"))
			{
				count++;
				userHand.addCard(deck.dealCard());
				System.out.println();
				
				System.out.println("-----------------");
				System.out.println("You hit.");
				System.out.println("You got the " 
					+ userHand.getCard(count));
				
				System.out.println();
				dealerTurn();
				System.out.println("You now have " 
					+ userHand.getBlackjackValue());
				System.out.println("Dealer now has " 
					+ dealerHand.getBlackjackValue());
				
				System.out.println("-----------------");
				System.out.println();
			}
			
			// User stays, prints the results and the dealer takes a turn.
			if (userHit.equalsIgnoreCase("s"))
			{
				System.out.println();
				
				System.out.println("-----------------");
				System.out.println("You stay.");
				
				System.out.println();
				dealerTurn();
				System.out.println("You have " 
					+ userHand.getBlackjackValue());
				System.out.println("Dealer has " 
					+ dealerHand.getBlackjackValue());
				
				System.out.println("-----------------");
				System.out.println();
				
				// If the dealer's hand is better than the user's hand
				// AND less than 21, the dealer wins.
				if ((dealerHand.getBlackjackValue() 
						>= userHand.getBlackjackValue())
					&& (dealerHand.getBlackjackValue() <= 21))
				{
					System.out.println("Dealer wins");
					winner = true;	
					money -= bet;
				}
				else
				{
					System.out.println("You win!");
					winner = true;
					money += bet;
				}	
			}
		}
		
	}
	/**
	 * Determines if the user or the dealer wins. 
	 * Shows the results of the win.
	 */
	public void determineWin()
	{
		// Dealer has exactly 21, dealer wins.
		if (dealerHand.getBlackjackValue() == 21) 
		{
			System.out.println("Dealer got Blackjack. Dealer wins.");
			winner = true;
			money -= bet;
		}
		// User has exactly 21, user wins.
		else if (userHand.getBlackjackValue() == 21) 
		{
			System.out.println("You got Blackjack. You win.");
			winner = true;	
			money += bet;
		}
		// Both dealer and user hands are over 21, dealer wins.
		else if ((userHand.getBlackjackValue() >= 21)
					&& (dealerHand.getBlackjackValue() >= 21))
		{
			System.out.println("You bust. Dealer wins.");
			bust = true;
			money -= bet;
		}
		// Dealer hand is over 21, user wins.
		else if (dealerHand.getBlackjackValue() > 21)
		{
			System.out.println("Dealer busts. You win.");
			bust = true;
			money += bet;
		}
		// User hand is over 21, dealer wins.
		else if (userHand.getBlackjackValue() > 21)
		{
			System.out.println("You bust. Dealer wins.");
			bust = true;
			money -= bet;
		}
	}
	
	/**
	 * Puts all the methods together in order to make the flow of the game.
	 */
	public void playGame()
	{
		// Asks the user for starting amount of money.
		System.out.println();
		System.out.print("How much money do you want to start with? $");
		money = scan.nextInt();
		
		do{
			deck = new Deck();
			dealerHand = new BlackJackHand();
			userHand = new BlackJackHand();
			
			// Resets variables whenever game resets.
			winner = false;
			bust = false;
			count = 1;
			dealerCount = 1;
			
			// Calls methods in the correct order.
			bet();
			dealCards();
			getHand();
			
			// Plays until a winner is found.
			do{
				determineWin();
				userTurn	();
			}while ((winner == false) && (bust == false));

			// Calculates the money earned or lost.
			money();	
			
		}while (rematch.equalsIgnoreCase("Y"));		
		
		System.out.println("Thanks for playing!");
		System.out.println();
	}
		
	public static void main (String args[])throws IOException
	{
		BlackJack game = new BlackJack();
		game.playGame();
	}
}