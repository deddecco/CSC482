package pentomino;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DLX {
     class DataNode {
          DataNode L, R, U, D;
          ColumnNode C;

          public DataNode() {
               L = R = U = D = this;
          }

          public DataNode(ColumnNode c) {
               this();
               C = c;
          }

          void linkDown(DataNode node) {
               node.D = D;
               node.D.U = node;
               node.U = this;
               D = node;
          }

          DataNode linkRight(DataNode node) {
               node.R = R;
               node.R.L = node;
               node.L = this;
               R = node;
               return node;
          }

          void linkLR() {
               L.R = R.L = this;
          }

          void unlinkLR() {
               L.R = R;
               R.L = L;
          }

          void linkUD() {
               U.D = D.U = this;
          }

          void unlinkUD() {
               U.D = D;
               D.U = U;
          }
     }

     class ColumnNode extends DataNode {
          int columnSize;
          String name;

          public ColumnNode(String name) {
               super();
               this.name = name;
               columnSize = 0;
               C = this;
          }

          void cover() {
               unlinkLR();
               for (DataNode i = D; i != this; i = i.D)
                    for (DataNode j = i.R; j != i; j = j.R) {
                         j.unlinkUD();
                         j.C.columnSize--;
                    }
          }

          void uncover() {
               for (DataNode i = U; i != this; i = i.U)
                    for (DataNode j = i.L; j != i; j = j.L) {
                         j.linkUD();
                         j.C.columnSize++;
                    }
               linkLR();
          }
     }

     private final ColumnNode head;
     private final List<ColumnNode> columnNodesList;
     private int solutions;
     private final LinkedList<DataNode> solution;

     public DLX(ArrayList<String> columnHeaders, ArrayList<ArrayList<Integer>> matrix) {
          head = new ColumnNode("head");
          columnNodesList = new ArrayList<>();
          solution = new LinkedList<>();
          buildColumnHeaders(columnHeaders);
          buildDancingLinks(matrix);
     }

     private void buildColumnHeaders(ArrayList<String> colHeaders) {
          ColumnNode last = head;
          for (String name : colHeaders) {
               ColumnNode node = new ColumnNode(name);
               columnNodesList.add(node);
               last = (ColumnNode) last.linkRight(node);
          }
     }

     private void buildDancingLinks(ArrayList<ArrayList<Integer>> matrix) {
          for (ArrayList<Integer> row : matrix) {
               DataNode prev = null;
               for (int col : row) {
                    ColumnNode c = columnNodesList.get(col);
                    DataNode node = new DataNode(c);
                    if (prev == null) prev = node;
                    else prev = prev.linkRight(node);
                    c.U.linkDown(node);
                    c.columnSize++;
               }
          }
     }

     public void run() {
          solutions = 0;
          search(0);
     }

     private void search(int k) {
          if (head.R == head) {
               solutions++;
               return;
          }

          ColumnNode col = selectColumn();
          col.cover();

          DataNode row = col.D;
          while (row != col) {
               DataNode nextRow = row.D;
               solution.add(row);

               for (DataNode j = row.R; j != row; j = j.R)
                    j.C.cover();

               search(k + 1);

               solution.removeLast();

               for (DataNode j = row.L; j != row; j = j.L)
                    j.C.uncover();

               row = nextRow;
          }

          col.uncover();
     }

     private ColumnNode selectColumn() {
          ColumnNode min = null;
          int minSize = Integer.MAX_VALUE;
          for (ColumnNode c = (ColumnNode) head.R; c != head; c = (ColumnNode) c.R)
               if (c.columnSize < minSize) {
                    min = c;
                    minSize = c.columnSize;
               }
          return min;
     }

     public int getSolutions() {
          return solutions;
     }

     public static void main(String[] args) {
          testCase(3, 20, 2);
          testCase(4, 15, 368);
          testCase(5, 12, 1010);
          testCase(6, 10, 2339);
     }

     private static void testCase(int rows, int cols, int expected) {
          PentominoData data = new PentominoData(rows, cols);
          DLX solver = new DLX(data.getColumnHeaders(), data.getMatrix());
          long start = System.currentTimeMillis();
          solver.run();
          long duration = System.currentTimeMillis() - start;
          System.out.printf("%dx%d: %d (expected %d) %s [%.2fs]%n", rows, cols, solver.getSolutions(), expected, solver.getSolutions() == expected ? "✔" : "❌", duration / 1000.0);
     }
}

class PentominoData {
     static final int[][] F = {{1, -1, 1, 0, 1, 1, 2, 1}, {0, 1, 1, -1, 1, 0, 2, 0}};
     static final int[][] V = {{1, 0, 2, 0, 2, 1, 2, 2}, {0, 1, 0, 2, 1, 0, 2, 0}, {1, 0, 2, -2, 2, -1, 2, 0}, {0, 1, 0, 2, 1, 2, 2, 2}};
     static final int[][] I = {{0, 1, 0, 2, 0, 3, 0, 4}, {1, 0, 2, 0, 3, 0, 4, 0}};
     static final int[][] L = {{1, 0, 1, 1, 1, 2, 1, 3}, {1, 0, 2, 0, 3, -1, 3, 0}, {0, 1, 0, 2, 0, 3, 1, 3}, {0, 1, 1, 0, 2, 0, 3, 0}, {0, 1, 1, 1, 2, 1, 3, 1}, {0, 1, 0, 2, 0, 3, 1, 0}, {1, 0, 2, 0, 3, 0, 3, 1}, {1, -3, 1, -2, 1, -1, 1, 0}};
     static final int[][] N = {{0, 1, 1, -2, 1, -1, 1, 0}, {1, 0, 1, 1, 2, 1, 3, 1}, {0, 1, 0, 2, 1, -1, 1, 0}, {1, 0, 2, 0, 2, 1, 3, 1}, {0, 1, 1, 1, 1, 2, 1, 3}, {1, 0, 2, -1, 2, 0, 3, -1}, {0, 1, 0, 2, 1, 2, 1, 3}, {1, -1, 1, 0, 2, -1, 3, -1}};
     static final int[][] P = {{0, 1, 1, 0, 1, 1, 2, 1}, {0, 1, 0, 2, 1, 0, 1, 1}, {1, 0, 1, 1, 2, 0, 2, 1}, {0, 1, 1, -1, 1, 0, 1, 1}, {0, 1, 1, 0, 1, 1, 1, 2}, {1, -1, 1, 0, 2, -1, 2, 0}, {0, 1, 0, 2, 1, 1, 1, 2}, {0, 1, 1, 0, 1, 1, 2, 0}};
     static final int[][] T = {{0, 1, 0, 2, 1, 1, 2, 1}, {1, -2, 1, -1, 1, 0, 2, 0}, {1, 0, 2, -1, 2, 0, 2, 1}, {1, 0, 1, 1, 1, 2, 2, 0}};
     static final int[][] U = {{0, 1, 0, 2, 1, 0, 1, 2}, {0, 1, 1, 1, 2, 0, 2, 1}, {0, 2, 1, 0, 1, 1, 1, 2}, {0, 1, 1, 0, 2, 0, 2, 1}};
     static final int[][] W = {{1, 0, 1, 1, 2, 1, 2, 2}, {1, -1, 1, 0, 2, -2, 2, -1}, {0, 1, 1, 1, 1, 2, 2, 2}, {0, 1, 1, -1, 1, 0, 2, -1}};
     static final int[][] X = {{1, -1, 1, 0, 1, 1, 2, 0}};
     static final int[][] Y = {{1, -2, 1, -1, 1, 0, 1, 1}, {1, -1, 1, 0, 2, 0, 3, 0}, {0, 1, 0, 2, 0, 3, 1, 1}, {1, 0, 2, 0, 2, 1, 3, 0}, {0, 1, 0, 2, 0, 3, 1, 2}, {1, 0, 1, 1, 2, 0, 3, 0}, {1, -1, 1, 0, 1, 1, 1, 2}, {1, 0, 2, -1, 2, 0, 3, 0}};
     static final int[][] Z = {{0, 1, 1, 0, 2, -1, 2, 0}, {1, 0, 1, 1, 1, 2, 2, 2}, {0, 1, 1, 1, 2, 1, 2, 2}, {1, -2, 1, -1, 1, 0, 2, -2}};

     static final int[][][] SHAPES = {F, I, L, N, P, T, U, V, W, X, Y, Z};
     static final String[] NAMES = {"F", "I", "L", "N", "P", "T", "U", "V", "W", "X", "Y", "Z"};

     private final int rows, cols;
     private final ArrayList<String> headers;
     private final ArrayList<ArrayList<Integer>> matrix;

     public ArrayList<String> getColumnHeaders() {
          return headers;
     }

     public ArrayList<ArrayList<Integer>> getMatrix() {
          return matrix;
     }

     public PentominoData(int rows, int cols) {
          this.rows = rows;
          this.cols = cols;
          this.headers = createHeaders();
          this.matrix = new ArrayList<>();
          generateMatrix();
     }

     private ArrayList<String> createHeaders() {
          ArrayList<String> headers = new ArrayList<>();
          for (int i = 0; i < rows * cols; i++)
               headers.add("C" + (i / cols) + "-" + (i % cols));
          for (String name : NAMES)
               headers.add("P" + name);
          return headers;
     }

     private void generateMatrix() {
          int boardSize = rows * cols;

          for (int shapeIdx = 0; shapeIdx < SHAPES.length; shapeIdx++) {
               for (int[] orient : SHAPES[shapeIdx]) {
                    for (int y = 0; y < rows; y++) {
                         for (int x = 0; x < cols; x++) {
                              HashSet<Integer> cells = new HashSet<>();
                              cells.add(y * cols + x);
                              boolean valid = true;

                              for (int i = 0; i < orient.length; i += 2) {
                                   int dx = orient[i];
                                   int dy = orient[i + 1];
                                   int cx = x + dx;
                                   int cy = y + dy;

                                   if (cx < 0 || cx >= cols || cy < 0 || cy >= rows) {
                                        valid = false;
                                        break;
                                   }
                                   cells.add(cy * cols + cx);
                              }

                              if (valid && cells.size() == 5) {
                                   ArrayList<Integer> row = new ArrayList<>(cells);
                                   row.add(boardSize + shapeIdx);
                                   matrix.add(row);
                              }
                         }
                    }
               }
          }
     }
}