
public class StudentPlayer extends Player{
    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }


    @Override
    public int step(Board board) {

        int bestEval = minimax(0,board,3,true);
        System.out.println(minimax(0,board,3,true));
        System.out.println(bestStep(board,bestEval));
        return 0;
    }

    private int bestStep(Board board,int bestEval){
        int step = 0;
        for(int i = 0;i<7;i++){
            if(board.stepIsValid(i)){
                Board b = new Board(board);
                b.step(2,i);
                if(evaluatePosition(b) == bestEval){
                    System.out.println("asd");
                    return i;
                }else{
                    System.out.println(evaluatePosition(b));
                }
            }
        }
        return step;
    }

    private int minimax(int depth,Board board,int height,boolean maximizingPlayer){

        int evaluatedPosition = 0;
        if(depth == height){
            return evaluatePosition(board);
        }
        if(maximizingPlayer){
            int maxEvaluation = -10000;
            for(int i = 0;i<7;i++){
                if(board.stepIsValid(i)){
                    Board b = new Board(board);
                    b.step(2,i);
                    int evaluation = minimax(depth+1,b,height,false);
                    if(evaluation > maxEvaluation){
                        return evaluation;
                    }
                    return maxEvaluation;
                }
            }
        }
        else {
            int minEvaluation = 10000;
            for(int i = 0;i<7;i++){
                if(board.stepIsValid(i)){
                    Board b = new Board(board);
                    b.step(1,i);
                    int evaluation = minimax(depth+1,b,height,true);
                    if(evaluation < minEvaluation){
                        return evaluation;
                    }
                    return minEvaluation;
                }
            }
        }
        return evaluatedPosition;
    }

    /**
     * Utility value = (foursOfTheAI * 10 + threesOfTheAI * 5 + twosOfTheAI * 2) - (opponentFours *10 + opponentThrees * 5 + opponentTwos * 2)
     * @param board
     * @return
     */
    private int evaluatePosition(Board board){

        int opponentIndex = 1;

        int foursOfStudentPlayer = checkForN(4, board.getState(),playerIndex);
        int threesOfStudentPlayer = checkForN(3, board.getState(),playerIndex);
        int twosOfStudentPlayer = checkForN(2, board.getState(),playerIndex);

        int foursOfOpponent = checkForN(4, board.getState(),opponentIndex);
        int threesOfOpponent = checkForN(3, board.getState(),opponentIndex);
        int twosOfOpponent = checkForN(2, board.getState(),opponentIndex);


        return ((foursOfStudentPlayer * 10 + threesOfStudentPlayer * 5 + twosOfStudentPlayer * 2) -
                (foursOfOpponent * 10 + threesOfOpponent * 5 + twosOfOpponent * 2));
    }

    private int checkForN(int N,int[][] state, int playerIndex){
        return (checkForNsDiagonally(N, state,playerIndex) + checkForNsInARow(N, state,playerIndex) + checkForNsInAColumn(N, state,playerIndex));
    }

    /**
     * For checking if there are any possible N-s diagonally.
     * @param N - number of the same numbers diagonally
     * @return the number of possible Ns
     */
    private int checkForNsDiagonally(int N, int[][] state,int playerNumber){

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
    private int checkForNsInARow(int N, int[][] state,int playerNumber){

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
                    rememberedPiece = state[j][i];
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
    private int checkForNsInAColumn(int N, int[][] state,int playerNumber){

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

    private int[][] replicatePosition(){
        int[][] position = new int[boardSize[0]][boardSize[1]];
        return position;
    }

    private void printOutBoard(int [][] state){
        for(int i = 0;i<6;i++){
            for(int j = 0;j<7;j++){
                System.out.print(state[i][j]+ " ");
            }
            System.out.println();
        }
    }

}
