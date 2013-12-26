import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class ShortestPathProblem implements Runnable {

	private BufferedReader br = null;
	private PrintWriter pw = null;
	private StringTokenizer stk = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(new ShortestPathProblem()).run();
	}

	public void run() {
		try {
			br = new BufferedReader(new FileReader("input.txt")); // pw = new
																	// PrintWriter("output.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// br = new BufferedReader(new InputStreamReader(System.in));
		pw = new PrintWriter(new OutputStreamWriter(System.out));
		solver();
		pw.close();

	}

	private void nline() {
		try {
			if (!stk.hasMoreTokens())
				stk = new StringTokenizer(br.readLine());
		} catch (IOException e) {
			throw new RuntimeException("KaVaBUnGO!!!", e);
		}
	}

	private String nstr() {
		while (!stk.hasMoreTokens())
			nline();
		return stk.nextToken();
	}

	private int ni() {
		return Integer.valueOf(nstr());
	}

	private long nl() {
		return Long.valueOf(nstr());
	}

	private double nd() {
		return Double.valueOf(nstr());
	}

	String nextLine() {
		try {
			return br.readLine();
		} catch (IOException e) {
		}
		return null;
	}

	// Input example
	// 5 5
	// 12 9 4 14 8
	// 7 12 10 7 15
	// 11 13 14 18 5
	// 19 8 7 15 12
	// 3 16 3 9 19
	//
	// DP for this example
	// 12 21 25 39 47 
	// 19 31 35 42 57 
	// 30 43 49 60 62 
	// 49 51 56 71 74 
	// 52 67 59 68 87 

	private void solver() {
		int n = ni(), m = ni();
		int a[][] = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				a[i][j] = ni();
			}
		}
		// simple DP
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (i==0 && j>0) a[i][j]+= a[i][j-1];
				if (i>0 && j==0) a[i][j]+= a[i-1][j];
				if (i>0 && j>0) a[i][j] += Math.min(a[i-1][j], a[i][j-1]);
			}
		}
		System.out.println("Result DP table:");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(a[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Anwser is: "+a[n-1][m-1]);
		System.out.println();
		
		//recovery path
		System.out.println("Path:");
		path(n-1, m-1, a);
		
	}
	// get minimal from a[i-1][j] and a[i][j-1]
	private void path(int x, int y, int a[][]){
		if (x+y>0){
			if ((x==0) || ((y>0) && (a[x][y-1]<a[x-1][y]))){
				path(x, y-1, a);
			}
			else {
				path(x-1, y, a);
			}
		}
		System.out.println("["+(x+1)+", "+(y+1)+"]");
	}
	

	
	private BigInteger nbi() {
		return new BigInteger(nstr());
	}

	void exit() {
		System.exit(0);
	}

}
