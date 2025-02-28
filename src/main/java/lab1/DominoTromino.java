package lab1;

import java.util.HashMap;

public class DominoTromino {
     private static final int MOD = 1000000007;
     private final HashMap<Integer, Integer> memo = new HashMap<>();

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
          // upright, 2 stacked flat
          // 2 stacked flats, upright
          // 3 upright
          // "PJ" with 2 trominos
          // "L7" wth 2 trominos
          if (n == 3) {
               return 5;
          }

          // efficient retrieval-- cut down on duplicate work
          if (memo.containsKey(n)) {
               return memo.get(n);
          }

          // recursive call
          // there are two cases-- the well-behaved flat-ended f cases, and the more difficult jagged-emded g cases
          // f is given by f(n) = f(n-1) + f(n-2) (this much is if only dominos) + 2g(n-1) this accounts for
          // the fact that trominos may still be present in a flat-ended solution
          // and g is given by g(n) = g(n-1) + g(n-2)
          // so the total solution is f(n) = 2f(n-1) + f(n-3)
          long result = ((long) 2 * numTilings(n - 1) + numTilings(n - 3)) % MOD;
          int value = (int) result;
          // prevent duplicate work
          memo.put(n, value);
          return value;
     }

     public static void main(String[] args) {
          DominoTromino dt = new DominoTromino();
          int numTilings = dt.numTilings(29);
          System.out.println(numTilings);
     }
}