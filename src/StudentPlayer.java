public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }



    @Override
    public int step(Board board) {
        System.out.println(this.playerIndex);
        return evaluatePosition(board);
    }

    private int evaluatePosition(Board board){
        return 0;
    }

}
