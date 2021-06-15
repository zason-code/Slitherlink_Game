
/**
 * Puzzle maintains the internal representation of a square Slither Link puzzle.
 * 
 * @authors Davinh Dang (22717235), Jason Ho JiaSheng (22360143)
 * @version 1.0
 */
import java.util.ArrayList;

public class Puzzle
{
    private int[][] puzzle;         // the numbers in the squares, i.e. the puzzle definition
                                    // -1 if the square is empty, 0-3 otherwise
    private boolean[][] horizontal; // the horizontal line segments in the current solution
                                    // true if the segment is on, false otherwise
    private boolean[][] vertical;   // the vertical line segments in the current solution
                                    // true if the segment is on, false otherwise

    /**
     * Creates the puzzle from file filename, and an empty solution.
     * filename is assumed to hold a valid puzzle.
     */
    public Puzzle(String filename)
    {
        // COMPLETE THIS 1b
        FileIO fio = new FileIO(filename);
        ArrayList lines = fio.getLines();
        puzzle = new int[lines.size()][lines.size()];
        horizontal = new boolean[lines.size() + 1][lines.size()];
        vertical = new boolean[lines.size()][lines.size() + 1];
        parseFile(lines);
    }
    
    /**
     * Creates the puzzle from "eg3_1.txt".
     */
    public Puzzle()
    {
        this("eg3_1.txt");
    }

    /**
     * Returns the size of the puzzle.
     */
    public int size()
    {
        return puzzle.length;
    }

    /**
     * Returns the number layout of the puzzle.
     */
    public int[][] getPuzzle()
    {
        return puzzle;
    }

    /**
     * Returns the state of the current solution, horizontally.
     */
    public boolean[][] getHorizontal()
    {
        return horizontal;
    }

    /**
     * Returns the state of the current solution, vertically.
     */
    public boolean[][] getVertical()
    {
        return vertical;
    }

    /**
     * Turns lines into a Slither Link puzzle.
     * Each String holds one line from the input file. 
     * The first String in the argument goes into puzzle[0], 
     * The second String goes into puzzle[1], etc. 
     * lines is assumed to hold a valid square puzzle; see eg3_1.txt and eg5_1.txt for examples.
     */
    public void parseFile(ArrayList<String> lines)
    {
        // COMPLETE THIS 1a
        for (int i = 0; i < lines.size(); i++) {
            String[] numbers = lines.get(i).split(" ");
            for (int j = 0; j < numbers.length; j++)
                puzzle[i][j] = Integer.parseInt(numbers[j]);
        }
    }
    
    /**
     * Toggles the horizontal line segment to the right of Dot r,c, if the indices are legal.
     * Otherwise do nothing.
     */
    public void horizontalClick(int r, int c)
    {
        // COMPLETE THIS 2a
        if (r >= 0 && r < horizontal.length && c >= 0 && c < horizontal[0].length) {
            boolean toggle = horizontal[r][c];
            horizontal[r][c] = !toggle;
        }
    }
    
    /**
     * Toggles the vertical line segment below Dot r,c, if the indices are legal.
     * Otherwise do nothing.
     */
    public void verticalClick(int r, int c)
    {
        // COMPLETE THIS 2b
        if (r >= 0 && r < vertical.length && c >= 0 && c < vertical[0].length) {
            boolean toggle = vertical[r][c];
            vertical[r][c] = !toggle;
        }
    }
    
    /**
     * Clears all line segments out of the current solution.
     */
    public void clear()
    {
        // COMPLETE THIS 3
        for (int r = 0; r < size() + 1; r++)
            for (int c = 0; c < size(); c++) {
                horizontal[r][c] = false;
                vertical[c][r] = false;
            }
    }
}
