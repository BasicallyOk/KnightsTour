package chess;


import princeton.introcs.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Tour {
	private Location curr_loc;
	public List<Location> visited = new ArrayList<>();
	private LinkedHashMap<Location, ArrayList<Location>> timeline = new LinkedHashMap<>(); // Record the current timeline to avoid making the same mistake
	private int BoardLength;
	private int BoardWidth;

	public Tour(Location loc, int length, int width){
		/*
		Create board in the form of nxm, however m>=n
		 */
		BoardWidth = width;
		BoardLength = length;
		visited.add(loc);
		curr_loc = loc;
		timeline.put(loc, new ArrayList<>());
	}


	public void startTour(){
		long startTime = System.nanoTime();
		int i = 0;
		mainlp: while (hasNext()) {
			Location next = next();
			while(next == null){
				// Backtracks
				if (timeline.get(curr_loc).size() == 8){
					// No possible move from curr_loc in the current timeline that will lead to success
					i--;
					timeline.remove(curr_loc);
					visited.remove(curr_loc);
				}
				if (timeline.size() == 0 || visited.size() == 0){
					System.out.println("Cannot be solved");
					break mainlp;
				}
				curr_loc = visited.get(i);
				next = next();

			}
			System.out.println(i + " : moving from " + curr_loc + " to " + next);

			curr_loc = new Location(next);
			timeline.put(new Location(next), new ArrayList<>());

			// uncomment the next line to slow down the viewer; 500 is the pause time in milliseconds
			// Thread.sleep(500);
			i++;
		}
		long endTime = System.nanoTime();
		System.out.println("Time elapsed: " + (endTime-startTime)/1000000 + " milliseconds");
	}
	
	
	private boolean hasNext() {
		if (visited.size() == BoardLength*BoardWidth){
			System.out.println("done");
			return false;
		}
		return true;
	}


	private boolean valid(Location loc){
		return (loc.x() > 0 && loc.y() > 0 && loc.x()<=BoardWidth && loc.y()<=BoardLength);
	}


	private boolean available(Location loc, Location origin){
		/*
		Find out if the location has already been visited, or visited from origin
		origin is there simply to negate the need to loop through timeline
		origin does not interfere with modularity much
		 */

		return !visited.contains(loc) && (!timeline.containsKey(origin) || !timeline.get(origin).contains(loc));
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


	private int possible_moves(Location loc){
		/*
		Returns total number of moves possible from loc
		 */
		if(!valid(loc)||visited.contains(loc)){
			return 10; // So it will be sorted last always
		}

		ArrayList<Location> possible_loc = new ArrayList<>(Arrays.asList(moves(loc)));

		for (int i = 0; i < possible_loc.size(); i++){
			// Remove impossible locations
			if(!valid(possible_loc.get(i))||visited.contains(possible_loc.get(i))||possible_loc.get(i).equals(loc)){
				possible_loc.remove(possible_loc.get(i));
			}
		}
		return possible_loc.size();
	}


	private void warnsdoff_sort(Location[] arr){
		/*
		Implements a slightly modified selection sort algorithm to sort array by warndoff's rule
		 */
		int n = arr.length;

		// One by one move boundary of unsorted subarray
		for (int i = 0; i < n-1; i++)
		{
			// Find the minimum element in unsorted array
			int min_idx = i;
			for (int j = i+1; j < n; j++)
				if (possible_moves(arr[j]) < possible_moves(arr[min_idx]))
					min_idx = j;

			// Swap the found minimum element with the first
			// element
			Location temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;
		}
	}


	private Location next() {
		Location[] possible_loc = moves(curr_loc);
		warnsdoff_sort(possible_loc);

		for(Location loc: possible_loc){
			Location[] future = moves(loc);
			warnsdoff_sort(future);

			if (visited.contains(loc) || !valid(loc)){
				if(timeline.get(curr_loc) == null || !timeline.get(curr_loc).contains(loc)) {
					timeline.get(curr_loc).add(loc);
				}
				// If it will be stuck, it should be skipped anyways
				else if(valid(loc) && possible_moves(future[0]) <= 1){
					timeline.get(curr_loc).add(loc);
				}
			}

			// If one of the possible moves in the taken after this is a 1, the program will avoid it altogether
			// This is because the only place it can move to is our current next move, and thus leads to a dead end
			if(valid(loc) && available(loc, curr_loc)){
				visited.add(loc);
				timeline.get(curr_loc).add(loc); // If it comes back here it wont do the same thing it did
				return loc;
			}
		}
		return null;
	}


	public static void main(String[] args) {
		Location start = new Location(1, 1);
		// create a Tour object on the next line
		Tour t = new Tour(start, 8, 8);
		t.startTour();

	}
}
