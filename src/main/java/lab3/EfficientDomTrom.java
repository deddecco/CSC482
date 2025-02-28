package lab3;

class Solution {
     private static final int MOD = 1_000_000_007;

     public int numTilings(int n) {
          if (n == 1) return 1;
          if (n == 2) return 2;

          // Base matrix for the recurrence relation
          long[][] baseMatrix = {
                  {2, 1},
                  {1, 0}
          };

          // Compute (baseMatrix)^(n-2)
          long[][] resultMatrix = matrixPower(baseMatrix, n - 2);

          // Result is derived from the first row of the resulting matrix
          return (int) ((resultMatrix[0][0] * 2 + resultMatrix[0][1]) % MOD);
     }

     private long[][] matrixMultiply(long[][] a, long[][] b) {
          int size = a.length;
          long[][] result = new long[size][size];

          for (int i = 0; i < size; i++) {
               for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                         result[i][j] = (result[i][j] + a[i][k] * b[k][j]) % MOD;
                    }
               }
          }

          return result;
     }

     private long[][] matrixPower(long[][] matrix, int n) {
          int size = matrix.length;
          long[][] result = new long[size][size];

          // Initialize result as the identity matrix
          for (int i = 0; i < size; i++) {
               result[i][i] = 1;
          }

          while (n > 0) {
               if ((n & 1) == 1) { // If n is odd
                    result = matrixMultiply(result, matrix);
               }
               matrix = matrixMultiply(matrix, matrix);
               n >>= 1; // Divide n by 2
          }

          return result;
     }

     public static void main(String[] args) {
          Solution solution = new Solution();

          System.out.println(solution.numTilings(3)); // Output: 5
          System.out.println(solution.numTilings(1)); // Output: 1

     }
}
