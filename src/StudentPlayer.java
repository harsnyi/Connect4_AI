
public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }


    @Override
    public int step(Board board) {
        return minimax(10,board,true,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }


    private int minimax(int depth, Board board, boolean maximizingPlayer,int alpha, int beta) {

        if(board.gameEnded()){
            return maximizingPlayer ? 100 : -100;
        }

        if (depth == 0) {
            return calculate(board);
        }

        if (maximizingPlayer) {
            int maxEvaluation = Integer.MIN_VALUE;
            int bestMove = -1;
            for (int i:board.getValidSteps()) {
                if (board.stepIsValid(i)) {
                    board.step(2, i);
                    int j = board.getLastPlayerRow();
                    int evaluation = minimax(depth - 1, board, false,alpha,beta);
                    board.getState()[j][i] = 0;
                    if (evaluation > maxEvaluation) {
                        maxEvaluation = evaluation;
                        bestMove = i;
                    }
                    alpha = Math.max(alpha,maxEvaluation);
                    if (alpha >= beta){
                        break;
                    }
                }
            }
            return bestMove;
        } else {
            int minEvaluation = Integer.MAX_VALUE;
            int bestMove = -1;
            for (int i:board.getValidSteps()) {
                if (board.stepIsValid(i)) {
                    board.step(1, i);
                    int j = board.getLastPlayerRow();
                    int evaluation = minimax(depth - 1, board,true,alpha,beta);
                    board.getState()[j][i] = 0;
                    if (evaluation < minEvaluation) {
                        minEvaluation = evaluation;
                        bestMove = i;
                    }
                    beta = Math.min(beta,minEvaluation);
                    if (alpha >= beta){
                        break;
                    }
                }
            }
            return bestMove;
        }
    }

    int isValidPlace(Board b, int row , int col){
        if(row < 6 && row > -1 && col < 7 && col > -1){
            return b.getState()[row][col];
        }
        else
            return -1;
    }

    public int calculate(Board board){
        int row = board.getLastPlayerRow();
        int col = board.getLastPlayerColumn();
        int score = 0;

        // UNDER
        if(isValidPlace(board, row  + 1 , col) == board.getLastPlayerIndex()) {
            score += 2;
            if(isValidPlace(board, row  + 2 , col) == board.getLastPlayerIndex())
                score += 2;
        }
        if(isValidPlace(board, row + 1 , col - 1) == board.getLastPlayerIndex()) {
            score += 2;
            if(isValidPlace(board, row + 2 , col - 2) == board.getLastPlayerIndex()) {
                score += 2;
            }else if(isValidPlace(board, row + 2, col - 2) == 0)
                score += 1;
        }else if(isValidPlace(board, row + 1, col - 1) == 0){
            score += 1;
            if(isValidPlace(board, row + 2, col - 2) == board.getLastPlayerIndex())
                score += 1;
        }

        if(isValidPlace(board, row + 1 , col + 1) == board.getLastPlayerIndex()) {
            score += 2;
            if (isValidPlace(board, row + 2, col + 2) == board.getLastPlayerIndex()) {
                score += 2;
            }else if(isValidPlace(board, row + 2, col + 2) == 0)
                score += 1;
        }else if(isValidPlace(board, row + 1, col + 1) == 0){
            score += 1;
            if(isValidPlace(board, row + 2, col + 2) == board.getLastPlayerIndex())
                score += 1;
        }

        // IN ROW
        if(isValidPlace(board, row , col - 1) == board.getLastPlayerIndex()) {
            score += 2;
            if(isValidPlace(board, row , col - 2) == board.getLastPlayerIndex()) {
                score += 2;
            }else if(isValidPlace(board, row , col - 2) == 0)
                score += 1;
        }
        else if(isValidPlace(board, row, col - 1) == 0){
            score += 1;
            if(isValidPlace(board, row, col - 2) == board.getLastPlayerIndex())
                score += 1;
        }

        if(isValidPlace(board, row , col + 1) == board.getLastPlayerIndex()) {
            score += 2;
            if(isValidPlace(board, row , col + 2) == board.getLastPlayerIndex()) {
                score += 2;
            }else if(isValidPlace(board, row , col + 2) == 0)
                score += 1;
        }
        else if(isValidPlace(board, row, col + 1) == 0){
            score += 1;
            if(isValidPlace(board, row, col + 2) == board.getLastPlayerIndex())
                score += 1;
        }

        // UPPER
        if(isValidPlace(board, row  - 1 , col) == 0) {
            score += 1;
            if(isValidPlace(board, row  - 2 , col) == 0)
                score += 1;
        }

        if(isValidPlace(board, row - 1 , col - 1) == board.getLastPlayerIndex()) {
            score += 2;
            if(isValidPlace(board, row - 2 , col - 2) == board.getLastPlayerIndex()) {
                score += 2;
            }else if(isValidPlace(board, row - 2, col - 2) == 0)
                score += 1;
        }else if(isValidPlace(board, row - 1, col - 1) == 0){
            score += 1;
            if(isValidPlace(board, row - 2, col - 2) == board.getLastPlayerIndex())
                score += 1;
        }

        if(isValidPlace(board, row - 1 , col + 1) == board.getLastPlayerIndex()) {
            score += 2;
            if(isValidPlace(board, row - 2 , col + 2) == board.getLastPlayerIndex()) {
                score += 2;
            }else if(isValidPlace(board, row - 2, col + 2) == 0)
                score += 1;
        }else if(isValidPlace(board, row - 1, col + 1) == 0){
            score += 1;
            if(isValidPlace(board, row - 2, col + 2) == board.getLastPlayerIndex())
                score += 1;
        }

        return board.getLastPlayerIndex() == 2 ? score : -score;
    }

}


