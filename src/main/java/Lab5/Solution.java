package Lab5;

class Solution {
     public int findMinArrowShots(int[][] points) {
          int n = points.length;
          if (n < 2) {
               return n; // If 1 balloon, we need 1 arrow; if 0 balloons, we need 0 arrows
          }

          // Sort the points by their end values
          mergeSort(points, 0, points.length - 1);

          // Start with one arrow
          int arrows = 1, prev = points[0][1];

          for (int i = 1; i < n; i++) {
               // If the current balloon does not overlap with the previous one
               if (points[i][0] > prev) {
                    arrows++; // Increase the number of arrows
                    prev = points[i][1]; // Update the previous end point
               }
          }

          return arrows;
     }

     private void mergeSort(int[][] intervals, int start, int end) {
          if (start < end) {
               int mid = start + (end - start) / 2;
               mergeSort(intervals, start, mid);
               mergeSort(intervals, mid + 1, end);
               merge(intervals, start, mid, end);
          }
     }

     private void merge(int[][] intervals, int start, int mid, int end) {
          int n1 = mid - start + 1;
          int n2 = end - mid;

          int[][] left = new int[n1][2];
          int[][] right = new int[n2][2];

          System.arraycopy(intervals, start, left, 0, n1);
          for (int j = 0; j < n2; j++) {
               right[j] = intervals[mid + 1 + j];
          }

          int i = 0, j = 0, k = start;

          while (i < n1 && j < n2) {
               if (left[i][1] <= right[j][1]) {
                    intervals[k++] = left[i++];
               } else {
                    intervals[k++] = right[j++];
               }
          }

          while (i < n1) {
               intervals[k++] = left[i++];
          }

          while (j < n2) {
               intervals[k++] = right[j++];
          }
     }
}