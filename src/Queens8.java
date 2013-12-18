import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Queens8 implements Runnable {

	private BufferedReader br = null;
	private PrintWriter pw = null;
	private StringTokenizer stk = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(new Queens8()).run();
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

	// размерность доски
	int n;
	// массив в котором хранится текущее решение (перестановка)
	int v[];
	// число найденных решений
	int it = 0;

	private void solver() {
		n = ni();
		v = new int[n];
		// Поехали! (С)
		search(0);
	}

	/**
	 * search(k) i=0;{инициализация выбора варианта} repeat{выбор варианта}
	 * inc(i); if check (k,i) {подходит} then v[k]=i;{запись варианта} if k<8
	 * {решение неполное} then search(k+1) {ищем дальше} else print(v);
	 * v[k]=0;{стирание варианта} until (i=8); {все перебрали};
	 **/

	// Функция выводит найденное решение на экран
	private void print() {
		System.out.println("---перестановка " + (++it) + "---");
		for (int i = 0; i < n; i++) {
			System.out.print(v[i] + 1 + " ");
		}
		System.out.println();
		System.out.println("---------------------");
		System.out.print(" ");
		for (int i = 0; i < n; i++)
			System.out.print("___");
		System.out.print("__");
		System.out.println();
		for (int i = 0; i < n; i++) {
			System.out.print((n-i)+"|");
			for (int j = 0; j < n; j++) {
				if (v[i] == j) {
					System.out.print(" ♛ ");
				} else {
					System.out.print(" ☐ ");
				}
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.print(" |");
		for (int i = 0; i < n; i++)
			System.out.print("___");
		System.out.print("|");
		System.out.println();
	}

	// Поиск готового решения (собвстенно сам рекурскивный перебор)
	// параметр - номер строки для которой ищем позицию ферзя
	private void search(int k) {
		// i - позиция ферзя в строке k
		int i = 0;
		// перебираем все возможные позиции для ферзя в данной строке
		while (i < n) {
			// если удачная позиция найдена
			if (check(k, i)) {
				// запоминаем
				v[k] = i;
				// если это не последняя строка
				if (k < n - 1) {
					// то ищем позицию ферзя для следующий строки
					search(k + 1);
				} else {
					// если последняя - выводим результат
					print();
				}
				// сюда можем попасть либо если вышли из рекурсии т.е. перебрали
				// все варианты
				// для текущей перестановки (возможно нашли и вывели решения)
				// затираем последнее расположение ферзя и попробуем поискать
				// другое расположение
				v[k] = 0;
			}
			i++;
		}
	}

	// Проверка на то бьют ли два ферзя друг друга
	private boolean check(int k, int pos) {
		// по умолчанию все ок, если не выскочим то будет ок
		boolean isGood = true;
		for (int i = 0; i < k; i++) {
			// если ферзи стоят на одной линии то неудача
			if (v[i] == pos)
				return false;
			// проверяем бьют ли они друг друга по диагонали
			if (checkDiagonal(i, v[i], k, pos))
				return false;
		}

		return isGood;
	}

	// Собственно проверка на удары по диагонали
	private boolean checkDiagonal(int x1, int y1, int x2, int y2) {
		if (Math.abs(x1 - x2) == Math.abs(y1 - y2)) {
			return true;
		} else {
			return false;
		}
	}

	private BigInteger nbi() {
		return new BigInteger(nstr());
	}

	void exit() {
		System.exit(0);
	}

}
