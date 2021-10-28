package chess;

import java.util.NoSuchElementException;

public class divideConquer extends Tour2 {

    private int[] orderX;
    private int[] orderY;
    public divideConquer(int width, int height) {
        super(width, height);
        orderX = new int[]{0, super.BoardWidth / 2, super.BoardWidth / 2, 0};
        orderY = new int[]{super.BoardHeight / 2, super.BoardHeight / 2, 0, 0};

        }


    public Board splitBoard(){
        return new Board(super.BoardWidth/2, super.BoardHeight/2, null);
    }


    public void conquerTour(Location start) {
        Board t = splitBoard();
        t.startTour(start);
        for(int i=0; i<4;i++){
            if(!t.pStructured(i)){
                t.rotate();
            }
            connectBoards(t, i);
        }
    }

    public void connectBoards(Board board, int quarter) throws NoSuchElementException {
        /*
        Only use this when the structure is correct
        Add the locations from each board to the complete visited list, knowing its quarter.
        start is the connection point
         */
        int start = board.visited.indexOf(board.dest1.get(quarter));

        if (board.visited.indexOf(board.dest2.get(quarter)) != start+1){
            for(int i = 0; i < board.visited.size(); i++){
                int index = Math.floorMod(start+i,board.visited.size());
                Location locs = board.visited.get(index);
                visited.add(new Location(locs.x()+orderX[quarter], locs.y()+orderY[quarter]));
            }
        }else{
            for(int i = board.visited.size()-1; i >= 0; i--){
                int index = Math.floorMod(i - board.visited.size() + start +1, board.visited.size());
                Location locs = board.visited.get(index);
                visited.add(new Location(locs.x()+orderX[quarter], locs.y()+orderY[quarter]));
            }
        }
    }
}
