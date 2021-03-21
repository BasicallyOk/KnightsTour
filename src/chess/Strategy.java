package chess;

import java.util.ArrayList;

public class Strategy extends Tour3 {
    private Location pos;

    /** Finds the next move.
     * 
     * @return the next move based on the strategy and piece.
     */
    protected Location nextMove() {
        switch (Tour3.strategy) {
            case "Warnsdorff":
                pos = warnsdorffMove();
                break;
            default:
                throw new IllegalArgumentException("Invalid strategy: " + Tour3.strategy);
        }
        return pos;
    }

    /** Finds next move using Warnsdorff's rule.
     * 
     * @return next move based on warnsdorff's rule.
     */
    private Location warnsdorffMove() {
        Piece p = new Piece();
        ArrayList<Location> unvisited = p.nextMoves(Tour3.curr);

        if (unvisited.isEmpty()) {
            return null;
        }

        int index = 0;
        int minVal = Integer.MAX_VALUE;
        for (int i = 0; i < unvisited.size(); i++) {
            int countMoves = p.nextMovesCount(unvisited.get(i));
            if (minVal >= countMoves && countMoves != 0) {
                minVal = countMoves;
                index = i;
            }
        }
        return unvisited.get(index);
    }
}
