import java.util.HashMap;

public class DominoTromino {
     private static final int MOD = 1000000007;
     private final HashMap<Integer, Integer> memo = new HashMap<>();

     public static void main(String[] args) {
          DominoTromino dt = new DominoTromino();
          int numTilings = dt.numTilings(3);
          System.out.println(numTilings);
     }

     public int numTilings(int n) {
          // Base cases
          // there is 1 way to do nothing
          if (n == 0) {
               return 1;
          }
          // 2by1 has to go in the direction of the grid
          // 2by2 can go either 2 flat or 2 upright
          if (n == 1 || n == 2) {
               return n;
          }
          // upright, 2 stacked flats
          // 2 stacked flats, upright
          // 3 upright
          // "PJ" with a tromino
          // "L7" wth a tromino
          if (n == 3) {
               return 5;
          }

          // efficient retrieval-- cut down on duplicate work
          if (memo.containsKey(n)) {
               return memo.get(n);
          }

          // recursive call
          long result = ((long) 2 * numTilings(n - 1) + numTilings(n - 3)) % MOD;
          int value = (int) result;
          memo.put(n, value);
          return value;
     }
}