
/**
* AnalyzeSolution methods are used to analyze the state of a Slither Link puzzle, 
* to determine if the puzzle is finished. 
* 
* @authors Davinh Dang (22717235), Jason Ho JiaSheng (22360143)
* @version v1.0
*/
import java.util.*;

public class AnalyzeSolution
{
    /**
     * We don't need to create any objects of class AnalyzeSolution; all of its methods are static.
     */
    private AnalyzeSolution() {}
    
    /**
     * Returns the number of line segments surrounding Square r,c in p.
     * Returns 0 if the indices are illegal.
     */
    public static int linesAroundSquare(Puzzle p, int r, int c)
    {
        // COMPLETE THIS 7
        int n = 0;
        if (r >= 0 && r < p.size() && c >= 0 && c < p.size()) {
            if (p.getHorizontal()[r][c])
                n++;
            if (p.getHorizontal()[r + 1][c])
                n++;
            if (p.getVertical()[r][c])
                n++;
            if (p.getVertical()[r][c + 1])
                n++;
        }
        return n;
    }
    
    /**
     * Returns all squares in p that are surrounded by the wrong number of line segments.
     * Each item on the result will be an int[2] containing the indices of a square.
     * The order of the items on the result is unimportant.
     */
    public static ArrayList<int[]> badSquares(Puzzle p)
    {
        // COMPLETE THIS 8
        ArrayList<int[]> badSqr = new ArrayList<>(); 
        for(int i = 0; i < p.size(); i++)
            for(int j = 0; j < p.size(); j++)
                if (p.getPuzzle()[i][j] != -1)
                    if(p.getPuzzle()[i][j] != linesAroundSquare(p, i, j))
                        badSqr.add(new int[] {i, j});
        return badSqr;
    }
    
    /**
     * Returns all dots connected by a single line segment to Dot r,c in p.
     * Each item on the result will be an int[2] containing the indices of a dot.
     * The order of the items on the result is unimportant.
     * Returns null if the indices are illegal.
     */
    public static ArrayList<int[]> getConnections(Puzzle p, int r, int c)
    {
        // COMPLETE THIS 9
        ArrayList<int[]> dots = new ArrayList<>();
        if (r >= 0 && r < p.size() + 1 && c >= 0 && c < p.size() + 1) {
            if (c < p.size() && p.getHorizontal()[r][c])
                dots.add(new int[] {r, c + 1});
            if (r < p.size() && p.getVertical()[r][c])
                dots.add(new int[] {r + 1, c});
            if (c - 1 >= 0 && p.getHorizontal()[r][c - 1])
                dots.add(new int[] {r, c - 1});
            if (r - 1 >= 0 && p.getVertical()[r - 1][c])
                dots.add(new int[] {r - 1, c});
            return dots;
        }       
        return null;
    }

    /**
     * Returns an array of length 3 whose first element is the number of line segments in the puzzle p, 
     * and whose other elements are the indices of a dot on any one of those segments. 
     * Returns {0,0,0} if there are no line segments on the board. 
     */
    public static int[] lineSegments(Puzzle p)
    {
        // COMPLETE THIS 10
        int[] segments = new int[3];
        int n = 0;
        for(int i = 0; i < p.size() + 1; i++)
            for(int j = 0; j < p.size(); j++)
                if(p.getHorizontal()[i][j]){
                    n++;
                    segments[1] = i;
                    segments[2] = j;
                }
        for(int i = 0; i < p.size(); i++)
            for(int j = 0; j < p.size() + 1; j++)
                if(p.getVertical()[i][j]){
                    n++;
                    segments[1] = i;
                    segments[2] = j;
                }
        segments[0] = n;
        return segments;
    }
    
    /**
     * Tries to trace a closed loop starting from Dot r,c in p. 
     * Returns either an appropriate error message, or 
     * the number of steps in the closed loop (as a String). 
     * See the project page and the JUnit for a description of the messages expected. 
     */
    public static String tracePath(Puzzle p, int r, int c)
    {
        // COMPLETE THIS 11
        int n = 0;
        int i = r;
        int j = c;
        int previousi = r;
        int previousj = c;
        if (getConnections(p, r, c).size() == 0)
            return "No path";
        while (getConnections(p, i, j).size() == 2) {
                int nexti = getConnections(p, i, j).get(0)[0];
                int nextj = getConnections(p, i, j).get(0)[1];
                if (nexti == previousi && nextj == previousj) {
                    nexti = getConnections(p, i, j).get(1)[0];
                    nextj = getConnections(p, i, j).get(1)[1];
                }
                n++;
                previousi = i;
                previousj = j;
                i = nexti;
                j = nextj;
                if (nexti == r && nextj == c)
                    break;
                if (getConnections(p, i, j).size() == 1)
                    return "Dangling end";
                if (getConnections(p, i, j).size() == 3 || getConnections(p, i, j).size() == 4)
                    return "Branching line";
        }
        if (getConnections(p, r, c).size() == 1)
            return "Dangling end";
        if (getConnections(p, r, c).size() == 3 || getConnections(p, r, c).size() == 4)
            return "Branching line";
        return new Integer(n).toString();
    }
    
    /**
     * Returns a message on whether the puzzle p is finished. 
     * p is finished iff all squares are good, and all line segments form a single closed loop. 
     * An algorithm is given on the project page. 
     * See the project page and the JUnit for a description of the messages expected.
     */
    public static String finished(Puzzle p)
    {
        // COMPLETE THIS 12
        boolean numeric = true;
        String traceValue = tracePath(p, lineSegments(p)[1], lineSegments(p)[2]);
        try {
            Integer.parseInt(traceValue);
        } catch (NumberFormatException e) {
            numeric = false;
        }
        if (numeric) {
            int n = Integer.parseInt(traceValue);
            if (lineSegments(p)[0] != n)
                return "Disconnected lines";
        }
        if (traceValue == "Dangling end")
            return "Dangling end";
        if (traceValue == "Branching line")
            return "Branching line";
        if (badSquares(p).size() > 0)
            return "Wrong number";
        return "Finished";
    }
}
