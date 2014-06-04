import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhegalkinPolynomialSolver implements Runnable {

    private BufferedReader br = null;
    private PrintWriter pw = null;
    private StringTokenizer stk = new StringTokenizer("");

    public static void main(String[] args) {
        new Thread(new ZhegalkinPolynomialSolver()).run();
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

    class ZhegalkinPolynomial {
        int n = 0;
        int lines = 0;
        TruthTable tt;

        ArrayList<ArrayList<Integer>> factorsArray = new ArrayList<ArrayList<Integer>>();

        public ZhegalkinPolynomial(int n, TruthTable tt) {
            this.n = n;
            this.lines = (int) Math.pow(2, n);
            this.tt = tt;
            generateFactors();
            findResultFactors();
            //    printFactors();
        }

        void generateFactors() {
            ArrayList<Integer> factors = new ArrayList<Integer>();
            generateFactorsRec(factors, 0);
            factors.clear();
            generateFactorsRec(factors, 1);
        }

        void printFactors() {
            for (int i = 0; i < factorsArray.size(); i++) {
                for (int j = 0; j < factorsArray.get(i).size(); j++) {
                    System.out.print(factorsArray.get(i).get(j) + " ");
                }
                System.out.println();
            }
        }

        void findResultFactors() {
            String[] factorsStrs = {"C0", "C1" ,"C2", "C3", "C4", "C5", "C6", "C7"};
            System.out.println();
            for (int i = 0; i < factorsArray.size(); i++) {
                if (checkPolynom(factorsArray.get(i))) {
                    System.out.print("Answer: ");
                    for (int j = 0; j < factorsArray.get(i).size(); j++) {
                        System.out.print(factorsStrs[j]+" = " + factorsArray.get(i).get(j) + "; ");
                    }
                    
                }
            }
        }

        void generateFactorsRec(ArrayList<Integer> f, int e) {
            f.add(e);
            if (f.size() == lines) {
                factorsArray.add((ArrayList<Integer>) f.clone());
            } else {
                generateFactorsRec(f, 0);
                f.remove(f.size() - 1);
                generateFactorsRec(f, 1);
                f.remove(f.size() - 1);
            }
        }

        boolean checkPolynom(ArrayList<Integer> factors) {
            boolean ans = true;
            if (n == 2) {
                for (int i = 0; i < tt.booleanMatrix.size(); i++) {
                    if ((factors.get(0) + factors.get(1) * tt.getBooleanMatrix().get(i).get(0) + factors.get(2) * tt.getBooleanMatrix().get(i).get(1)
                            + factors.get(3) * tt.getBooleanMatrix().get(i).get(0) * tt.getBooleanMatrix().get(i).get(1))%2 != tt.getFunctionResult().get(i)) {
                        ans = false;
                    }
                }
            // с0 + с1x1 + c2x2 + c3x3 + c12x1x2 + c13x1x3 + c23x2x3 = c123x1x2x3
            } else if (n==3){
                for (int i = 0; i < tt.booleanMatrix.size(); i++) {
                    if ((factors.get(0) + 
                         factors.get(1) * tt.getBooleanMatrix().get(i).get(0) + 
                         factors.get(2) * tt.getBooleanMatrix().get(i).get(1) + 
                         factors.get(3) * tt.getBooleanMatrix().get(i).get(2) +
                         factors.get(4) * tt.getBooleanMatrix().get(i).get(0) * tt.getBooleanMatrix().get(i).get(1) +
                         factors.get(5) * tt.getBooleanMatrix().get(i).get(0) * tt.getBooleanMatrix().get(i).get(2) + 
                         factors.get(6) * tt.getBooleanMatrix().get(i).get(1) * tt.getBooleanMatrix().get(i).get(2) + 
                         factors.get(7) * tt.getBooleanMatrix().get(i).get(0) * tt.getBooleanMatrix().get(i).get(1) * tt.getBooleanMatrix().get(i).get(2) 
                        )%2 != tt.getFunctionResult().get(i)) {
                        ans = false;
                    }
                }
            }
            return ans;
        }
    }

    class TruthTable {
        private ArrayList<Integer> functionResult = new ArrayList<Integer>();
        private ArrayList<ArrayList<Integer>> booleanMatrix = new ArrayList<ArrayList<Integer>>();
        private int n;

        public TruthTable(int n) {
            this.n = n;
            ArrayList<Integer> a = new ArrayList<Integer>();
            getBoioleanMatrix(a, n, 0);
            a.clear();
            getBoioleanMatrix(a, n, 1);
        }

        void getBoioleanMatrix(ArrayList<Integer> booleanVector, int n, int e) {
            booleanVector.add(e);
            if (booleanVector.size() == n) {
                booleanMatrix.add((ArrayList<Integer>) booleanVector.clone());
            } else {
                getBoioleanMatrix(booleanVector, n, 0);
                booleanVector.remove(booleanVector.size() - 1);
                getBoioleanMatrix(booleanVector, n, 1);
                booleanVector.remove(booleanVector.size() - 1);
            }
        }

        void printBooleanMatrix() {
            System.out.println("Truth table:");
            System.out.println(this.n==3 ? "x   y   z   f(x,y,z)" : "x   y   f(x,y)");
            for (int i = 0; i < booleanMatrix.size(); i++) {
                for (int j = 0; j < booleanMatrix.get(i).size(); j++) {
                    System.out.print(booleanMatrix.get(i).get(j) + "   ");
                }
                System.out.print("  " + functionResult.get(i));
                System.out.println();
            }
        }

        public ArrayList<Integer> getFunctionResult() {
            return functionResult;
        }

        public void setFunctionResult(ArrayList<Integer> functionResult) {
            this.functionResult = functionResult;
        }

        public ArrayList<ArrayList<Integer>> getBooleanMatrix() {
            return booleanMatrix;
        }

        public void setBooleanMatrix(ArrayList<ArrayList<Integer>> booleanMatrix) {
            this.booleanMatrix = booleanMatrix;
        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }
    }

    private void solver() {
        int n = ni();
        TruthTable tt = new TruthTable(n);
        ArrayList<Integer> input = new ArrayList<Integer>();
        for (int i = 0; i < Math.pow(2, n); i++) {
            input.add(ni());
        }
        System.out.println();
        tt.setFunctionResult(input);
        tt.printBooleanMatrix();
        ZhegalkinPolynomial zp = new ZhegalkinPolynomial(n, tt);
    }

    public BigInteger nextRandomBigInteger(BigInteger n) {
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while (result.compareTo(n) >= 0) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }

    private BigInteger nbi() {
        return new BigInteger(nstr());
    }

    void exit() {
        System.exit(0);
    }

}
