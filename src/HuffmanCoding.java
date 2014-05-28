import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class HuffmanCoding implements Runnable {

    private BufferedReader br = null;
    private PrintWriter pw = null;
    private StringTokenizer stk = new StringTokenizer("");

    public static void main(String[] args) {
        new Thread(new HuffmanCoding()).run();
    }

    public void run() { /*
                         * try { // br = new BufferedReader(new FileReader("input.txt")); pw = new PrintWriter("output.txt"); } catch (FileNotFoundException e)
                         * { e.printStackTrace(); }
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

    // узел дерева
    class Tree_node {
        double probability; // вероятность узла
        Tree_node left; // левое поддерево
        Tree_node right; // правое поддерево

        // конструктор по вероятости для конечных узлов
        public Tree_node(double probability) {
            this.probability = probability;
        }

        // конструктор нового узла по двум другим
        public Tree_node(Tree_node t1, Tree_node t2) {
            this.left = t1;
            this.right = t2;
            this.probability = t1.probability + t2.probability;
        }

        // компаратор по вероятности
        public int compareTo(Tree_node tn) {
            if (this.probability > tn.probability) {
                return 1;
            }
            if (this.probability < tn.probability) {
                return -1;
            }
            return 0;
        }
    }

    /* Компаратор для очереди с приорететами */
    class NodeComparator implements Comparator<Tree_node> {
        public int compare(Tree_node tn1, Tree_node tn2) {
            if (tn1.probability > tn2.probability) {
                return 1;
            }
            if (tn1.probability < tn2.probability) {
                return -1;
            }
            return 0;
        }
    }

    /* Выводим дерево */
    void printNoode(Tree_node node, String code) {
        if (node.left == null && node.right == null) {
            System.out.print("(" + node.probability + " : " + (code.equals("") ? "0" : code) + ") ");
            return;
        }
        printNoode(node.left, code + "0");
        printNoode(node.right, code + "1");

    }

    private void solver() {
        System.out.println("Введите 'lazy' для автоматической генерации инпута или 'hand' для ручного ввода:");
        String mod = nstr();
        ArrayList<Double> inputProbabilities = new ArrayList<Double>();
        if ("lazy".equals(mod)) {
            inputProbabilities = generateInput(); // сгенерировали инпут
        } else {
            System.out.println("Введите кол-во элементов а затем их вероятности через пробел на новой строчке:");
            int n = ni();
            for (int i = 0; i < n; i++) {
                inputProbabilities.add(Double.valueOf(nstr()));
            }
        }
        PriorityQueue<Tree_node> huffmanTree = new PriorityQueue<HuffmanCoding.Tree_node>(inputProbabilities.size(), new NodeComparator()); // создали очередь с приоритетом
        // Заполняем очередь начальными значениями
        for (int i = 0; i < inputProbabilities.size(); i++) {
            huffmanTree.add(new Tree_node(inputProbabilities.get(i)));
        }
        // пока в очереде не останется 1 элемент
        while (huffmanTree.size() > 1) {
            // извлекаем 2 элемента с меньшими вероятностями и формируем новый, кладем в очередь
            huffmanTree.add(new Tree_node(huffmanTree.poll(), huffmanTree.poll()));
        }
        // Обходим дерево и выводим результаты
        printNoode(huffmanTree.peek(), "");
    }

    /* Генерация инпута рандомно */
    ArrayList<Double> generateInput() {
        int n = (int) Math.round(Math.random() * 10 / 3 + 3);
        ArrayList<Double> randomValues = new ArrayList<Double>();
        ArrayList<Double> inputValues = new ArrayList<Double>();
        double sum = 0;
        for (int i = 0; i < n; i++) {
            randomValues.add(Math.random());
            sum += randomValues.get(i);
        }
        System.out.print("input: {");
        for (int i = 0; i < n; i++) {
            inputValues.add(randomValues.get(i) / sum);
            System.out.print(inputValues.get(i));
            if (i != n - 1)
                System.out.print("; ");
        }
        System.out.println("}");
        return inputValues;
    }

    private BigInteger nbi() {
        return new BigInteger(nstr());
    }

    void exit() {
        System.exit(0);
    }

}
