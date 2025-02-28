package lab1;

import java.util.Arrays;

public class EuclidAlgo {
     // Standard Euclidean GCD algorithm
     static int gcd(int a, int b) {
          if (b == 0) {
               return a;
          }
          return gcd(b, a % b);
     }

     // Extended Euclidean algorithm that calls gcd() and returns an array [GCD, x, y]
     public static int[] gcdExtended(int a, int b) {
          // Variables for the extended Euclidean algorithm
          int x = 0;
          int y = 1;
          int lastx = 1;
          int lasty = 0;
          int temp;

          int originalA = a;
          int originalB = b;

          // Run the Euclidean algorithm to find the GCD and coefficients
          while (b != 0) {
               int q = a / b;
               int r = a % b;

               // Update a and b for the next iteration
               a = b;
               b = r;

               // Update x and y using the values from the previous iteration
               temp = x;
               x = lastx - q * x;
               lastx = temp;

               temp = y;
               y = lasty - q * y;
               lasty = temp;
          }

          // Return an array containing the GCD and the coefficients x and y
          return new int[]{a, lastx, lasty};
     }

     public static void main(String[] args) {
          int a = 12;
          int b = 32;

          // Call gcdExtended and get the result array [GCD, x, y]
          int[] result = gcdExtended(a, b);

          // Print the results
          System.out.println("GCD: " + result[0]);
          System.out.println("x: " + result[1] + ", y: " + result[2]);
          System.out.println(Arrays.toString(result));
          System.out.println(result[0] + " = (" + result[1] + " * " + a + ") + (" + result[2] + " * " + b + ")");
     }
}
