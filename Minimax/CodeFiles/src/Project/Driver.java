package Project;


public class Driver {
	public static char[][] board = new char[3][3];
	
	
	public static void main(String[] args) {
		
	}
	
	
	
	public static char PWFMove(int x, int y, char player) {
		board[y][x] = player;
		
		return evaluation();
	}
	
	
	
	private static char evaluation() {
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
				return board[n1[0]][n1[1]];
			}
		}
		
		return ' ';
	}
	
	private static int[] mapping(int n) {
    	int x = n%3;
    	int y = n/3;
    	
    	int[] arr = {x, y};
    	return arr;
    }
}

