import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class KnapsackProblemBranchAndBoundMethod implements Runnable {

	private BufferedReader br = null;
	private PrintWriter pw = null;
	private StringTokenizer stk = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(new KnapsackProblemBranchAndBoundMethod()).run();
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

	int best = Integer.MAX_VALUE;
	TreeSet<Integer> rk1 = new TreeSet<Integer>();
	TreeSet<Integer> rk2 = new TreeSet<Integer>();
	int rw1 = 0, rw2 = 0;
	
	
	/* a[] - исходный массив с весами, w - масса оставшихся (нераспеределенных) предметов
	 *  w1, w2 - массы предметов в 1 и 2 рюкзаках, i - индекс предмета который мы будем распределять
	 *  k1, k2 - предметы в рюкзаках
	 */
	private void search(int a[], int w, int w1, int w2, int i,
			TreeSet<Integer> k1, TreeSet<Integer> k2) {
		// если все предметы распределили
		if (w == 0) {
			// если текущее распределение лучше того, что мы знаем
			if (Math.abs(w1 - w2) < best) {
				// запоминаем всё об этом распределениии
				best = Math.abs(w1 - w2);
				rk1 = (TreeSet<Integer>) k1.clone();
				rk2 = (TreeSet<Integer>) k2.clone();
				rw1 = w1;
				rw2 = w2;
				return;
			}
		}
		// Есди разница масс в рюкзаках, при условии что мы положем все оставшееся в один больше чем лучшая, выходим
		// это как раз отсечение ветви
		if (Math.abs(w1 - w2) - w >= best) {
			return;
		}
		// Кладем i предмет в 1 рюкзак
		k1.add(a[i]);
		// запускаем перебор для следующего предмета
		search(a, w - a[i], w1 + a[i], w2, i + 1, k1, k2);
		// вынимаем из 1 рюкзака, кладём во второй
		k1.remove(a[i]);
		k2.add(a[i]);
		// запускаем перебор если предмет во втором
		search(a, w - a[i], w1, w2 + a[i], i + 1, k1, k2);
		k2.remove(a[i]);
	}
	/* Три примера для инпута
	12
	1 2 3 4 5 6 7 8 9 10 1 2 

	10
	1 2 3 4 5 6 7 8 9 10 

	5
	1 2 3 4 5
	*/
	
	
	private void solver() {
		int n = ni();
		int a[] = new int[n];
		int sum = 0;
		for (int i = 0; i < n; i++) {
			a[i] = ni();
			sum += a[i];
		}
		TreeSet<Integer> k1 = new TreeSet<Integer>();
		TreeSet<Integer> k2 = new TreeSet<Integer>();
		search(a, sum, 0, 0, 0, k1, k2);
		
		System.out.println("Best diff: " + best + ", weight1 = " + rw1
				+ ", weight2 = " + rw2);
		System.out.print("First knapsack contain: ");
		for (Integer e : rk1) {
			System.out.print(e + " ");
		}
		System.out.println();
		System.out.print("Second knapsack contain: ");
		for (Integer e : rk2) {
			System.out.print(e + " ");
		}
	}

	private BigInteger nbi() {
		return new BigInteger(nstr());
	}

	void exit() {
		System.exit(0);
	}

}
