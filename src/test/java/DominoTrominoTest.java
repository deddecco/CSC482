import org.junit.Test;

public class DominoTrominoTest {

     @Test
     public void assertN1Is1() {
          DominoTromino dt = new DominoTromino();
          int result = dt.numTilings(1);
          assert result == 1;
     }

     @Test
     public void assertN3Is5() {
          DominoTromino dt = new DominoTromino();
          int result = dt.numTilings(3);
          assert result == 5;
     }

     @Test
     public void assert4Is11() {
          DominoTromino dt = new DominoTromino();
          int result = dt.numTilings(4);
          assert result == 11;
     }

     @Test
     public void assert30is312342182() {
          DominoTromino dt = new DominoTromino();
          int result = dt.numTilings(30);
          assert result == 312342182;
     }
}
