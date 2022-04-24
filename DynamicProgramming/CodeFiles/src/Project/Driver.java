package Project;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;



public class Driver {
	public static ArrayList<Integer> lcs = new ArrayList<Integer>();
	
	public static int[] getArray(File file) throws Exception {
		int[] x = readFile(file);
		
		if (checkIfAllAvailable(x)) {
			return x;
		}
		
		else {
			throw new Exception("Invalid Data!");
		}

	}
	
	
	public static int[] getLCS(File file) throws Exception{
		int[] x;
		x = getArray(file);
	
	
		int number = x[0];
		int[] y = new int[number+1];
		
		for (int i = 1; i < number+1; i++) {
			y[i] = i;
		}
		
		int[][] b = LCS(x, y);
		setLCS(b, x, number, number);
		
		int[] indexes = new int[number];
		
		for (int i = 0; i < lcs.size(); i++) {
			indexes[lcs.get(i)-1] = 1;
		}
		
		return indexes;
	}

	
	@SuppressWarnings("resource")
	private static int[] readFile(File file) throws Exception {
		Scanner sc = new Scanner(file);
		
		int numberOfLeds = sc.nextInt();
		
		int[] ledsOrder = new int[numberOfLeds+1];
		ledsOrder[0] = numberOfLeds;
		
		for (int i = 1; i < numberOfLeds+1; i++) {
			ledsOrder[i] = sc.nextInt();
		}
		
		sc.close();
		return ledsOrder;
	}
	
	
	private static boolean checkIfAllAvailable(int[] list) {
		int number = list[0];
		boolean flag = false;
		
		for (int i = 1; i <= number; i++) {
			flag = false;
			for  (int j = 1; j < number+1; j++) {
				if (i == list[j]) {
					flag = true;
					break;
				}
			}
			if (!flag) break;
		}
		
		return flag;
	}
	
	
	
	
	private static int[][] LCS(int[] X, int[] Y)
	{
		int m = X.length;
		int n = Y.length;
		int[][] C = new int[m][n];
		int[][] B = new int[m][n]; //0 Diameter -- 1 Up -- 2 Left
		
		for (int i = 1; i < m; i++) {
			C[i][0] = 0;
		}
		
		for (int i = 1; i < n; i++) {
			C[0][i] = 0;
		}
		
		
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (X[i] == Y[j]) {
					C[i][j] = C[i-1][j-1] + 1;
					B[i][j] = 0;
				}
				
				else if (C[i][j-1] > C[i-1][j]) {
					C[i][j] = C[i][j-1];
					B[i][j] = 2;
				}
				
				else {
					C[i][j] = C[i-1][j];
					B[i][j] = 1;
				}
			}
		}
		
		return B;
	}
	
	
	private static void setLCS(int[][] B, int[] X, int i, int j) {
		if (i == 0 || j == 0) {
			return;
		}
		
		if (B[i][j] == 0) {
			setLCS(B, X, i-1, j-1);
			lcs.add(X[i]);
		}
		
		else if (B[i][j] == 1) {
			setLCS(B, X, i-1, j);
		}
		
		else {
			setLCS(B, X, i, j-1);
		}
	}
}
