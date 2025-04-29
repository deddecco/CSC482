package homework1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.fill;

public class Main {
     // Define infinity as half of the maximum integer value to avoid overflow
     static final int INF = MAX_VALUE / 2;
     // Number of nodes in the graph
     static int nodes;
     // Number of edges in the graph
     static int edges;
     // Starting node
     static int start;
     // Destination node
     static int destination;
     // Adjacency list representation of the graph
     static List<List<Edge>> reverseGraph;
     // Reverse graph for backtracking
     static List<List<Edge>> graph;

     // Edge class to represent graph edges
     static class Edge {
          // Destination node of the edge
          int to;
          // Length (weight) of the edge
          int length;

          Edge(int origin, int length) {
               this.to = origin;
               this.length = length;
          }
     }

     public static void main(String[] args) throws IOException {
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

          while (true) {
               // Read number of nodes and edges
               String[] input = br.readLine().split(" ");
               nodes = parseInt(input[0]);
               edges = parseInt(input[1]);

               // Exit condition
               if (nodes == 0 && edges == 0) {
                    break;
               }

               // Read start and destination nodes
               input = br.readLine().split(" ");
               start = parseInt(input[0]);
               destination = parseInt(input[1]);

               // Initialize graph and reverse graph
               graph = new ArrayList<>();
               reverseGraph = new ArrayList<>();
               for (int i = 0; i < nodes; i++) {
                    graph.add(new ArrayList<>());
                    reverseGraph.add(new ArrayList<>());
               }

               // Read edges and populate graph and reverse graph
               for (int i = 0; i < edges; i++) {
                    input = br.readLine().split(" ");
                    int U = parseInt(input[0]);
                    int V = parseInt(input[1]);
                    int P = parseInt(input[2]);
                    graph.get(U).add(new Edge(V, P));
                    reverseGraph.get(V).add(new Edge(U, P));
               }

               // Calculate and print the result
               int result = almostShortestPath();
               if (result == INF) {
                    System.out.println(-1);
               } else {
                    System.out.println(result);
               }
          }
     }

     // Find the almost shortest path
     static int almostShortestPath() {
          // Find the shortest path
          int[] dist = dijkstra(start, graph);
          if (dist[destination] == INF) {
               return INF;
          }

          // Remove edges of the shortest path
          removeShortestPathEdges(destination, start, dist, graph, reverseGraph);

          // Find the new shortest path (which is the almost shortest path)
          return dijkstra(start, graph)[destination];
     }

     // Dijkstra's algorithm to find the shortest paths
     static int[] dijkstra(int start, List<List<Edge>> g) {
          int[] dist = new int[nodes];
          fill(dist, INF);
          dist[start] = 0;

          PriorityQueue<Integer> pq = new PriorityQueue<>(nodes);
          pq.insert(start, 0);

          while (pq.size > 0) {
               int u = pq.extractMin();
               for (Edge e : g.get(u)) {
                    int v = e.to;
                    int newDist = dist[u] + e.length;
                    if (newDist < dist[v]) {
                         dist[v] = newDist;
                         if (pq.contains(v)) {
                              pq.changePriority(v, newDist);
                         } else {
                              pq.insert(v, newDist);
                         }
                    }
               }
          }
          return dist;
     }

     // Remove edges that are part of the shortest path
     static void removeShortestPathEdges(int u,
                                         int target,
                                         int[] dist,
                                         List<List<Edge>> graph,
                                         List<List<Edge>> reverseGraph) {
          if (u == target) {
               return;
          }

          List<Edge> toRemove = new ArrayList<>();
          for (Edge e : reverseGraph.get(u)) {
               // Check if this edge is part of the shortest path
               if (dist[u] == dist[e.to] + e.length) {
                    // Identify the edge to remove from graph
                    for (Edge forwardEdge : graph.get(e.to)) {
                         if (forwardEdge.to == u && forwardEdge.length == e.length) {
                              toRemove.add(forwardEdge);
                         }
                    }
                    // Recursively remove edges from the previous node
                    removeShortestPathEdges(e.to, target, dist, graph, reverseGraph);
               }
          }

          // Remove the identified edges from the graph
          for (int i = 0; i < nodes; i++) {
               graph.get(i).removeAll(toRemove);
          }
     }
}