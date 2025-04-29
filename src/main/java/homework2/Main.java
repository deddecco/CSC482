package homework2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Integer.*;
import static java.lang.Math.max;

public class Main {

     public static void main(String[] args) throws IOException {
          handleIO();
     }

     /**
      * Method to handle input and output for the pipe-cutting optimization problem.
      */
     public static void handleIO() throws IOException {
          BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

          // Read the first line containing N and T
          String[] firstLine = reader.readLine().split(" ");
          // Number of different pipe sizes
          int T = parseInt(firstLine[1]);
          // Size of each pipe produced by OBI
          int N = parseInt(firstLine[0]);

          // Arrays to store lengths (Ci) and values (Vi)
          int[] lengths = new int[N];
          int[] values = new int[N];

          // Read the next N lines containing Ci and Vi
          for (int i = 0; i < N; i++) {
               String[] line = reader.readLine().split(" ");
               lengths[i] = parseInt(line[0]);
               values[i] = parseInt(line[1]);
          }

          int maxProfit = calculateMaxProfit(N, T, lengths, values);

          // Output the result
          System.out.println(maxProfit);
     }


         /*  Knapsack(N, T, lengths, values)
          Array dp[0...T] ← 0
          For i = 1 to N (1 to total number of different sizes)
              For w = lengths[i] to T (lengths[i] to the total length available)
                  dp[w] ← max(dp[w], dp[w - lengths[i]] + values[i])
              Endfor
          Endfor
          Return dp[T]*/
     public static int calculateMaxProfit(int N,
                                          int T,
                                          int[] lengths,
                                          int[] values) {
          // Initialize dp array where dp[w] stores max value for pipe length w--
          // up to an index that can support a length of T, so T+1
          int[] dp = new int[T + 1];

          // Process each available pipe size (client order)
          for (int i = 0; i < N; i++) {
               // Client-requested pipe length (C_i)
               int Csubi = lengths[i];
               // Corresponding sales value (V_i)
               int Vsubi = values[i];
               for (int j = Csubi; j <= T; j++) {
                    // Choose between keeping previous value
                    // or optimal value achievable for the remaining pipe
                    // length after cutting out a segment of size Csubi
                    // dp[j] = existing state
                    // dp[j-Csubi] + Vsubi = maximum profit already achieved, plus profit achievable with cut
                    dp[j] = max(dp[j], dp[j - Csubi] + Vsubi);
               }
          }

          // maximum possible profit achievable from cutting a pipe of total length T
          // into segments that match client orders
          return dp[T];
     }
}