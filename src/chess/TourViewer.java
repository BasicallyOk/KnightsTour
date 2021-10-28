package chess;

import princeton.introcs.StdDraw;

public class TourViewer extends Thread{

	/**
	 * Draws a regular rectangular chess board of the specified size.
	 * 
	 * <p>
	 * Students will need to modify this method to draw irregular boards
	 * if their solutions allows for irregular boards. 
	 * 
	 * @param width the number of squares in the width of the board
	 * @param height the number of squares in the height of the board
	 */
	public static void drawBoard(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException();
		}
		int max = Math.max(width, height);
		StdDraw.setScale(0.5, max + 0.5);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if ((i + j) % 2 == 0) {
					StdDraw.setPenColor(StdDraw.BLUE);
				} else {
					StdDraw.setPenColor(StdDraw.WHITE);
				}
				StdDraw.filledSquare(i + 1, j + 1, 0.5);
			}
		}
	}


	public static void main(String[] args) throws Exception {
		// edit the next line to draw a board of the size that you are testing
		int width = 256;
		int height = 256;

		Thread draw = new Thread(){
			public void run(){
				drawBoard(width, height);
				StdDraw.setPenColor(StdDraw.BLACK);
			}
		};

		long startTime = System.currentTimeMillis();
		Location start = new Location(5, 5);
		Board t = new Board(width, height, null);
		Thread findTour = new Thread(){
			public void run(){
				// create a Tour object on the next line
				t.startTour(start);
			}
		};
		// Start the threads
		draw.start();
		findTour.start();
		draw.join();
		findTour.join();
		long endTime = System.currentTimeMillis();

		for(int u = 0; u < t.visited.size()-1; u++){
			Location curr = t.visited.get(u);
			Location next = t.visited.get(u+1);

			System.out.println(u + " : moving from " + curr + " to " + next);

			StdDraw.line(curr.x(), curr.y(), next.x(), next.y());
			StdDraw.filledCircle(next.x(), next.y(), 0.1);
		}
		System.out.println("Time elapsed:" + (endTime-startTime) + " milliseconds");
	}
}
