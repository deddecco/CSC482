package lab3;

public class FibonacciLogN {
     private static final long MOD = 1_000_000_007L;

     public static long fibLogN(long n) {
          if (n <= 1) {
               return n;
          }

          long[][] base = {{1, 1}, {1, 0}};
          long[][] result = matrixPower(base, n - 1);

          return result[0][0];
     }

     private static long[][] matrixPower(long[][] base, long exponent) {
          long[][] result = {{1, 0}, {0, 1}}; // Identity matrix

          while (exponent > 0) {
               if (exponent % 2 == 1) {
                    result = multiplyMatrix(result, base);
                    base = multiplyMatrix(base, base);
               } else {
                    base = multiplyMatrix(base, base);
               }
               exponent /= 2;
          }

          return result;
     }

     private static long[][] multiplyMatrix(long[][] a, long[][] b) {
          long[][] c = new long[2][2];
          for (int i = 0; i < 2; i++) {
               for (int j = 0; j < 2; j++) {
                    c[i][j] = (a[i][0] * b[0][j] % MOD + a[i][1] * b[1][j] % MOD) % MOD;
               }
          }
          return c;
     }

     public static void main(String[] args) {
          long n = 13; // Example input
          System.out.println("Fibonacci of " + n + " is: " + fibLogN(n));
     }
}
