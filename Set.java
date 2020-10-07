import java.util.*;
import java.util.Stack;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
/**
 * Set game
 * 
 * @author Martin Bourdev 
 * @version 12-12-18
 */
public class Set
{
    /**
     * Constructor for objects of class Set
     * @param args code
     */
    public static void main(String [] args)
    {
        new Set();
    }

    public static final int ROWS = 4; //actually cols
    public static final int COLS = 3; //actually rows
    public static final int PLAYERS = 2;

    private Stack<Card> deck;
    private Card[][] cards;
    private SetDisplay display;
    private int[] playerScores = new int[PLAYERS];

    public Set()
    {
        display = new SetDisplay(this);
        for(int i = 0; i < PLAYERS; i++)
        {
            playerScores[i] = 0;
        }
        deck = new Stack<Card>();
        cards = new Card[ROWS][COLS];
        createDeck();
        deal();

    }

    /**
     * Creates a shuffled deck of set cards
     */
    private void createDeck()
    {
        ArrayList<Card> unshuffledDeck = new ArrayList<Card>();
        for(int i = 0; i < 3; i++) //nums
        {
            for(int j = 0; j < 3; j++) //shape
            {
                for(int k = 0; k < 3; k++) //pattern
                {
                    for(int l = 0; l < 3; l++) //color
                    {
                        unshuffledDeck.add(new Card(i, j, k, l));
                    }
                }
            }
        }
        for(int i = 0; i < 81; i++)
        {
            deck.push(unshuffledDeck.remove((int) (Math.random() * unshuffledDeck.size())));
        }
    }

    /**
     * Deals the set cards to the empty spots on the board
     */
    public void deal()
    {
        assert (!deck.isEmpty());
        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                if(cards[i][j] == null && !deck.isEmpty())
                    cards[i][j] = deck.pop();
            }
        }
        display.repaint();
    }

    /**
     * Returns a card from the board given the coordinates
     * @param row the row of the card
     * @param col the col of the card
     * @return the card at row, col
     */
    public Card getCard(int row, int col)
    {
        return cards[row][col];
    }

    /**
     * Returns the array of the player scores
     * @return players' scores
     */
    public int[] playerScores()
    {
        return playerScores;
    }

    /**
     * Called when a card is clicked
     * @param x the x value of the mouse
     * @param y the y value of the mouse
     */
    public void cardClicked(int x, int y)
    {
        Card card = cards[x][y];
        if(card != null)
        {
            int dim = 10*(x+1) + (y+1);
            if(!display.selectedDims.contains(dim))
                display.selectedDims.add(dim);
            else
            {
                for(int i = 0; i < display.selectedDims.size(); i++)
                {
                    if(dim == display.selectedDims.get(i))
                        display.selectedDims.remove(i);
                }
            }
            //System.out.println(x + " " + y);
            if(card.isSelected())
                card.unSelect();
            else
                card.select();
        }
    }

    /**
     * Called when a number is pressed
     * Checks if cards are a set
     * @param col the number
     */
    public void pileClicked(int col)
    {
        Card[] selectedCards = new Card[display.selectedDims.size()];
        for(int i = 0; i < selectedCards.length; i++)
        {
            //System.out.println(selectedCards.length);

            int dims = display.selectedDims.remove(0);
            int x = dims/10;
            int y = dims%10;
            //System.out.println(x);
            //System.out.println(y);
            selectedCards[i] = cards[x-1][y-1];
            //System.out.println("Card " + x + " " + y + " selected.");
        }
        if(selectedCards.length < 3) //not enough cards for set
        {
            display.unselect();
            if(col <= PLAYERS)
                System.out.println("Player " + col + " has " + playerScores[col-1] + " points.");
        }
        else if(selectedCards.length == 3) //set
        {
            if(isSet(selectedCards))
            {

                clear(col);
                System.out.println("set found!");
                if(!deck.isEmpty())
                    deal();
            }
            else
            {
                System.out.println("not a set");

                display.unselect();
            }
        }
        else if(selectedCards.length == 4) //planet
        {
            if(isPlanet(selectedCards))
            {
                clear(col);
                if(!deck.isEmpty())
                    deal();
                System.out.println("planet found!");
            }
            else
            {
                System.out.println("not a planet");
                display.unselect();
            }
        }
        else if(selectedCards.length == 6)
        {
            if(isGhostSet(selectedCards))
            {
                clear(col);
                if(!deck.isEmpty())
                    deal();
                System.out.println("ghost set found!");
            }
            else
            {
                System.out.println("not a ghost set");
                display.unselect();
            }
        }
        display.repaint();
    }

    /**
     * @param cards array of cards
     * @return true if given array of cards is a set
     * 
     */
    public boolean isSet(Card[] cards)
    {
        assert(cards.length == 3);
        Card missingCard = findMissingCard(cards[0], cards[1]);
        //System.out.println("Missing card: " + missingCard);
        //System.out.println("Selected card: " + cards[2]);
        return findMissingCard(cards[0], cards[1]).equals(cards[2]);
    }

    /**
     * Given two cards, returns the card needed to make it a set
     * @param card1 the first card
     * @param card2 the second card
     * @return the hypothetical third card to make it a set
     */
    public Card findMissingCard(Card card1, Card card2)
    {
        //assert(cards.length == 2);
        int num;
        int shape;
        int pattern;
        int color;
        if(card1.getNum() == card2.getNum())
            num = card1.getNum();
        else
            num = 3 - (card1.getNum() + card2.getNum());
        if(card1.getShape() == card2.getShape())
            shape = card1.getShape();
        else
            shape = 3 - (card1.getShape() + card2.getShape());
        if(card1.getPattern() == card2.getPattern())
            pattern = card1.getPattern();
        else
            pattern = 3 - (card1.getPattern() + card2.getPattern());
        if(card1.getColor() == card2.getColor())
            color = card1.getColor();
        else
            color = 3 - (card1.getColor() + card2.getColor());
        return new Card(num, shape, pattern, color);
    }

    /**
     * Given a card array, returns true if it's a planet (two pairs of cards are missing the same card to make a set).
     * @param cards 4 card card array
     * @return true if cards is a planet
     */
    public boolean isPlanet(Card[] cards)
    {
        assert(cards.length == 4);
        Card card1 = cards[0];
        Card card2 = cards[1];
        Card card3 = cards[2];
        Card card4 = cards[3];

        return findMissingCard(card1, card2).equals(findMissingCard(card3, card4)) ||
        findMissingCard(card1, card3).equals(findMissingCard(card2, card4)) ||
        findMissingCard(card1, card4).equals(findMissingCard(card2, card3));
    }

    /**
     * Clears the board of selected cards, gives them to the player
     * @param player the player that scored
     */
    public void clear(int player)
    {
        for(int i = 0; i < Set.ROWS; i++)
        {
            for(int j = 0; j < Set.COLS; j++)
            {
                if(cards[i][j] != null && cards[i][j].isSelected())
                {
                    Card card = cards[i][j];
                    playerScores[player-1]++;
                    cards[i][j] = null;
                }
            }
        }
    }

    /**
     * Mixes the cards of the board around
     */
    public void mix()
    {
        ArrayList<Card> table = new ArrayList<Card>();
        for(int i = 0; i < Set.ROWS; i++)
        {
            for(int j = 0; j < Set.COLS; j++)
            {
                if(cards[i][j] != null)
                {
                    table.add(cards[i][j]);
                    //cards[i][j] = null;
                }
            }
        } 
        for(int i = 0; i < Set.ROWS; i++)
        {
            for(int j = 0; j < Set.COLS; j++)
            {
                if(cards[i][j] != null)
                {
                    cards[i][j] = table.remove((int) (Math.random()*table.size()));
                    //cards[i][j] = null;
                }
            }
        } 
        display.repaint();
    }

    /**
     * Returns true if given card array is a ghost set (for three pairs of cards, 
     * the missing cards make a set themselves).
     * @param cards 6 card card array
     * @return true if cards is a ghost set
     */
    public boolean isGhostSet(Card[] cards)
    {
        ArrayList missing = new ArrayList();
        StringUtil u = new StringUtil();
        String s = "012345"; //order of cards to consider
        ArrayList<String> a = u.permutation("", s);
        for(String str : a) //iterate through all permutations of string
        {
            //add missing cards for pairs (a, b), (c, d), (e, f)
            for(int i = 0; i < str.length()/2; i++) 
            {
                missing.add(findMissingCard(cards[Integer.valueOf(str.substring(2*i, 2*i+1))], 
                                            cards[Integer.valueOf(str.substring(2*i+1, 2*i+2))]));
                                           
            }
        }
        //System.out.println(missing);
        for(int i = 0; i < missing.size()-2; i++)
        {
            Card[] guess = {(Card) missing.get(i), (Card) missing.get(i+1), (Card) missing.get(i+2)};
            if(isSet(guess))
                return true;
        }
    
        return false;
    }
    
}
