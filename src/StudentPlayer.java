
public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }


    @Override
    public int step(Board board) {

        if(checkForN(3,board,playerIndex) > 0){
            for(int i = 0;i<7;i++){
                if(board.stepIsValid(i)){
                    Board b = new Board(board);
                    b.step(playerIndex, i);
                    if(checkForN(4,b,playerIndex) > 0){
                        return i;
                    }
                }
            }
        }

        if(checkForN(3,board,3-playerIndex) > 0){
            for(int i = 0;i<7;i++){
                if(board.stepIsValid(i)){
                    Board b = new Board(board);
                    b.step(3-playerIndex, i);
                    if(checkForN(4,b,3-playerIndex) > 0){
                        return i;
                    }
                }
            }
        }

        return minimax(7,board,true,-100000,100000);
    }



    private int minimax(int depth, Board board, boolean maximizingPlayer,int alpha, int beta) {
        if (depth == 0 || board.gameEnded()) {
            return evaluatePosition(board);
        }

        if (maximizingPlayer) {
            int maxEvaluation = -100000;
            int bestMove = -1;
            for (int i = 0; i < 7; i++) {
                if (board.stepIsValid(i)) {
                    Board b = new Board(board);
                    b.step(3-playerIndex, i);
                    int evaluation = minimax(depth - 1, b, false,alpha,beta);
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
            int minEvaluation = 100000;
            int bestMove = -1;
            for (int i = 0; i < 7; i++) {
                if (board.stepIsValid(i)) {
                    Board b = new Board(board);
                    b.step(playerIndex, i);
                    int evaluation = minimax(depth - 1, b,true,alpha,beta);
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


    /**
     * Utility value = (foursOfTheAI * 10 + threesOfTheAI * 5 + twosOfTheAI * 2) - (opponentFours *10 + opponentThrees * 5 + opponentTwos * 2)
     * @param board
     * @return
     */
    private int evaluatePosition(Board board){

        int opponentIndex = 3 - playerIndex;
        int foursOfStudentPlayer = checkForN(4, board,playerIndex);
        int threesOfStudentPlayer = checkForN(3, board,playerIndex);
        int twosOfStudentPlayer = checkForN(2, board,playerIndex);
        int centerPiecesOfStudentPlayer = checkForImportantPieces(board.getState(),playerIndex);

        int foursOfOpponent = checkForN(4, board,opponentIndex);
        int threesOfOpponent = checkForN(3, board,opponentIndex);
        int twosOfOpponent = checkForN(2, board,opponentIndex);
        int centerPiecesOfOpponent = checkForImportantPieces(board.getState(),opponentIndex);

        return
                ((foursOfStudentPlayer * 1800 + threesOfStudentPlayer * 300 + twosOfStudentPlayer * 150 + centerPiecesOfStudentPlayer * 80)) -
                        ((foursOfOpponent * 2600 + threesOfOpponent * 300 + twosOfOpponent * 150 + centerPiecesOfOpponent * 80));
    }

    private int checkForN(int N,Board board, int playerIndex){
        return (checkForNsDiagonally(N, board.getState(),board.getLastPlayerIndex(),playerIndex) +
                checkForNsInARow(N, board.getState(),board.getLastPlayerIndex(),playerIndex) +
                checkForNsInAColumn(N, board.getState(),board.getLastPlayerIndex(),playerIndex));
    }

    /**
     * For checking if there are any possible N-s diagonally.
     * @param N - number of the same numbers diagonally
     * @return the number of possible Ns
     */
    private int checkForNsDiagonally(int N, int[][] state,int lastPlayer,int playerNumber){

        int connectedNs = 0;

        //Checking from the start of the table
        for(int i = 0; i<7-N;i++){
            for(int j = 0;j<=6-N;j++){
                int count = 0;
                for(int x = 0;x< N;x++){
                    if(state[j+x][i+x] == playerNumber){
                        count++;
                    }
                }
                if(count == N){
                    connectedNs++;
                }

            }
        }

        //Checking from the end of the table
        for(int i = 6; i>=N-1;i--){
            for(int j = 0;j< 7-N;j++){
                int count = 0;
                for(int x = 0;x< N;x++){
                    if(state[j+x][i-x] == playerNumber){
                        count++;
                    }
                }
                if(count == N){
                    connectedNs++;
                }

            }
        }

        return connectedNs;
    }

    /**
     * For checking if there are any possible N-s in a row.
     * @param N - number of the same numbers in a row
     * @return the number of possible Ns
     */
    private int checkForNsInARow(int N, int[][] state,int lastPlayer,int playerNumber){

        int connectedNs = 0;

        for(int i = 0; i<6;i++){
            int piecesUnder = 0;
            int rememberedPiece = 0;
            for(int j = 0;j<7;j++){
                if(rememberedPiece == 0 && state[i][j] == playerNumber){
                    rememberedPiece =  state[i][j];
                    piecesUnder++;
                }
                else if(rememberedPiece == state[i][j] && state[i][j] == playerNumber){
                    rememberedPiece = state[i][j];
                    piecesUnder++;
                    if(piecesUnder == N){
                        connectedNs++;
                    }
                }
                else if(rememberedPiece != state[i][j]){
                    rememberedPiece = state[i][j];
                    piecesUnder = 1;
                }

            }
        }

        return connectedNs;
    }

    /**
     * For checking if there are any possible N-s in a column.
     * @param N - number of the same numbers in a column
     * @return the number of possible Ns
     */
    private int checkForNsInAColumn(int N, int[][] state,int lastPlayer,int playerNumber){

        int connectedNs = 0;

        for(int i = 0; i<7;i++){
            int piecesUnder = 0;
            int rememberedPiece = 0;
            for(int j = 5;j>=0;j--){
                if(rememberedPiece == 0 && state[j][i] == playerNumber){
                    rememberedPiece =  state[j][i];
                    piecesUnder++;
                }
                else if(rememberedPiece == state[j][i] && state[j][i] == playerNumber){
                    rememberedPiece = state[j][i];
                    piecesUnder++;
                    if(piecesUnder == N){
                        connectedNs++;
                    }
                }
                else if(rememberedPiece != state[j][i]){
                    rememberedPiece = state[j][i];
                    piecesUnder = 1;
                }

            }
        }
        return connectedNs;
    }

    private int checkForImportantPieces(int[][] state,int playerNumber){

        int foundPieces = 0;

        for(int i = 0; i<7;i++){
            for(int j = 0; j<6;j++){

                if(state[j][3] == playerNumber
                        || state[j][4] == playerNumber
                        || state[j][2] == playerNumber){
                    foundPieces ++;
                }
            }
        }
        return foundPieces;
    }



}
