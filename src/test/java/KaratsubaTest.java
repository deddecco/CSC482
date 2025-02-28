import lab2.Karatsuba;
import org.junit.Test;

public class KaratsubaTest {

     @Test
     public void tenMIllion() {
          String s1 = "10000000";
          String s2 = "123456789";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("1234567890000000");
     }

     @Test
     public void tenNineEight() {
          String s1 = "10987654321";
          String s2 = "12345678910";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("135650052221140070110");
     }

     @Test
     public void tenDigTest1() {
          String s1 = "1932875648";
          String s2 = "1140367259";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("2204188104697608832");
     }

     @Test
     public void tenDigTest2() {
          String s1 = "4049924658";
          String s2 = "6789774612";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("27498075623401182696");
     }

     @Test
     public void small1() {
          String s1 = "40";
          String s2 = "67";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("2680");
     }

     @Test
     public void small2() {
          String s1 = "4";
          String s2 = "67";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("268");
     }

     @Test
     public void small3() {
          String s1 = "54";
          String s2 = "6";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("324");
     }

     @Test
     public void small4() {
          String s1 = "5";
          String s2 = "6";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("30");
     }

     @Test
     public void small5() {
          String s1 = "3";
          String s2 = "2";
          String s3 = Karatsuba.multiply(s1, s2);
          System.out.println(s3);
          assert s3.equals("6");
     }


}

