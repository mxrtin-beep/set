import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
/**
 * Set Display
 * 
 * @author Martin Bourdev
 * @version 12-12-18
 */
public class SetDisplay extends JComponent implements MouseListener, KeyListener
{
    // instance variables - replace the example below with your own
    private static final int CARD_WIDTH = 350;
    private static final int CARD_HEIGHT = 250;
    private static final int SPACING = 5;
    
    private JFrame frame;
    private Set game;
    private double startTime = System.currentTimeMillis();
    private double endTime = System.currentTimeMillis();
    public ArrayList<Integer> selectedDims;
    
    public SetDisplay(Set game)
    {
        this.game = game;
        frame = new JFrame("Set");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        
        this.setPreferredSize(new Dimension((CARD_WIDTH + SPACING)*(Set.COLS+1), 
                                            (CARD_HEIGHT + SPACING + 1)*(Set.ROWS+1)));
        this.addMouseListener(this);
        frame.addKeyListener(this);
        frame.pack();
        frame.setVisible(true);
        selectedDims = new ArrayList<Integer>();
    }

    
    public void paintComponent(Graphics g)
    {
        //background
        g.setColor(Color.PINK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //cards
        for(int i = 0; i < Set.ROWS; i++)
        {
            for(int j = 0; j < Set.COLS; j++)
            {
                if(game.getCard(i, j) != null)
                {
                    drawCard(g, game.getCard(i, j), 
                        i*(SPACING + CARD_WIDTH), j*(SPACING + CARD_HEIGHT));
                    if(game.getCard(i, j).isSelected())
                        drawBorder(g, i*(SPACING+CARD_WIDTH), j*(SPACING + CARD_HEIGHT));
                }
                /*
                for(int k = 0; k < selectedDims.size(); k++)
                {
                    int selection = selectedDims.get(k);
                    int selectedCol = selection/10;
                    int selectedRow = selection%10;
                    drawBorder(g, selectedCol, selectedRow);
                }
                */
            }
        }
        
        
    }
    
    private void drawCard(Graphics g, Card card, int x, int y)
    {
        if(card == null)
        {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        }
        else
        {
            String fileName = card.getFileName();
            if(!new File(fileName).exists())
                throw new IllegalArgumentException("bad file name: " + fileName);
            Image image = new ImageIcon(fileName).getImage();
            g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
        }
    }
    
    public void mouseExited(MouseEvent e)
    {
    }
    
    public void mouseEntered(MouseEvent e)
    {
    }
    
    public void mouseReleased(MouseEvent e)
    {
    }
    
    public void mousePressed(MouseEvent e)
    {
    }
    
    public void mouseClicked(MouseEvent e)
    {
        
        int col = e.getX() / (SPACING + CARD_WIDTH);
        int row = e.getY() / (SPACING + CARD_HEIGHT);
        //System.out.println("Col: " + col + " Row: " + row);
        if(col > Set.ROWS-1)
            col = Set.ROWS-1;
        
        if(row > Set.COLS)
            row = Set.COLS;
        
        if(row < Set.COLS)
            game.cardClicked(col, row);
        repaint();
        //System.out.println(selectedDims);
    }
    
    private void drawBorder(Graphics g, int x, int y)
    {
        g.setColor(Color.BLUE);
        g.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        g.drawRect(x + 1, y + 1, CARD_WIDTH - 2, CARD_HEIGHT - 2);
        g.drawRect(x + 2, y + 2, CARD_WIDTH - 4, CARD_HEIGHT - 4);
    }
    
    public void unselect()
    {
        while(selectedDims.size() > 0)
            selectedDims.remove(0);
        for(int i = 0; i < Set.ROWS; i++)
        {
            for(int j = 0; j < Set.COLS; j++)
            {
                if(game.getCard(i, j) != null)
                    game.getCard(i, j).unSelect();
            }
        }
        repaint();
    }
    
    public void selectCard(int x, int y)
    {
        assert(x < Set.COLS - 1 && y < Set.ROWS - 1);
        selectedDims.add(new Integer(x*10 + y));
    }
    
    public void keyTyped(KeyEvent e)
    {
        if(e.getKeyChar() == 't' || e.getKeyChar() == 'T')
        {
            endTime = System.currentTimeMillis();
            System.out.println("The elapsed time is " + Double.toString((endTime - startTime)/1000)
                                 + " seconds.");
        }
        if(e.getKeyChar() == '1' || e.getKeyChar() == '2' || e.getKeyChar() == '3' || 
            e.getKeyChar() == '4' || e.getKeyChar() == '5' || e.getKeyChar() == '6' ||
            e.getKeyChar() == '7' || e.getKeyChar() == '8' || e.getKeyChar() == '9')
        {
            if(Integer.valueOf(e.getKeyChar()) - 48 <= game.PLAYERS)
            {
                game.pileClicked(Integer.valueOf(e.getKeyChar())-48);
                //System.out.println(e.getKeyChar());
            }
            //System.out.println(Integer.valueOf(e.getKeyChar()));
        }
        if(e.getKeyChar() == 'r' || e.getKeyChar() == 'R')
            game = new Set();
        if(e.getKeyChar() == 'm' || e.getKeyChar() == 'M')
            game.mix();
        if(e.getKeyChar() == 'u' || e.getKeyChar() == 'U')
            unselect();
    }
    
    public void keyPressed(KeyEvent e)
    {
    }
    
    public void keyReleased(KeyEvent e)
    {
    }
}
