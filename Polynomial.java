import java.io.*;
import java.io.BufferedReader;
import java.io.FileWriter;

public class Polynomial {

  double[] coefficients;
  int[] exponents;

  public Polynomial() {
    coefficients = new double[] { 0 };
    exponents = new int[] { 0 };
  }

  public Polynomial(double[] values, int[] exp) {
    coefficients = new double[values.length];
    for (int i = 0; i < values.length; i++) {
      coefficients[i] = values[i];
    }

    exponents = new int[exp.length];

    for (int i = 0; i < exp.length; i++) {
      exponents[i] = exp[i];
    }
  }

  public Polynomial(File info) throws FileNotFoundException, IOException {
    BufferedReader input = new BufferedReader(new FileReader(info));

    if (info.length() == 0) {
      coefficients = new double[] { 0 };
      exponents = new int[] { 0 };
    } else {
      String in = input.readLine();

      int index = 0;
      int count = 1;

      while (index < in.length()) {
        int tmpIdx = in.indexOf("x", index);

        if (tmpIdx != -1) {
          count++;

          if (tmpIdx < in.length() - 1) {
            index = tmpIdx + 1;
          }
        } else {
          index = in.length();
        }
        index++;
      }

      double[] newCoeff = new double[count];
      int[] newExp = new int[count];

      String[] terms = in.split("\\+", 0);

      String[] newTerms = new String[count];

      int arrIdx = 0;

      for (int i = 0; i < terms.length; i++) { //iterates through each term in the array
        int curIdx = 0;
        int idx = terms[i].indexOf("-", curIdx);

        if (idx == -1) {
          newTerms[arrIdx] = terms[i];
          arrIdx++;
        } else {
          String tmpStr = terms[i];

          while (!tmpStr.equals("")) {

            if (idx == -1) {
              newTerms[arrIdx] = terms[i];
              arrIdx++;
              tmpStr = "";
            } else if (idx == 0) {
              int tmpIdx = tmpStr.indexOf("-", 1);
              if (tmpIdx == -1) {
                newTerms[arrIdx] = tmpStr;
                tmpStr = "";
              } else {
                newTerms[arrIdx] = tmpStr.substring(0, tmpIdx);
                tmpStr = tmpStr.substring(tmpIdx);
              }
              arrIdx++;
            } else {
              newTerms[arrIdx] = tmpStr.substring(0, idx);
              tmpStr = tmpStr.substring(idx);
              arrIdx++;
              idx = tmpStr.indexOf("-");
            }
          }
        }
      }

      int idx = 0;
      for (int i = 0; i < newTerms.length; i++) {
        if (newTerms[i].indexOf("x") != -1) {
          String[] temp = newTerms[i].split("x", 0);
          newCoeff[idx] = Double.parseDouble(temp[0]);
          newExp[idx] = Integer.parseInt(temp[1]);
        } else {
          newCoeff[idx] = Double.parseDouble(newTerms[i]);
          newExp[idx] = 0;
        }
        idx++;
      }
      coefficients = newCoeff;
      exponents = newExp;
    }
  }

  public Polynomial add(Polynomial other) {
    double polyArray[];

    int length = Math.max(exponents[exponents.length - 1], other.exponents[other.exponents.length - 1]);

    double[] coEff = new double[length];
    int[] exp = new int[length];

    for (int i = 0; i < length; i++) {
      if (i < exponents.length) {
        exp[exponents[i]] = exponents[i];
      }
      if (i < other.exponents.length) {
        exp[other.exponents[i]] = other.exponents[i];
      }
    }

    for (int i = 0; i < coefficients.length; i++) {
      coEff[exponents[i]] += coefficients[i];
    }
    for (int i = 0; i < other.coefficients.length; i++) {
      coEff[other.exponents[i]] += other.coefficients[i];
    }

    int count = 0;
    for (int i = 0; i < length; i++) {
      if (coEff[i] != 0) {
        count++;
      }
    }

    double[] finalCo = new double[count];
    int[] finalExp = new int[count];

    int idx = 0;
    for (int i = 0; i < length; i++) {
      if (coEff[i] != 0) {
        finalCo[idx] = coEff[i];
        finalExp[idx] = exp[i];
        idx++;
      }
    }

    Polynomial newPolynomial = new Polynomial(coEff, exp);
    return newPolynomial;
  }

  public double evaluate(double x) {
    double sum = 0;
    for (int i = 0; i < coefficients.length; i++) {
      sum += coefficients[i] * Math.pow(x, exponents[i]);
    }
    return sum;
  }

  public boolean hasRoot(double x) {
    double sum = evaluate(x);
    if (sum == 0) {
      return true;
    } else {
      return false;
    }
  }

  public Polynomial multiply(Polynomial other) {
    int longest = Math.max(coefficients.length, other.coefficients.length);

    int largestExp = exponents[exponents.length - 1] + other.exponents[other.exponents.length - 1] + 1;

    double[] newCoeff = new double[largestExp];
    int[] newExp = new int[largestExp];

    //Initializes exponents at each index
    for (int i = 0; i < newExp.length; i++) {
      newExp[i] = i;
    }

    for (int i = 0; i < coefficients.length; i++) { //Goes through first coefficient
      for (int j = 0; j < other.coefficients.length; j++) {
        double product = coefficients[i] * other.coefficients[j];
        int expSum = exponents[i] + other.exponents[j];
        newCoeff[expSum] += product;
      }
    }

    int count = 0;
    for (int n = 0; n < newCoeff.length; n++) {
      if (newCoeff[n] != 0) {
        count++;
      }
    }

    double[] finalCoeff = new double[count];
    int[] finalExp = new int[count];
    int idx = 0;
    for (int n = 0; n < newCoeff.length; n++) {
      if (newCoeff[n] != 0) {
        finalCoeff[idx] = newCoeff[n];
        finalExp[idx] = newExp[n];
        idx++;
      }
    }

    Polynomial product = new Polynomial(finalCoeff, finalExp);
    return product;
  }

  public void saveToFile(String text) throws IOException {
    String poly = "";

    for (int i = 0; i < coefficients.length; i++) {
      if (exponents[i] == 0) { //It's a constant
        poly += coefficients[i];
      } else { //Doesn't need an exponent
        poly += coefficients[i] + "x" + exponents[i];
      }

      if (i != coefficients.length - 1) { //If it's not the last term
        if (coefficients[i + 1] > 0) {
          poly += "+";
        }
      }
    }

    File newFile = new File("/Users/jamiehvizdos/b07lab1/b07lab1/" + text);
    boolean newFileCreated = newFile.createNewFile();

    PrintStream output = new PrintStream(newFile);
    output.print(poly);

  }
}
