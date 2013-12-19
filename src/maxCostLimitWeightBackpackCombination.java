import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class maxCostLimitWeightBackpackCombination implements Runnable {

	private BufferedReader br = null;
	private PrintWriter pw = null;
	private StringTokenizer stk = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(new maxCostLimitWeightBackpackCombination()).run();
	}

	public void run() { /*
						 * try { // br = new BufferedReader(new
						 * FileReader("input.txt")); pw = new
						 * PrintWriter("output.txt"); } catch
						 * (FileNotFoundException e) { e.printStackTrace(); }
						 */
		br = new BufferedReader(new InputStreamReader(System.in));
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

	public class CombinationGenerator {

		private int[] a;
		private int n;
		private int r;
		private BigInteger numLeft;
		private BigInteger total;

		// Конструктор
		public CombinationGenerator(int n, int r) {
			// Валидатор если входные данные некорректны
			if (r > n) {
				throw new IllegalArgumentException();
			}
			if (n < 1) {
				throw new IllegalArgumentException();
			}
			// заполняем поля класса
			this.n = n;
			this.r = r;
			a = new int[r];
			BigInteger nFact = getFactorial(n);
			BigInteger rFact = getFactorial(r);
			BigInteger nminusrFact = getFactorial(n - r);
			// считаем сразу число перестановок
			total = nFact.divide(rFact.multiply(nminusrFact));
			// инициализируем начальную перестановку
			reset();
		}

		public void reset() {
			for (int i = 0; i < a.length; i++) {
				a[i] = i;
			}
			// тут лежит сколько осталось перестановок от текущей
			numLeft = new BigInteger(total.toString());
		}

		public BigInteger getNumLeft() {
			return numLeft;
		}

		// есть ли еще перестановки?
		public boolean hasMore() {
			return numLeft.compareTo(BigInteger.ZERO) == 1;
		}

		public BigInteger getTotal() {
			return total;
		}

		// факториал
		private BigInteger getFactorial(int n) {
			BigInteger fact = BigInteger.ONE;
			for (int i = n; i > 1; i--) {
				fact = fact.multiply(new BigInteger(Integer.toString(i)));
			}
			return fact;
		}

		// Получить следующую перестановку от текущей
		public int[] getNext() {
			// если это первый запрос то возвращаем начальную перестановку
			if (numLeft.equals(total)) {
				numLeft = numLeft.subtract(BigInteger.ONE);
				return a;
			}
			// уличная магия, погоняй по дебагу пойми почему так... либо на
			// листочке от начальной перестановки пляши
			int i = r - 1;
			while (a[i] == n - r + i) {
				i--;
			}
			a[i] = a[i] + 1;
			for (int j = i + 1; j < r; j++) {
				a[j] = a[i] + j - i;
			}

			// ну и уменьшаем кол-во оставшихся перестановок на одну
			numLeft = numLeft.subtract(BigInteger.ONE);
			return a;

		}
	}

	private void solver() {
		// n - кол-во предметов | limitWeight - масса которую может поднять
		// рюкзак | m - массив масс | с - массив стоимостей
		// формат входных данных
		// n , limitWeight
		// m1 c1
		// ..
		// mn cn
		int n = ni(), limitWeight = ni();
		int m[] = new int[n], c[] = new int[n];
		for (int i = 0; i < n; i++) {
			m[i] = ni();
			c[i] = ni();
		}
		// ответы
		int maxCost = Integer.MIN_VALUE;
		int resultCombination[] = null;

		for (int i = 1; i <= n; i++) {
			CombinationGenerator cg = new CombinationGenerator(n, i);
			for (int j = 0; j < Long.valueOf(cg.getTotal().toString()); j++) {
				int ans[] = cg.getNext();
				// проверим массу
				if (getWeight(ans, m) <= limitWeight) {
					// проверим стоимость
					if (getCost(ans, c) > maxCost) {
						maxCost = getCost(ans, c);
						resultCombination = ans.clone();
					}
				}
			}
		}
		System.out.println("Max cost = " + maxCost);
		System.out.print("get next items: ");
		for(int i=0; i<resultCombination.length; i++){
			System.out.print(" ("+(resultCombination[i]+1)+", weight: "+m[resultCombination[i]]+" , cost: "+c[resultCombination[i]]+" ) ");
		}

	}

	
	private int getWeight(int ans[], int mass[]) {
		int totalMass = 0;
		for (int i = 0; i < ans.length; i++) {
			totalMass += mass[ans[i]];
		}
		return totalMass;
	}

	public int getCost(int ans[], int cost[]) {
		int totalCost = 0;
		for (int i = 0; i < ans.length; i++) {
			totalCost += cost[ans[i]];
		}
		return totalCost;
	}

	private BigInteger nbi() {
		return new BigInteger(nstr());
	}

	void exit() {
		System.exit(0);
	}

}
