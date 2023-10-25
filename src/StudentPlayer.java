public class StudentPlayer extends Player{

    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    @Override
    public int step(Board board) {
        return minimax(board,10,true, Integer.MIN_VALUE, Integer.MAX_VALUE)[1];
    }

    /**
     * Minimax algorithm for choosing the best step for the Student Player
     * @param board board the game plays on
     * @param depth depth of the minimax algorithm
     * @param alpha
     * @param beta
     * @param playerIndex
     * @return the best step
     */
    public int[] minimax(Board board, int depth, boolean playerIndex, int alpha, int beta) {

        if (board.gameEnded()) {
            return new int[]{board.getLastPlayerIndex() == 2 ? 100 : -100,  board.getLastPlayerColumn()};
        }

        if(depth == 0){
            return new int[]{evaluatePosition(board), board.getLastPlayerColumn()};
        }

        if (playerIndex) {
            int maxValue = Integer.MIN_VALUE;
            int maxStep = -1;
            for (int i : board.getValidSteps()) {
                board.step(2, i);
                int j = board.getLastPlayerRow();
                int playerScore = minimax(board, depth - 1,false, alpha, beta)[0];
                board.getState()[j][i] = 0;
                if(playerScore > maxValue){
                    maxStep = i;
                    maxValue = playerScore;
                }
                alpha = Integer.max(alpha, playerScore);
                if (beta <= alpha)
                    break;
            }
            return new int[]{maxValue, maxStep};

        } else {
            int minValue = Integer.MAX_VALUE;
            int minStep = -1;
            for (int i : board.getValidSteps()) {
                board.step(1, i);
                int j = board.getLastPlayerRow();
                int playerScore = minimax(board, depth - 1,true, alpha, beta)[0];
                board.getState()[j][i] = 0;
                if(playerScore < minValue){
                    minStep = i;
                    minValue = playerScore;
                }
                beta = Integer.min(beta, playerScore);
                if (beta <= alpha)
                    break;
            }
            return new int[]{minValue, minStep};
        }
    }


    int checkValidPosition(Board b, int row , int col){
        if(row < 6 && row > -1 && col < 7 && col > -1){
            return b.getState()[row][col];
        }
        else{
            return -1;
        }
    }

    private int checkDiagonals(Board board,int row, int col,int prefix,int afterfix){
        int playerScore = 0;
        if(checkValidPosition(board, row + prefix, col + afterfix) == board.getLastPlayerIndex()) {
            playerScore += 2;
            if(checkValidPosition(board, row + prefix * 2 , col + afterfix * 2) == board.getLastPlayerIndex()) {
                playerScore += 2;
            }else if(checkValidPosition(board, row + prefix * 2, col + afterfix * 2) == 0)
                playerScore += 1;
        }else if(checkValidPosition(board, row + prefix, col + afterfix) == 0){
            playerScore += 1;
            if(checkValidPosition(board, row + prefix * 2, col +afterfix * 2) == board.getLastPlayerIndex())
                playerScore += 1;
        }

        return playerScore;
    }

    private int checkColumns(Board board, int row, int col, int afterfix){
        int playerScore = 0;
        if(checkValidPosition(board, row , col + afterfix) == board.getLastPlayerIndex()) {
            playerScore += 2;
            if(checkValidPosition(board, row , col + afterfix * 2) == board.getLastPlayerIndex()) {
                playerScore += 2;
            }else if(checkValidPosition(board, row , col + afterfix * 2) == 0)
                playerScore += 1;
        }
        else if(checkValidPosition(board, row, col + afterfix) == 0){
            playerScore += 1;
            if(checkValidPosition(board, row, col + afterfix * 2) == board.getLastPlayerIndex())
                playerScore += 1;
        }
        return playerScore;
    }

    private int checkRows(Board board, int row, int col, int prefix){
        int playerScore = 0;
        if(checkValidPosition(board, row  + prefix, col) == board.getLastPlayerIndex()) {
            playerScore += 2;
            if(checkValidPosition(board, row  + prefix * 2 , col) == board.getLastPlayerIndex())
                playerScore += 2;
        }
        return playerScore;
    }

    /**
     * Function for calculating the score of the player in the given position
     * @param board
     * @return
     */
    public int evaluatePosition(Board board){
        int row = board.getLastPlayerRow();
        int col = board.getLastPlayerColumn();
        int playerScore = 0;

        playerScore += checkRows(board,row,col,1);
        playerScore += checkDiagonals(board,row,col,1,-1);
        playerScore += checkDiagonals(board,row,col,1,1);
        playerScore += checkColumns(board,row,col,-1);
        playerScore += checkColumns(board,row,col,1);
        playerScore += checkRows(board,row,col,-1);
        playerScore += checkDiagonals(board,row,col,-1,-1);
        playerScore += checkDiagonals(board,row,col,-1,1);

        return board.getLastPlayerIndex() == 2 ? playerScore : -playerScore;
    }
}
