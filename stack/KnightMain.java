package stack;

import stack.Location;
import stack.StackList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.*;


public class KnightMain {

    final static int rowL = 100; //number of rows for chess board
    final static int colL = 100; //number of cols for chess board
    static StackList<Location> stack = new StackList<Location>(); //store moves in order (backtrack capability)

    //list of exhausted locations for each location.  Must use method convertLocToIndex to find a Locations proper index
    static ArrayList<ArrayList<Location>> exhausted = new ArrayList<ArrayList<Location>>(rowL*colL); //Changed initialCapacity to 25 from 64
    static int board[][] = new int[rowL][colL]; //2d array used to store the order of moves
    static boolean visited[][] = new boolean[rowL][colL]; //2d array used to store what locations have been used
    static long startTime;
    static long endTime;
    static int numOfLoops = 0;
    static int numOfIfs = 0;
    static int sumOfSpots = 0;
    static int spotSum = 0;
    static int numOfRewind = 0;
    static Location startLoc;
    static Location currentLoc;
    static Location destination;
    private static int counter = 1;

    public static void main(String[] args) {
        initExhausted();
        obtainStartLoc();
        System.out.println("START: " + startLoc);

        startTime = System.currentTimeMillis();
        stack.push(startLoc);
        currentLoc = startLoc;
        destination = getNextMove(currentLoc, getPossibleMoves(currentLoc));
        board[currentLoc.row][currentLoc.col] = counter;
        visited[currentLoc.row][currentLoc.col] = true;
        counter++;

        //printBoard();
        //printVisited();
        //System.out.println();
        //System.out.println();

        while(stack.size() != rowL * colL && stack.size() != 0) {
            //System.out.println();
            //System.out.println("Current Location: " + currentLoc);
            currentLoc = destination;
            destination = getNextMove(currentLoc, getPossibleMoves(currentLoc));

            //if getNextMove returns null, start rewind sequence
            while (destination == null) {
                //System.out.println();
                //System.out.print("Rewinding! ");
                numOfRewind++;

                clearExhausted(currentLoc);
                board[currentLoc.row][currentLoc.col] = 0;
                visited[currentLoc.row][currentLoc.col] = false;
                stack.pop();

                if (stack.peek() != null) {
                    currentLoc = stack.peek();
                    destination = getNextMove(currentLoc, getPossibleMoves(currentLoc));
                    counter--;
                    numOfIfs++;
                }
                else {
                    stack.push(startLoc);
                    currentLoc = stack.peek();
                    destination = getNextMove(currentLoc, getPossibleMoves(currentLoc));
                    counter = 1;
                }
                numOfLoops++;
            }

            board[currentLoc.row][currentLoc.col] = counter;
            visited[currentLoc.row][currentLoc.col] = true;
            //printBoard();
            //System.out.println();
            //printVisited();
            //System.out.println();
            //System.out.println();
            counter++;
            numOfLoops++;
        }

        endTime = System.currentTimeMillis();
        System.out.println("Total time (ms): " + (endTime-startTime));
        System.out.println("Number of loops: " + numOfLoops);
        System.out.println("Number of ifs: " + numOfIfs);
        System.out.println("Number of rewinds: " + numOfRewind);

        currentLoc = destination;
        board[currentLoc.row][currentLoc.col] = counter;
        visited[currentLoc.row][currentLoc.col] = true;

        for (int j = 0; j <= rowL*colL; j++) {
            sumOfSpots += j;
        }
        for (int i = 0; i < colL; i++) {
            for (int j = 0; j < rowL; j++) {
                spotSum += board[i][j];
            }
        }

        if (spotSum == sumOfSpots) {
            System.out.println("Simulation completed successfully");
            printBoard();
        }
        else {
            System.out.println("Simulation has no solution.");
        }

        //printVisited();
    }

    //printed out the exhausted list for a given Location
    public static void printExhausedList(Location loc) {
        ArrayList<Location> tempEList = exhausted.get(convertLocToIndex(loc));
        for (int i = 0; i < tempEList.size(); i++) {
            tempEList.get(i).toString();
        }
    }

    //prints out the possible move locations for a given Location
    public static void printPossibleMoveLocations(Location loc) {
        ArrayList<Location> tempLocs = new ArrayList<>();
        tempLocs = getPossibleMoves(loc);
        System.out.print("Possible Moves: ");
        for (int i = 0; i < tempLocs.size(); i++) {
            System.out.print(tempLocs.get(i).toString());
        }
    }

    //prints out the board (numbers correspond to moves)
    public static void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] < 10) {
                    System.out.print("[" + board[i][j] + "]  ");
                }
                else {
                    System.out.print("[" + board[i][j] + "] ");
                }
            }
            System.out.println();
        }
    }

    // prints out true/false for what spaces have been visited
    public static void printVisited() {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                System.out.print("  " + visited[i][j]);
            }
            System.out.println();
        }
    }

    /* clear out the exhausted list for a given Location
    This needs to be done everytime a Location is removed from the Stack */
    public static void clearExhausted(Location loc) {
        //ArrayList<Location> emptyList = new ArrayList<>();
        exhausted.get(convertLocToIndex(loc)).clear();
        //System.out.println("Clearing Exhausted!");
    }

    //set up the exhausted list with empty exhausted lists.
    public static void initExhausted()
    {
        for (int i = 0; i < rowL * colL; i++) {
            exhausted.add(i, new ArrayList<Location>());
        }
    }

    //is this dest Location exhausted from the source Location
    public static boolean inExhausted(Location source, Location dest) {
        ArrayList<Location> tempEList = exhausted.get(convertLocToIndex(source));
        for (int i = 0; i < tempEList.size(); i++) {
            if (tempEList.get(i).row == dest.getRow() && tempEList.get(i).col == dest.getCol()) {
                //System.out.println(dest.toString() + " is in exhausted list!!");
                numOfIfs++;
                return true;
            }
            numOfLoops++;
        }
        /*System.out.print(dest.toString() + " is not in exhausted list!!");
        if (visited[dest.row][dest.col] == true) {
            System.out.println("...and is already occupied");
        }
        else {
            System.out.println();
        }*/
        return false;
    }

    //passes in location + possible moves list
    public static Location getNextMove(Location loc, ArrayList<Location> list) {
        int length = 9;
        int length2 = 0;
        Location best = null;
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Location> locations2;

        /*System.out.println("Checked Location: " + list.get(j).toString());
        System.out.println("List size of " + list.get(j).toString() + " is: " + getPossibleMoves(list.get(j)).size());
        length = getPossibleMoves(list.get(j)).size();
        best = list.get(j);*/

        for (int j = 0; j < list.size(); j++) {
            if ((!inExhausted(loc, list.get(j))) && (visited[list.get(j).row][list.get(j).col] == false)) {
                locations.add(list.get(j));
                numOfIfs++;
            }
            numOfLoops++;
        }


        for (int i = 0; i < locations.size(); i++) {
            locations2 = getPossibleMoves(locations.get(i));
            for (int j = 0; j < locations2.size(); j++) {
                if ((visited[locations2.get(j).row][locations2.get(j).col] == false)) {
                    length2++;
                    numOfIfs++;
                }
            }
            if (length2 < length) {
                best = locations.get(i);
                length = length2;
                numOfIfs++;
            }
            length2 = 0;
            numOfLoops++;
        }

        if (best != null) {
            //System.out.println("Moving To: " + best.toString());
            addToExhausted(loc, best);
            stack.push(best);
            numOfIfs++;
        }
        return best;
    }

    /*
     * converts a (row,col) to an array index for use in the exhausted list
     */
    public static int convertLocToIndex(Location loc)
    {
        return (loc.row*rowL) + loc.col;
    }

    /*
     * adds a dest Location in the exhausted list for the source Location
     */
    public static void addToExhausted(Location source, Location dest) {
        exhausted.get(convertLocToIndex(source)).add(dest);
        /*System.out.print("Exhausted List: ");
        for (int i = 0; i < exhausted.get(convertLocToIndex(source)).size(); i++) {
            System.out.print(exhausted.get(convertLocToIndex(source)).get(i).toString());
        }
        System.out.println();*/
    }

    /*
     * is this Location a valid one
     */
    public static boolean isValid(Location loc) {
        if (loc.getRow() < 0 || loc.getRow() > rowL-1 || loc.getCol() < 0 || loc.getCol() > colL-1) {
            numOfIfs++;
            return false;
        }
        else {
            return true;
        }
    }

    /*
     * returns another Location for the knight to move in.  If no more possible move
     * locations exist from Location loc, then return null
     */
    public static ArrayList<Location> getPossibleMoves(Location loc) {
        ArrayList<Location> locations = new ArrayList<>();
        String locationStr = "";

        if (isValid(new Location(loc.row+2,loc.col+1))) {
            locations.add(new Location(loc.row+2,loc.col+1));
            numOfIfs++;
        }
        if (isValid(new Location(loc.row+1,loc.col+2))) {
            locations.add(new Location(loc.row+1,loc.col+2));
            numOfIfs++;
        }
        if (isValid(new Location(loc.row+2,loc.col-1))) {
            locations.add(new Location(loc.row+2,loc.col-1));
            numOfIfs++;
        }
        if (isValid(new Location(loc.row+1,loc.col-2))) {
            locations.add(new Location(loc.row+1,loc.col-2));
            numOfIfs++;
        }
        if (isValid(new Location(loc.row-1,loc.col+2))) {
            locations.add(new Location(loc.row-1,loc.col+2));
            numOfIfs++;
        }
        if (isValid(new Location(loc.row-1,loc.col-2))) {
            locations.add(new Location(loc.row-1,loc.col-2));
            numOfIfs++;
        }
        if (isValid(new Location(loc.row-2,loc.col-1))) {
            locations.add(new Location(loc.row-2,loc.col-1));
            numOfIfs++;
        }
        if (isValid(new Location(loc.row-2,loc.col+1))) {
            locations.add(new Location(loc.row-2,loc.col+1));
            numOfIfs++;
        }

        /*System.out.print("Possible Locations:");
        for (int i = 0; i < locations.size(); i++){
            System.out.print(" " + locations.get(i).toString());
        }
        System.out.println();*/
        return locations;
    }

    /*
     * prompt for input and read in the start Location
     */
    public static void obtainStartLoc() {
        int row;
        int col;
        String start = JOptionPane.showInputDialog("Enter a start location (row,col)");

        row = Integer.parseInt(start.substring(1,start.indexOf(",")));
        col = Integer.parseInt(start.substring(start.indexOf(",")+1,start.length()-1));
        startLoc = new Location(row,col);
    }

}