package Project;

public class ComputerTurn {

	final static char computer = 'o';
	final static char human = 'x';

	static Boolean hasMoves(char board[][]) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (board[i][j] == ' ')
					return true;
		return false;
	}

	private static int[] mapping(int n) {
    	int x = n%3;
    	int y = n/3;
    	
    	int[] arr = {x, y};
    	return arr;
    }

	
	static int evaluation(char board[][]) {
		int[][] win = {
				{0, 1, 2},
				{3, 4, 5},
				{6, 7, 8},
				{0, 3, 6},
				{1, 4, 7},
				{2, 5, 8},
				{0, 4, 8},
				{2, 4, 6}
			};
		
		for (int i = 0; i < win.length; i++) {
			int[] n1 = mapping(win[i][0]);
			int[] n2 = mapping(win[i][1]);
			int[] n3 = mapping(win[i][2]);
			if (board[n1[0]][n1[1]] != ' ' && board[n1[0]][n1[1]] == board[n2[0]][n2[1]] && board[n2[0]][n2[1]] == board[n3[0]][n3[1]]) {
				if (board[n1[0]][n1[1]] == computer) return 10;
				else return -10;
			}
		}
		
		return 0;
	}

	static int minimax(char board[][], Boolean isMax) {
		int score = evaluation(board);

		if (score != 0)
			return score;

		if (hasMoves(board) == false)
			return 0;

		if (isMax) {
			int best = -Integer.MAX_VALUE;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {

					if (board[i][j] == ' ') {
						board[i][j] = computer;

						best = Math.max(best, minimax(board, false));

						board[i][j] = ' ';
					}
				}
			}
			return best;
		}

		else {
			int best = Integer.MAX_VALUE;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					
					if (board[i][j] == ' ') {
						board[i][j] = human;

						best = Math.min(best, minimax(board, true));

						board[i][j] = ' ';
					}
				}
			}
			return best;
		}
	}

	public static Move findBestMove(char board[][]) {
		int bestVal = -Integer.MAX_VALUE;
		Move bestMove = new Move();
		bestMove.row = -1;
		bestMove.col = -1;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (board[i][j] == ' ') {

					board[i][j] = computer;

					int moveVal = minimax(board, false);

					board[i][j] = ' ';

					if (moveVal > bestVal) {
						bestMove.row = i;
						bestMove.col = j;
						bestVal = moveVal;
					}
				}
			}
		}

		return bestMove;
	}
}
