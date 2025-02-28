package lab2;

import java.math.BigInteger;

import static java.lang.Math.*;

public class BigIntegerKaratsuba {
     public static BigInteger karatsuba(BigInteger x, BigInteger y) {
          int N = max(x.bitLength(), y.bitLength());
          // no gain if numbers too small
          if (N <= 2000) { // Threshold for switching to simple multiplication
               return x.multiply(y);
          }

          // split the numbers
          N = (N / 2) + (N % 2); // Half the number of bits, rounded up
          BigInteger b = x.shiftRight(N);
          BigInteger a = x.subtract(b.shiftLeft(N));
          BigInteger d = y.shiftRight(N);
          BigInteger c = y.subtract(d.shiftLeft(N));

          // intermediate steps
          BigInteger ac = karatsuba(a, c);
          BigInteger bd = karatsuba(b, d);
          BigInteger abcd = karatsuba(a.add(b), c.add(d));

          // put all the intermediate results through Karatsuba's final formula
          return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2 * N));
     }

     public static void main(String[] args) {
          BigInteger x = new BigInteger("123456789012345678901234567890");
          BigInteger y = new BigInteger("987654321098765432109876543210");

          BigInteger result = karatsuba(x, y);
          System.out.println("Result: " + result);
     }
}
