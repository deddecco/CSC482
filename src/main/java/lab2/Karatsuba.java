package lab2;

import static java.lang.Long.parseLong;

public class Karatsuba {

     // Function to multiply two numbers using the Karatsuba algorithm (with string inputs)
     public String multiply(String x, String y) {
          // Base case: if one of the numbers is a single digit, just multiply them directly
          if (x.length() == 1 || y.length() == 1) {
               return String.valueOf(parseLong(x) * parseLong(y));
          }

          // Ensure both numbers are of the same length by padding the shorter one with leading zeros
          int maxLength = Math.max(x.length(), y.length());
          int m2 = maxLength / 2;

          StringBuilder xBuilder = new StringBuilder(x);
          while (xBuilder.length() < maxLength) {
               xBuilder.insert(0, "0");
          }
          x = xBuilder.toString();

          StringBuilder yBuilder = new StringBuilder(y);
          while (yBuilder.length() < maxLength) {
               yBuilder.insert(0, "0");
          }
          y = yBuilder.toString();

          // Split the numbers into two halves
          String highX = x.substring(0, x.length() - m2);
          String lowX = x.substring(x.length() - m2);
          String highY = y.substring(0, y.length() - m2);
          String lowY = y.substring(y.length() - m2);

          // Recursively calculate the three products
          String z0 = multiply(lowX, lowY); // lowX * lowY
          String z2 = multiply(highX, highY); // highX * highY
          String z1 = multiply(addStrings(lowX, highX), addStrings(lowY, highY)); // (lowX + highX) * (lowY + highY)

          // Use the Karatsuba formula to combine the results
          String temp = subtractStrings(subtractStrings(z1, z2), z0);

          // Combine and remove leading zeroes before returning
          String result = addStrings(addStrings(shiftLeft(z2, 2 * m2), shiftLeft(temp, m2)), z0);
          return removeLeadingZeros(result);
     }

     // Utility function to add two strings (representing large numbers)
     private static String addStrings(String num1, String num2) {
          StringBuilder result = new StringBuilder();
          int carry = 0;

          int i = num1.length() - 1;
          int j = num2.length() - 1;

          while (i >= 0 || j >= 0 || carry != 0) {
               int digit1 = i >= 0 ? num1.charAt(i) - '0' : 0;
               int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;

               int sum = digit1 + digit2 + carry;
               result.append(sum % 10);
               carry = sum / 10;

               i--;
               j--;
          }

          return result.reverse().toString();
     }

     // Utility function to subtract two strings (representing large numbers)
     private static String subtractStrings(String num1, String num2) {
          StringBuilder result = new StringBuilder();
          int borrow = 0;

          int i = num1.length() - 1;
          int j = num2.length() - 1;

          while (i >= 0) {
               int digit1 = num1.charAt(i) - '0';
               int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;

               int diff = digit1 - digit2 - borrow;
               if (diff < 0) {
                    diff += 10;
                    borrow = 1;
               } else {
                    borrow = 0;
               }

               result.append(diff);
               i--;
               j--;
          }

          return result.reverse().toString();
     }

     // Utility function to shift a string left by a number of digits (equivalent to multiplying by 10^n)
     private static String shiftLeft(String num, int n) {
          return num + "0".repeat(Math.max(0, n));
     }

     // Utility function to remove leading zeroes from a string
     private static String removeLeadingZeros(String num) {
          int startIndex = 0;
          while (startIndex < num.length() && num.charAt(startIndex) == '0') {
               startIndex++;
          }
          // If the number is all zeroes, return "0"
          if (startIndex == num.length()) {
               return "0";
          }
          return num.substring(startIndex);
     }

     public static void main(String[] args) {
          String num1 = "1234";
          String num2 = "5678";

          Karatsuba k = new Karatsuba();
          String product = k.multiply(num1, num2);
          System.out.println("Product of " + num1 + " and " + num2 + " is: " + product);
     }
}
