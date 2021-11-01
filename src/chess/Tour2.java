package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

public class Tour2 extends Thread{
    private Location curr_loc;
    public List<Location> visited = new ArrayList<>();
    protected int BoardHeight;
    protected int BoardWidth;


    public Tour2(int width, int height){
		/*
		Create board in the form of nxm, however m>=n
		 */
        BoardWidth = width;
        BoardHeight = height;
    }


    public boolean findTour(Location loc, boolean closed){
        curr_loc = loc;
        visited.clear();
        visited.add(curr_loc);
        while (hasNext()){
            Location next = next();
            if (next == null){
                return false;
            }

            curr_loc = next;
            visited.add(next);
        }
        List<Location> list = Arrays.asList(moves(curr_loc));
        return !closed || list.contains(loc); // If the program needs a closed tour, that's what it finds
    }


    private boolean hasNext() {
        if (visited.size() == BoardHeight*BoardWidth){
            System.out.println("done");
            return false;
        }
        return true;
    }


    private boolean valid(Location loc){
        return (loc.x() > 0 && loc.y() > 0 && loc.x()<=BoardWidth && loc.y()<=BoardHeight);
    }


    private boolean available(Location loc){
		/*
		Find out if the location has already been visited
		 */
        return !visited.contains(loc);
    }


    private Location[] moves(Location loc){
        Location loc1 = new Location(loc.x()+2, loc.y()+1);
        Location loc2 = new Location(loc.x()+2, loc.y()-1);
        Location loc3 = new Location(loc.x()-2, loc.y()+1);
        Location loc4 = new Location(loc.x()-2, loc.y()-1);
        Location loc5 = new Location(loc.x()-1, loc.y()+2);
        Location loc6 = new Location(loc.x()+1, loc.y()+2);
        Location loc7 = new Location(loc.x()-1, loc.y()-2);
        Location loc8 = new Location(loc.x()+1, loc.y()-2);

        return new Location[]{loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8};
    }


    private Location[] possible_moves(Location loc){
		/*
		Returns total number of moves possible from loc as well as invalid moves that can be taken

		 */

        ArrayList<Location> possible_loc = new ArrayList<>(Arrays.asList(moves(loc)));

        for (int i = 0; i < possible_loc.size(); i++){
            // Remove impossible locations
            if(!valid(possible_loc.get(i))||!available(possible_loc.get(i))){
                possible_loc.remove(possible_loc.get(i));
            }
        }
        Location[] possible = new Location[possible_loc.size()];

        return possible_loc.toArray(possible);
    }


    private Location next() {
        int start = ThreadLocalRandom.current().nextInt(8);
        int minIndex = -1;
        int minMoves = 8;
        Location[] possible = moves(curr_loc);

        for(int i = 0; i < possible.length; i++){
            // Loop through each moves to determine which one has the least future moves
            int index = (start + i)%8;  // This way, we can make sure that our random number is <8
            Location[] moves = possible_moves(possible[index]);
            int degree = moves.length;

            // Pick using Warnsdorff
            if (valid(possible[index]) && available(possible[index]) && degree < minMoves){
                minIndex = index;
                minMoves = degree;
            }
        }
        if (minIndex == -1 || minMoves < 1){
            return null;
        }
        else{
            return possible[minIndex];
        }
    }


    public static void main(String[] args) {
        Location start = new Location(4, 4);
        Tour2 t = new Tour2(64, 64);
        long startTime = System.currentTimeMillis();
        t.findTour(start, false); // true if you want a closed tour
        for(int u = 0; u < t.visited.size()-1; u++) {
            Location curr = t.visited.get(u);
            Location next = t.visited.get(u + 1);

            System.out.println(u + " : moving from " + curr + " to " + next);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time elapsed:" + (endTime-startTime) + " milliseconds");
    }
}
