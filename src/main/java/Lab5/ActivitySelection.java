package Lab5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Request {
     int startTime;
     int finishTime;

     public Request(int startTime, int finishTime) {
          this.startTime = startTime;
          this.finishTime = finishTime;
     }
}

public class ActivitySelection {

     static List<Request> selectActivities(List<Request> requests) {
          // Sort the requests by their finishing times
          requests.sort(Comparator.comparingInt(r -> r.finishTime));

          List<Request> acceptedRequests = new ArrayList<>();

          // Initially, let A (acceptedRequests) be empty
          int lastFinishTime = 0;

          // While R (requests) is not empty
          for (Request request : requests) {
               // Choose a request i ∈ R that has the smallest finishing time
               if (request.startTime >= lastFinishTime) {
                    // Add request i to A
                    acceptedRequests.add(request);
                    // Update the last finish time
                    lastFinishTime = request.finishTime;
               }
          }

          // Return the set A as the set of accepted requests
          return acceptedRequests;
     }

     public static void main(String[] args) {
          // Example input: List of requests with start and finish times
          List<Request> requests = new ArrayList<>();
          requests.add(new Request(1, 4));
          requests.add(new Request(3, 5));
          requests.add(new Request(0, 6));
          requests.add(new Request(5, 7));
          requests.add(new Request(3, 9));
          requests.add(new Request(5, 9));
          requests.add(new Request(6, 10));
          requests.add(new Request(8, 11));
          requests.add(new Request(8, 12));
          requests.add(new Request(2, 14));
          requests.add(new Request(12, 16));

          // Get the selected activities
          List<Request> selectedActivities = selectActivities(requests);

          // Print the selected activities
          System.out.println("Selected activities:");
          for (Request request : selectedActivities) {
               System.out.println("Start: " + request.startTime + ", Finish: " + request.finishTime);
          }
     }
}