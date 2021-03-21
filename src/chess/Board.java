package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private String strategy = "Warnsdorff";
    private int BoardHeight, BoardWidth;
    public List<Location> visited;
    protected List<Location> dest1, dest2;

    public Board(int width, int height, String strat){
        BoardHeight = height;
        BoardWidth = width;
        if (strat == null){
            if (width >= 6 || height >= 6){
                strategy = "Warnsdorff";
            }
            if (width%2==0 && height%2==0 && (width >= 6 || height >= 6)){
                strategy = "WarnsdorffClosed";
            }
            if (width%4==0 && height%4==0 &&(width >= 10 || height >= 10)){
                strategy = "D&C";
            }
        }else{strategy = strat;}

        dest1 = Arrays.asList(new Location(BoardWidth-2, 1), new Location(2, 3),
                new Location(3, BoardHeight), new Location(BoardWidth-1, BoardHeight-2));
        dest2 = Arrays.asList(new Location(BoardWidth, 2), new Location(1, 1),
                new Location(1, BoardHeight-1), new Location(BoardWidth, BoardHeight));
    }

    public void startTour(Location start){
        switch (strategy){
            case "Warnsdorff":
                Tour2 t = new Tour2(BoardWidth, BoardHeight);
                while(!t.findTour(start, false)){
                    System.out.println("Try again");
                }
                visited = t.visited;
                break;
            case "Backtrack":
                Tour b = new Tour(start, BoardWidth, BoardHeight);
                b.startTour();
                visited = b.visited;
                break;
            case "WarnsdorffClosed":
                Tour2 t2 = new Tour2(BoardWidth, BoardHeight);
                while(!t2.findTour(start, true)){
                    System.out.println("Try again");
                }
                visited = t2.visited;
                break;
            case "D&C":
                divideConquer d = new divideConquer(BoardWidth, BoardHeight);
                d.conquerTour(start);
                visited = d.visited;
                break;
        }
    }


    protected void rotate(){
        /*
        Flip completed board by 90 degree
         */
        ArrayList<Location> newVisited = new ArrayList<>();
        for (Location loc: visited){
            newVisited.add(new Location(loc.y(), BoardWidth-loc.x()+1));
        }
        visited = newVisited;
    }


    protected boolean pStructured(int quarter){
        /*
        Check if the board's structure is suitable for use with divide and conquer,
        based on which quarter the board is on
         */
        return visited.indexOf(dest1.get(quarter))+1==visited.indexOf(dest2.get(quarter))||
                visited.indexOf(dest2.get(quarter))+1==visited.indexOf(dest1.get(quarter));
    }


    public static void main(String[] args) {
        Location start = new Location(1, 1);
        // create a Tour object on the next line
        Board t = new Board(8, 8, null);
        long startTime = System.nanoTime();
        t.startTour(start);
        long endTime = System.nanoTime();
        System.out.println("Time elapsed: " + (endTime-startTime)/1000000 + " milliseconds");
    }
}
