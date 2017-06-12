/**
 * An object of type Deck represents an ordinary deck of 52 playing cards.
 * The deck can be shuffled, and cards can be dealt from the deck.
 */

public class Deck 
{
	/** An array of 52 Cards, representing the deck. */
	private Card[] deck; 
	
	/** How many cards have been dealt from the deck. */
	private int cardsUsed;
	
	/**
	 * Create an unshuffled deck of cards.
	 */
	
	public Deck() 
	{
		deck = new Card[52];
		int cardCt = 0; // How many cards have been created so far.
		for ( int suit = 0; suit <= 3; suit++ ) 
		{
			for ( int value = 1; value <= 13; value++ ) 
			{
				deck[cardCt] = new Card(value,suit);
				cardCt++;
			}
		}
		cardsUsed = 0;
	}
	
	/**
	 * Put all the used cards back into the deck, and shuffle it into
	 * a random order.
	 */
	
	public void shuffle() 
	{
		for ( int i = 51; i > 0; i-- ) 
		{
			int rand = (int)(Math.random()*(i+1));
			Card temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardsUsed = 0;
	}
	
	/**
	 * As cards are dealt from the deck, the number of cards left
	 * decreases.  
	 * @return Returns the number of cards that are still left in the deck.s
	 */
	
	public int cardsLeft() 
	{
		return 52 - cardsUsed;
	}
	
	/**
	 * Deals one card from the deck and returns it.
	 */
	
	public Card dealCard() 
	{
		if (cardsUsed == 52)
			shuffle();
		cardsUsed++;
		return deck[cardsUsed - 1];
	}
} // end class Deck
