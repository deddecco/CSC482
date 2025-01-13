import java.util.HashMap;


// solves LeetCode 790
// 39 / 39 testcases passed (on LeetCode)
public class DominoTromino {
     private static final int MOD = 1000000007;

     public static void main(String[] args) {
          DominoTromino dt = new DominoTromino();

          int numTilings = dt.numTilings(3);
          System.out.println(numTilings);
     }

     public int numTilings(int n) {
          // base cases-- one way to do nothing, 2 ways to do 2x2, 5 ways to do 3x2
          if (n == 0) {
               // do nothing
               return 1;
          }
          if (n == 1 || n == 2) {
               // 2x1 is only OK in the orientation of the grid
               // 2x2 lets the dominos be upright or flat
               return n;
          }
          if (n == 3) {
               // trominos that look like "PJ" or "L7" = 2
               // upright, 2 stacked flat = 1
               // 2 stacked flat, upright = 1
               // 3 upright = 1
               // 2 + 1 + 1 + 1 = 5
               return 5;
          }


          // construct the map from the base cases
          HashMap<Integer, Integer> f = new HashMap<>();
          f.put(0, 1);
          f.put(1, 1);
          f.put(2, 2);
          f.put(3, 5);

          // for 2x4 or greater, the number is 2x how many for n-1 + how many for n-3
          for (int i = 4; i <= n; i++) {
               int value = (int) (((2L * f.get(i - 1)) + f.get(i - 3)) % MOD);
               // once a value is computed, put it into the map
               f.put(i, value);
          }

          return f.get(n);
     }
}