package pentomino;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DLX1 {
     private ColumnNode head;
     private List<ColumnNode> columnNodesList;
     private int numSolutions;
     private LinkedList<DataNode> solution;
     private ArrayList<ArrayList<ArrayList<Integer>>> solutions;

     // Main method for testing
     public static void main(String[] args) {
          testCase(3, 20, 2);
          testCase(4, 15, 368);
          testCase(5, 12, 1010);
          testCase(6, 10, 2339);
     }

     private static void testCase(int rows, int cols, int expected) {
          PentominoData data = new PentominoData(rows, cols);
          DLX1 dlx1 = new DLX1(data.getColumnHeaders(), data.getMatrix());
          long start = System.currentTimeMillis();
          dlx1.run();
          long end = System.currentTimeMillis();
          System.out.printf("%dx%d: %d solutions (expected %d)%s [%.2fs]%n", rows, cols, dlx1.getNumberOfSolutions(), expected, dlx1.getNumberOfSolutions() == expected ? " ✔" : " ❌", (end - start) / 1000.0);
     }

     private DLX1() {
          this.head = new ColumnNode("head");
          this.columnNodesList = new ArrayList();
          this.solution = new LinkedList();
     }

     public DLX1(int var1, ArrayList<ArrayList<Integer>> var2) {
          this();
          this.buildColumnHeaders(var1);
          this.buildDancingLinks(var2);
     }

     public DLX1(ArrayList<ArrayList<Integer>> var1) {
          this(((ArrayList) var1.get(0)).size(), var1);
     }

     public DLX1(ArrayList<String> var1, ArrayList<ArrayList<Integer>> var2) {
          this();
          this.buildColumnHeaders(var1);
          this.buildDancingLinks(var2);
     }

     public void run() {
          this.numSolutions = 0;
          this.solutions = new ArrayList();
          this.search(0);
     }

     public int getNumberOfSolutions() {
          return this.numSolutions;
     }

     public ArrayList<ArrayList<ArrayList<Integer>>> getSolutions() {
          return this.solutions;
     }

     private void search(int var1) {
          if (this.head.R == this.head) {
               ++this.numSolutions;
               this.addSolution();
          } else {
               ColumnNode var2 = this.selectColumn();
               var2.cover();

               for (DataNode var3 = var2.D; var3 != var2; var3 = var3.D) {
                    this.solution.add(var3);

                    DataNode var4;
                    for (var4 = var3.R; var4 != var3; var4 = var4.R) {
                         var4.C.cover();
                    }

                    this.search(var1 + 1);
                    var3 = (DataNode) this.solution.removeLast();
                    var2 = var3.C;

                    for (var4 = var3.L; var4 != var3; var4 = var4.L) {
                         var4.C.uncover();
                    }
               }

               var2.uncover();
          }
     }

     private ColumnNode selectColumn() {
          int var1 = Integer.MAX_VALUE;
          ColumnNode var2 = null;

          for (ColumnNode var3 = (ColumnNode) this.head.R; var3 != this.head; var3 = (ColumnNode) var3.R) {
               if (var3.columnSize < var1) {
                    var1 = var3.columnSize;
                    var2 = var3;
               }
          }

          return var2;
     }

     private void buildColumnHeaders(int var1) {
          ColumnNode var2 = this.head;

          for (int var3 = 0; var3 < var1; ++var3) {
               ColumnNode var4 = new ColumnNode(Integer.toString(var3));
               this.columnNodesList.add(var4);
               var2 = (ColumnNode) var2.linkRight(var4);
          }

     }

     private void buildColumnHeaders(ArrayList<String> var1) {
          int var2 = var1.size();
          ColumnNode var3 = this.head;

          for (String s : var1) {
               ColumnNode var5 = new ColumnNode((String) s);
               this.columnNodesList.add(var5);
               var3 = (ColumnNode) var3.linkRight(var5);
          }

     }

     private void buildDancingLinks(ArrayList<ArrayList<Integer>> var1) {
          int var2 = var1.size();

          for (int var3 = 0; var3 < var2; ++var3) {
               DataNode var4 = null;

               ColumnNode var7;
               for (Iterator var5 = ((ArrayList) var1.get(var3)).iterator(); var5.hasNext(); ++var7.columnSize) {
                    Integer var6 = (Integer) var5.next();
                    var7 = (ColumnNode) this.columnNodesList.get(var6);
                    DataNode var8 = new DataNode(var7);
                    if (var4 == null) {
                         var4 = var8;
                    }

                    var7.U.linkDown(var8);
                    var4 = var4.linkRight(var8);
               }
          }

     }

     private void addSolution() {
          ArrayList var1 = new ArrayList();

          for (DataNode dataNode : this.solution) {
               DataNode var3;
               for (var3 = dataNode; this.columnNodesList.indexOf(var3.C) > 11; var3 = var3.R) {
               }

               ArrayList var4 = new ArrayList();
               var4.add(Integer.parseInt(var3.C.name));

               for (DataNode var5 = var3.R; var5 != var3; var5 = var5.R) {
                    var4.add(Integer.parseInt(var5.C.name));
               }

               var1.add(var4);
          }

          this.solutions.add(var1);
     }

     class ColumnNode extends DataNode {
          int columnSize;
          String name;

          public ColumnNode(String var2) {
               super();
               this.name = var2;
               this.columnSize = 0;
               this.C = this;
          }

          void cover() {
               this.unlinkLR();

               for (DataNode var1 = this.D; var1 != this; var1 = var1.D) {
                    for (DataNode var2 = var1.R; var2 != var1; var2 = var2.R) {
                         var2.unlinkUD();
                         --var2.C.columnSize;
                    }
               }

          }

          void uncover() {
               for (DataNode var1 = this.U; var1 != this; var1 = var1.U) {
                    for (DataNode var2 = var1.L; var2 != var1; var2 = var2.L) {
                         var2.linkUD();
                         ++var2.C.columnSize;
                    }
               }

               this.linkLR();
          }
     }

     class DataNode {
          DataNode L;
          DataNode R;
          DataNode U;
          DataNode D;
          ColumnNode C;

          public DataNode() {
               this.L = this.R = this.U = this.D = this;
          }

          public DataNode(ColumnNode var2) {
               this();
               this.C = var2;
          }

          DataNode linkDown(DataNode var1) {
               var1.D = this.D;
               var1.D.U = var1;
               var1.U = this;
               this.D = var1;
               return var1;
          }

          DataNode linkRight(DataNode var1) {
               var1.R = this.R;
               var1.R.L = var1;
               var1.L = this;
               this.R = var1;
               return var1;
          }

          void linkLR() {
               this.L.R = this.R.L = this;
          }

          void unlinkLR() {
               this.L.R = this.R;
               this.R.L = this.L;
          }

          void linkUD() {
               this.U.D = this.D.U = this;
          }

          void unlinkUD() {
               this.U.D = this.D;
               this.D.U = this.U;
          }
     }
}