package homework1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.*;

public class Main {
     static final int INF = MAX_VALUE / 2;
     static int N;
     static int M;
     static int S;
     static int D;
     static ArrayList<ArrayList<Edge>> graph;
     static ArrayList<ArrayList<Edge>> reverseGraph;
     static int[] dist;
     static boolean[][] shortestPathEdges;

     static class Edge {
          int to;
          int weight;

          Edge(int to, int weight) {
               this.to = to;
               this.weight = weight;
          }
     }

     public static void main(String[] args) throws IOException {
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          PrintWriter out = new PrintWriter(System.out);

          while (true) {
               String[] input = br.readLine().split(" ");
               N = parseInt(input[0]);
               M = parseInt(input[1]);

               if (N == 0 && M == 0) break;

               input = br.readLine().split(" ");
               S = parseInt(input[0]);
               D = parseInt(input[1]);

               graph = new ArrayList<>();
               reverseGraph = new ArrayList<>();
               for (int i = 0; i < N; i++) {
                    graph.add(new ArrayList<>());
                    reverseGraph.add(new ArrayList<>());
               }

               for (int i = 0; i < M; i++) {
                    input = br.readLine().split(" ");
                    int U = parseInt(input[0]);
                    int V = parseInt(input[1]);
                    int P = parseInt(input[2]);
                    graph.get(U).add(new Edge(V, P));
                    reverseGraph.get(V).add(new Edge(U, P));
               }

               shortestPathEdges = new boolean[N][N];
               int shortestDist = dijkstra(S, D);
               markShortestPaths(D);

               int almostShortestDist = dijkstra(S, D);
               out.println(almostShortestDist == INF ? -1 : almostShortestDist);
          }

          out.close();
     }

     static int dijkstra(int start, int end) {
          dist = new int[N];
          Arrays.fill(dist, INF);
          dist[start] = 0;

          PriorityQueue<Integer> pq = new PriorityQueue<>(N, (a, b) -> compare(dist[a], dist[b]));
          pq.insert(start, 0);

          while (pq.size > 0) {
               int u = pq.extractMin();
               if (u == end) break;

               for (Edge e : graph.get(u)) {
                    if (shortestPathEdges[u][e.to]) continue;
                    int newDist = dist[u] + e.weight;
                    if (newDist < dist[e.to]) {
                         dist[e.to] = newDist;
                         if (pq.contains(e.to)) {
                              pq.changePriority(e.to, newDist);
                         } else {
                              pq.insert(e.to, newDist);
                         }
                    }
               }
          }

          return dist[end];
     }

     static void markShortestPaths(int v) {
          if (v == S) return;
          for (Edge e : reverseGraph.get(v)) {
               if (dist[v] == dist[e.to] + e.weight) {
                    shortestPathEdges[e.to][v] = true;
                    markShortestPaths(e.to);
               }
          }
     }
}