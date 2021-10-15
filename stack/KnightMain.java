package stack;

import stack.Location;
import stack.StackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.*;


public class KnightMain {

    final static int rowL = 5;//number of rows for chess board
    final static int colL = 5;//number of cols for chess board
    static StackList<Location> stack = new StackList<Location>(); //store moves in order (backtrack capability)

    //list of exhausted locations for each location.  Must use method convertLocToIndex to find a Locations proper index
    static ArrayList<ArrayList<Location>> exhausted = new ArrayList<ArrayList<Location>>(25); //Changed initialCapacity to 25 from 64
    static int board[][] = new int[rowL][colL];//2d array used to store the order of moves
    static boolean visited[][] = new boolean[rowL][colL];//2d array used to store what locations have been used
    static Location startLoc;

    public static void main(String[] args) {

        System.out.println("START");
        initExhausted();
        ArrayList<Location> currentPossible;
        obtainStartLoc();
        System.out.println("Start Loc is " + startLoc);

        stack.push(startLoc);
        visited[startLoc.row][startLoc.col] = true;

        while(stack.size() != rowL * colL && stack.size() != 0)
        {


        }


    }

    /*
     * Printed out the exhausted list for a given Location
     */
    public static void printExhausedList(Location loc)
    {

    }

    /*
     * Prints out the possible move locations for a given Location
     */
    public static void printPossibleMoveLocations(Location loc)
    {

    }

    /*
     * prints out the board (numbers correspond to moves)
     */
    public static void printBoard()
    {

    }

    /*
     * prints out true/false for what spaces have been visited
     */
    public static void printVisited()
    {

    }

    /*
     * clear out the exhausted list for a given Location
     * This needs to be done everytime a Location is removed from the Stack
     */
    public static void clearExhausted(Location loc)
    {

    }

    /*
     * set up the exhausted list with empty exhuasted lists.
     */
    public static void initExhausted()
    {

    }

    /*
     * is this dest Location exhausted from the source Location
     */
    public static boolean inExhausted(Location source, Location dest)
    {
        return false;
    }

    /*
     * returns the next valid move for a given Location on a given ArrayList of possible moves
     */
    public static Location getNextMove(Location loc, ArrayList<Location> list)
    {
        return null;
    }

    /*
     * converts a (row,col) to an array index for use in the exhausted list
     */
    public static int convertLocToIndex(Location loc)
    {
        return (loc.row*5) + loc.col;
    }

    /*
     * adds a dest Location in the exhausted list for the source Location
     */
    public static void addToExhausted(Location source, Location dest)
    {

    }

    /*
     * is this Location a valid one
     */
    public static boolean isValid(Location loc)
    {
        return false;
    }

    /*
     * returns another Location for the knight to move in.  If no more possible move
     * locations exist from Location loc, then return null
     */
    public static ArrayList<Location> getPossibleMoves(Location loc)
    {
        Location tempLoc1 = new Location(loc.row+3,loc.col+2);
        Location tempLoc2 = new Location(loc.row+3,loc.col-2);
        Location tempLoc3 = new Location(loc.row-3,loc.col+2);
        Location tempLoc4 = new Location(loc.row-3,loc.col-2);
        Location tempLoc5 = new Location(loc.row+2,loc.col+3);
        Location tempLoc6 = new Location(loc.row+2,loc.col+3);
        return null;
    }

    /*
     * prompt for input and read in the start Location
     */
    public static void obtainStartLoc()
    {
        int row;
        int col;
        String start = JOptionPane.showInputDialog("Enter a start location (row,col)");

        row = Integer.parseInt(start.substring(1,start.indexOf(",")));
        col = Integer.parseInt(start.substring(start.indexOf(",")+1,start.length()-1));
        startLoc = new Location(row,col);

        System.out.println(startLoc.toString());
    }

}