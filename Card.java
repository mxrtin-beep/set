
/**
 * Card class
 * 
 * @author Martin Bourdev
 *          with assistance from Ellen Guo
 * @version 12-12-18
 */
public class Card
{
    // instance variables - replace the example below with your own
    private int number; //1, 2, 3
    private int shape;   //circle, diamond, wiggle (c, d, w)
    private int pattern; //open, closed, striped   (o, c, s)
    private int color;   //red, purple, green        (r, p, g)
    private boolean isSelected;
    /**
     * Constructor for objects of class Card
     * @param num number
     * @param sha shape
     * @param pat pattern
     * @param col color
     */
    public Card(int num, int sha, int pat, int col)
    {
        number = num;
        shape = sha;
        pattern = pat;
        color = col;
        isSelected = false;
    }

    /**
     * @return filename of card
     */
    public String getFileName()
    {
        return "cards/" + (number+1) + (shape+1) + (pattern+1) + (color+1) + ".png";
    }
    
    /**
     * @return card number
     */
    public int getNum()
    {
        return number;
    }
    
    /**
     * @return card shape
     */
    public int getShape()
    {
        return shape;
    }
    
    /**
     * @return card pattern
     */
    public int getPattern()
    {
        return pattern;
    }
    
    /**
     * @return card color
     */
    public int getColor()
    {
        return color;
    }
    
    /**
     * @return true if card is selected
     */
    public boolean isSelected()
    {
        return isSelected;
    }
    
    /**
     * unselects card
     */
    public void unSelect()
    {
        isSelected = false;
    }
    
    /**
     * selects card
     */
    public void select()
    {
        isSelected = true;
    }
    
    /**
     * Returns true if card equals card
     * @param card card to be tested
     * @return true if card is equal to card
     */
    public boolean equals(Card card)
    {
        return number == card.number && shape == card.shape && pattern == card.pattern &&
                            color == card.color;
    }
    
    /**
     * @return card as a string
     */
    public String toString()
    {
        return (number+1) + " " + (shape+1) + " " + (pattern+1) + " " + (color+1);
    }
}
