import java.util.LinkedList;

/**
 * Общий метод решения задачи о N ферзях
 **/
public class Queens {

	public static void main(String[] args) {
		int n = 8;
		// Разделить N на 12 и запомнить остаток (N будет равно 8 для задачи о
		// восьми ферзях).
		int remainder = n % 12;

		LinkedList<Integer> ls = new LinkedList<Integer>();
		// Занести в список все четные числа от 2 до N по порядку.
		for (int i = 2; i <= n; i += 2) {
			ls.add(i);
		}

		// Если остаток равен 3 или 9, перенести 2 в конец списка.
		if (remainder == 3 || remainder == 9) {
			ls.remove(Integer.valueOf(2));
			ls.add(2);
		}

		// Добавить в список все нечетные числа от 1 до N по порядку, но, если
		// остаток равен 8, перевернуть пары соседних чисел (например: 3, 1, 7,
		// 5, 11, 9, …).
		if (remainder == 8) {
			for (int i = 1; i <= n; i += 4) {
				ls.add(i + 2);
				ls.add(i);
			}
		} else {
			for (int i = 1; i <= n; i += 2) {
				ls.add(i);
			}
		}

		// Если остаток равен 2 и N ≥ 3, поменять местами 1 и 3, затем, если N ≥
		// 5, перенести 5 в конец списка.
		if (remainder == 2 && n >= 3) {
			int pos_1 = ls.indexOf(1), pos_3 = ls.indexOf(3);
			ls.set(pos_1, 3);
			ls.set(pos_3, 1);
			if (n > 5) {
				ls.remove(5);
				ls.add(5);
			}
		}

		// Если остаток равен 3 или 9, переместить 1 и 3 (именно в этом порядке,
		// а не в котором они сейчас) в конец списка.
		if (remainder == 3 || remainder == 9) {
			ls.remove(1);
			ls.remove(3);
			ls.add(1);
			ls.add(3);
		}

		// Разместить ферзя в первом столбце и в строке с номером, равным
		// первому элементу списка, затем поместить следующего ферзя во втором
		// столбце и в строке с номером, равным второму элементу списка, и т.д.

		int a[][] = new int[n][n];
		for (int i = 0; i < n; i++) {
			System.out.print(ls.get(i) + " ");
			a[i][ls.get(i) - 1] = 1;
		}
		System.out.println();
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j < n; j++) {
				if (a[j][i] == 0) {
					System.out.print("-");
				} else {
					System.out.print("Ф");
				}
			}
			System.out.println();
		}

	}

}
