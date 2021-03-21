package chess;

import java.util.ArrayList;

public class Tour3 {
	protected static int x, y;
	protected static Location curr;
	protected static String piece;
	protected static String strategy;
	protected static ArrayList<Location> visited = new ArrayList<>();

	/** Initializes the Tour and subclasses.
	 * 
	 * @param x is the width of the board.
	 * @param y is the height of the board.
	 * @param piece is the type of chess piece. (Knight, Bishop, etc.)
	 * @param strategy is the type of strategy. (Warnsdoff, Brute force, etc.)
	 */
	public void setTour(int x, int y, String piece, String strategy) {
		Tour3.x = x;
		Tour3.y = y;
		Tour3.piece = piece;
		Tour3.strategy = strategy;
	}

	/** Starts tour on point and sets point to be occupied.
	 * 
	 * @param loc
	 */
	public void startTour(Location loc) {
		Tour3.curr = loc;
		Tour3.visited.add(loc);
	}

	/** Checks if another move can be made by counting moves.
	 * 
	 * @return if another move can be made.
	 */
	public boolean hasNext() {
		Piece p = new Piece();
		int numMoves = p.nextMovesCount(Tour3.curr);
		return (numMoves > 0);
	}

	/** Finds next move based on strategy and piece.
	 * 
	 * @return the next move.
	 */
	public Location next() {
		Strategy s = new Strategy();
		Location nextMove = s.nextMove();

		Tour3.curr = nextMove;
		Tour3.visited.add(nextMove);
		return nextMove;
	}
}
