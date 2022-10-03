import java.io.*;
import java.io.File;

public class Driver {

  public static void main(String[] args) throws IOException {
    Polynomial p = new Polynomial();
    System.out.println(p.evaluate(3));

    double[] c1 = { -3, -5, 2 };
    int[] e1 = { 0, 1, 2 };

    double[] c2 = { 2, -5 };
    int[] e2 = { 1, 3 };

    double[] c5 = { 5, 5 };
    int[] e5 = { 0, 2 };

    Polynomial p1 = new Polynomial(c1, e1);

    Polynomial p2 = new Polynomial(c2, e2);
    Polynomial p3 = new Polynomial();
    Polynomial p5 = new Polynomial(c5, e5);

    File text = new File("/Users/jamiehvizdos/b07lab1/b07lab1/test.txt");
    boolean newFileCreated = text.createNewFile();

    p3 = new Polynomial(text);

    Polynomial p6 = new Polynomial();

    File text2 = new File("/Users/jamiehvizdos/b07lab1/b07lab1/test2.txt");
    boolean newFileCreated2 = text2.createNewFile();

    p6 = new Polynomial(text2);
    System.out.println(p6.evaluate(3));

    System.out.println("3 is a root of p1: " + p1.hasRoot(3));
    System.out.println("1 is a root of p1: " + p1.hasRoot(1));

    System.out.println("p2 evaluated at 3: " + p2.evaluate(3));
    System.out.println("p3 evaluated at 1: " + p3.evaluate(1));

    Polynomial p4 = p1.multiply(p2);

    System.out.println("p1 * p2 evaluated at 1: " + p4.evaluate(1));

    p4.saveToFile("output.txt");
    p5.saveToFile("output2.txt");
  }
}
