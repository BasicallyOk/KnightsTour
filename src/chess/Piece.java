package chess;

import java.util.ArrayList;

public class Piece extends Tour3 {
    private int[][] pieceMoves;

    /**
     * Finds appropriate moves 2D list based on chess piece.
     */
    protected Piece() {
        switch (Tour3.piece) {
            case "Knight":
                pieceMoves = knight();
                break;
            default:
                throw new IllegalArgumentException("Invalid piece: " + Tour3.piece);
        }
    }

    /** Finds locations of moves
     * 
     * @param loc Location of piece/potential location.
     * @return potential locations of moves.
     */
    protected ArrayList<Location> nextMoves(Location loc) {
        ArrayList<Location> moves = new ArrayList<>();
        for (int i = 0; i < this.pieceMoves.length; i++) {
            Location nextLoc = new Location(loc.x() + this.pieceMoves[i][0], 
                                            loc.y() + this.pieceMoves[i][1]); // Calculates moved positions.
            
            if (nextLoc.x() > 0 && nextLoc.x() <= Tour3.x
             && nextLoc.y() > 0 && nextLoc.y() <= Tour3.y
             && !visited.contains(nextLoc)) { // Checks if move is valid.
                moves.add(nextLoc);
            }
        }
        return moves;
    }

    /** Finds how many moves can be made at a position.
     * 
     * @param loc Location of piece/potential location.
     * @return amount of moves that can be made.
     */
    protected int nextMovesCount(Location loc) {
        return nextMoves(loc).size();
    }

    /**
     * Knight movement relative to (x,y) location.
     * @return 2D array of movements to be calculated.
     */
    private int[][] knight() {
        return new int[][] { { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }, 
                             { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 } };
    }
}
