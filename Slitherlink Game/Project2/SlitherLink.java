
/**
 * SlitherLink does the user interaction for a square Slither Link puzzle.
 * 
 * @authors Davinh Dang (22717235), Jason Ho JiaSheng (22360143)
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;

public class SlitherLink implements MouseListener
{    
    private Puzzle game;     // internal representation of the game
    private SimpleCanvas sc; // the display window
    
    private final int cellSize = 50;
    private final int dotSize = 6;
    private final int margin = cellSize;
    private final int fontSize = 16;
    private final Color bgColor = Color.white;
    private final Color line = Color.black;
    private final Color noLine = bgColor;

    /**
     * Creates a display for playing the puzzle p.
     */
    public SlitherLink(Puzzle p)
    {
        // COMPLETE THIS 4b
        game = p;
        int canvasSize = cellSize * (game.size() + 2);
        sc = new SimpleCanvas("Slither Link", canvasSize, canvasSize, bgColor);
        sc.setFont(new Font("Arial", 0, fontSize));
        sc.addMouseListener(this);
        displayPuzzle();
    }
    
    /**
     * Returns the current state of the game.
     */
    public Puzzle getGame()
    {
        return game;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        return sc;
    }

    /**
     * Displays the initial puzzle on sc. 
     * Have a look at puzzle-loop.com for a basic display, or use your imagination. 
     */
    public void displayPuzzle()
    {
        // COMPLETE THIS 4a
        Color c = noLine;
        for (int x = 0; x < game.size(); x++)
            for (int y = 0; y < game.size(); y++)
                if (game.getPuzzle()[y][x] != -1)
                    sc.drawString(game.getPuzzle()[y][x],
                                  cellSize * x + cellSize / 2 + cellSize,
                                  cellSize * y + cellSize / 2 + cellSize,
                                  line);
        for (int k = 0; k < game.size() + 1; k++)
            for (int l = 0; l < game.size(); l++) {
                if (game.getHorizontal()[k][l])   
                    c = line;
                else
                    c = noLine;
                sc.drawRectangle(cellSize * (l + 1),
                                 cellSize * (k + 1) - 3,
                                 cellSize * (l + 2),
                                 cellSize * (k + 1) + 3,
                                 c);
            }
        for (int m = 0; m < game.size(); m++)
            for (int n = 0; n < game.size() + 1; n++) {
                if (game.getVertical()[m][n])
                    c = line;
                else
                    c = noLine;
                sc.drawRectangle(cellSize * (n + 1) - 3,
                                 cellSize * (m + 1),
                                 cellSize * (n + 1) + 3,
                                 cellSize * (m + 2),
                                 c);
            }
        for (int i = 0; i < game.size() + 1; i++)
            for (int j = 0; j < game.size() + 1; j++)
                sc.drawCircle(cellSize * (i + 1),
                              cellSize * (j + 1),
                              dotSize,
                              line);
        sc.drawString("Clear",
                      cellSize / 2,
                      cellSize / 2,
                      line);
        sc.drawString("Done",
                      cellSize / 2,
                      cellSize * (game.size() + 2) - cellSize / 3,
                      line);
    }
    
    /**
     * Makes a horizontal click to the right of Dot r,c.
     * Update game and the display, if the indices are legal; otherwise do nothing.
     */
    public void horizontalClick(int r, int c)
    {
        // COMPLETE THIS 5a
        game.horizontalClick(r,c); 
        displayPuzzle();
    }
    
    /**
     * Makes a vertical click below Dot r,c. 
     * Update game and the display, if the indices are legal; otherwise do nothing. 
     */
    public void verticalClick(int r, int c)
    {
        // COMPLETE THIS 5b
        game.verticalClick(r,c); 
        displayPuzzle();
    }
    
    /**
     * Actions for a mouse press.
     */
    public void mousePressed(MouseEvent e) 
    {
        // COMPLETE THIS 6
        int x = e.getX();
        int y = e.getY();
        int diff=cellSize - dotSize;
        int canvasSize = cellSize * (game.size() + 2);
        if (x % cellSize < diff && x % cellSize > dotSize)
            if (y % cellSize < 10 && y % cellSize >= 0)
                horizontalClick(y / cellSize - 1, x / cellSize - 1);
            if (y % cellSize < cellSize && y % cellSize >= cellSize-10)
                horizontalClick(y / cellSize, x / cellSize - 1);
        if (y % cellSize < diff && y % cellSize > dotSize)
            if (x % cellSize < dotSize && x % cellSize >= 0)
                verticalClick(y / cellSize - 1, x / cellSize - 1);
            if (x % cellSize < cellSize && x % cellSize >= diff)
                verticalClick(y / cellSize - 1, x / cellSize);
        if (x >= cellSize / 2 && x <= cellSize + cellSize / 2
                && y >= 0 && y <= cellSize / 2)
            game.clear();
        if (x >= cellSize / 2 && x <= cellSize + cellSize / 2
                && y >= canvasSize - cellSize * 4 / 5 && y <= canvasSize) {
            sc.drawRectangle(cellSize * 2,
                             canvasSize - cellSize * 4 / 5,
                             canvasSize,
                             canvasSize,
                             noLine);
            sc.drawString(AnalyzeSolution.finished(game),
                          cellSize * 2,
                          canvasSize - cellSize / 3,
                          line);
        }
        displayPuzzle();
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
