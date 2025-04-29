package Lab5;

import static java.lang.Math.*;
import static java.lang.System.*;

public class NonOverlappingIntervals {
     public int eraseOverlapIntervals(int[][] intervals) {
          if (intervals != null && intervals.length != 0) {// sort intervals
               mergeSort(intervals, 0, intervals.length - 1);

               int inCompatibles = 0;
               int lastFinishedIntervalEnd = intervals[0][1];

               for (int i = 1; i < intervals.length; i++) {
                    int start = intervals[i][0];
                    int end = intervals[i][1];

                    if (start >= lastFinishedIntervalEnd) {
                         lastFinishedIntervalEnd = end;
                    } else {
                         inCompatibles++;
                         lastFinishedIntervalEnd = min(lastFinishedIntervalEnd, end);
                    }
               }

               return inCompatibles;
          } else {
               return 0;
          }

     }

     public void mergeSort(int[][] intervals, int left, int right) {
          if (left < right) {
               int mid = left + (right - left) / 2;
               mergeSort(intervals, left, mid);
               mergeSort(intervals, mid + 1, right);
               merge(intervals, left, mid, right);
          }
     }

     public void merge(int[][] intervals, int low, int mid, int high) {
          int n1 = mid - low + 1;
          int n2 = high - mid;

          int[][] leftArray = new int[n1][2];
          int[][] rightArray = new int[n2][2];

          arraycopy(intervals, low, leftArray, 0, n1);
          for (int j = 0; j < n2; j++) {
               rightArray[j] = intervals[mid + 1 + j];
          }

          int i = 0, j = 0, k = low;

          while (i < n1 && j < n2) {
               if (leftArray[i][0] <= rightArray[j][0]) {
                    intervals[k] = leftArray[i];
                    i++;
               } else {
                    intervals[k] = rightArray[j];
                    j++;
               }
               k++;
          }

          while (i < n1) {
               intervals[k] = leftArray[i];
               i++;
               k++;
          }

          while (j < n2) {
               intervals[k] = rightArray[j];
               j++;
               k++;
          }
     }
}