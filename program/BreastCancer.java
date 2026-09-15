import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BreastCancer {
  //"Breast Cancer [Classification]
// Overview: This data describes characteristics of cell nuclei present in benign and malignant tumors.
// Predictor: Diagnosis: M or B
// Source: University of Wisconsin, 1993
// URL: https://archive.ics.uci.edu/ml/datasets/Breast+Cancer+Wisconsin+%28Original%29
// Notes: The classes are indicated with the values “2” and “4”, but these are just two discrete classes and
// should be treated as such. The features are all values in the range [1, 10] and should be treated
// either as numeric or ordinal features. The Sample code number should be discarded. Missing
// values may be imputed, or the examples with missing features may be dropped from the data
// set."

  static ArrayList<BreastCancer> data = new ArrayList<>();

  public int sampleCode;
  public int clumpThickness;
  public int uniformitySize;
  public int uniformityShape;
  public int marginalAdhesion;
  public int singleCellSize;
  public int bareNuclei;
  public int blandChromatin;
  public int normalNucleoli;
  public int mitoses;
  public int classes;

  BreastCancer(int sC, int cT, int uSi, int uSh, int mA, int sCZ, int bN, int bC, int nN, int m, int c) {
    this.sampleCode = sC;
    this.clumpThickness = cT;
    this.uniformitySize = uSi;
    this.uniformityShape = uSh;
    this.marginalAdhesion = mA;
    this.singleCellSize = sCZ;
    this.bareNuclei = bN;
    this.blandChromatin = bC;
    this.normalNucleoli = nN;
    this.mitoses = m;
    this.classes = c;
  }

  public static ArrayList<BreastCancer> collectData() {
    try (BufferedReader br = new BufferedReader(new FileReader("data/breast-cancer-wisconsin.data"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] split = line.split(",");

        BreastCancer temp = new BreastCancer(
                parseInteger(split[0]),
                parseInteger(split[1]),
                parseInteger(split[2]),
                parseInteger(split[3]),
                parseInteger(split[4]),
                parseInteger(split[5]),
                parseInteger(split[6]),
                parseInteger(split[7]),
                parseInteger(split[8]),
                parseInteger(split[9]),
                parseInteger(split[10])
        );
        data.add(temp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (BreastCancer entry : data) {
      System.out.println(entry);
    }
    return data;
  }

  private static Integer parseInteger(String input) {
    if (input == null || input.trim().equals("?")) {
      return 100; // Or return 0 if primitive int is required
    }
    return Integer.parseInt(input.trim());
  }

  public static void classify () {
    int b = 0;
    int m = 0;
    for (BreastCancer entry : data) {
      if (entry.classes == 2) {
        b++;
      } else {
        m++;
      }
    }

    System.out.println("Benign: " + b + "\nMalignant: " + m);
    if (b > m) {
      System.out.println("benign");
    } else {
      System.out.println("malignant");
    }
  }

  public String toString() {
    return  sampleCode + "," + clumpThickness + "," + uniformitySize + "," + uniformityShape +
            "," + marginalAdhesion + "," + singleCellSize + "," + bareNuclei + "," + blandChromatin +
            "," + normalNucleoli + "," + mitoses + "," + classes;
  }
}