import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BreastCancer implements normalizedFeatures {
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

  public static ArrayList<BreastCancer> data = new ArrayList<>();

  public int sampleCode; public static int sampleCodeMax = Integer.MIN_VALUE; public static int sampleCodeMin = Integer.MAX_VALUE;
  public int clumpThickness; public static int clumpThicknessMax = Integer.MIN_VALUE; public static int clumpThicknessMin = Integer.MAX_VALUE;
  public int uniformitySize; public static int uniformitySizeMax = Integer.MIN_VALUE; public static int uniformitySizeMin = Integer.MAX_VALUE;
  public int uniformityShape; public static int uniformityShapeMax = Integer.MIN_VALUE; public static int uniformityShapeMin = Integer.MAX_VALUE;
  public int marginalAdhesion; public static int marginalAdhesionMax = Integer.MIN_VALUE; public static int marginalAdhesionMin = Integer.MAX_VALUE;
  public int singleCellSize; public static int singleCellSizeMax = Integer.MIN_VALUE; public static int singleCellSizeMin = Integer.MAX_VALUE;
  public int bareNuclei; public static int bareNucleiMax = Integer.MIN_VALUE; public static int bareNucleiMin = Integer.MAX_VALUE;
  public int blandChromatin; public static int blandChromatinMax = Integer.MIN_VALUE; public static int blandChromatinMin = Integer.MAX_VALUE;
  public int normalNucleoli; public static int normalNucleoliMax = Integer.MIN_VALUE; public static int normalNucleoliMin = Integer.MAX_VALUE;
  public int mitoses; public static int mitosesMax = Integer.MIN_VALUE; public static int mitosesMin = Integer.MAX_VALUE;
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

        if (temp.sampleCode > sampleCodeMax) { sampleCodeMax = temp.sampleCode; } if (temp.sampleCode < sampleCodeMin) { sampleCodeMin = temp.sampleCode; }
        if (temp.clumpThickness > clumpThicknessMax) { clumpThicknessMax = temp.clumpThickness; } if (temp.clumpThickness < clumpThicknessMin) { clumpThicknessMin = temp.clumpThickness; }
        if (temp.uniformitySize > uniformitySizeMax) { uniformitySizeMax = temp.uniformitySize; } if (temp.uniformitySize < uniformitySizeMin) { uniformitySizeMin = temp.uniformitySize; }
        if (temp.uniformityShape > uniformityShapeMax) { uniformityShapeMax = temp.uniformityShape; } if (temp.uniformityShape < uniformityShapeMin) { uniformityShapeMin = temp.uniformityShape; }
        if (temp.marginalAdhesion > marginalAdhesionMax) { marginalAdhesionMax = temp.marginalAdhesion; } if (temp.marginalAdhesion < marginalAdhesionMin) { marginalAdhesionMin = temp.marginalAdhesion; }
        if (temp.singleCellSize > singleCellSizeMax) { singleCellSizeMax = temp.singleCellSize; } if (temp.singleCellSize < singleCellSizeMin) { singleCellSizeMin = temp.singleCellSize; }
        if (temp.bareNuclei > bareNucleiMax) { bareNucleiMax = temp.bareNuclei; } if (temp.bareNuclei < bareNucleiMin) { bareNucleiMin = temp.bareNuclei; }
        if (temp.blandChromatin > blandChromatinMax) { blandChromatinMax = temp.blandChromatin; } if (temp.blandChromatin < blandChromatinMin) { blandChromatinMin = temp.blandChromatin; }
        if (temp.normalNucleoli > normalNucleoliMax) { normalNucleoliMax = temp.normalNucleoli; } if (temp.normalNucleoli < normalNucleoliMin) { normalNucleoliMin = temp.normalNucleoli; }
        if (temp.mitoses > mitosesMax) { mitosesMax = temp.mitoses; } if (temp.mitoses < mitosesMin) { mitosesMin = temp.mitoses; }

        data.add(temp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  private static Integer parseInteger(String input) {
    if (input == null || input.trim().equals("?")) {
      return 100; // Or return 0 if primitive int is required
    }
    return Integer.parseInt(input.trim());
  }

  @Override
  public double[] getFeatures() {
    return new double[] {
            encodeSampleCode(),
            encodeClumpThickness(),
            encodeUniformitySize(),
            encodeUniformityShape(),
            encodeMarginalAdhesion(),
            encodeSingleCellSize(),
            encodeBareNuclei(),
            encodeBlandChromatin(),
            encodeNormalNucleoli(),
            encodeMitoses(),
    };
  }

  private double encodeSampleCode() { return (double) (sampleCode - sampleCodeMin) / (sampleCodeMax - sampleCodeMin);}
  private double encodeClumpThickness() { return (double) (clumpThickness - clumpThicknessMin) / (clumpThicknessMax - clumpThicknessMin);}
  private double encodeUniformitySize() { return (double) (uniformitySize - uniformitySizeMin) / (uniformitySizeMax - uniformitySizeMin);}
  private double encodeUniformityShape() { return (double) (uniformityShape - uniformityShapeMin) / (uniformityShapeMax - uniformityShapeMin);}
  private double encodeMarginalAdhesion() { return (double) (marginalAdhesion - marginalAdhesionMin) / (marginalAdhesionMax - marginalAdhesionMin);}
  private double encodeSingleCellSize() { return (double) (singleCellSize - singleCellSizeMin) / (singleCellSizeMax - singleCellSizeMin);}
  private double encodeBareNuclei() { return (double) (bareNuclei - bareNucleiMin) / (bareNucleiMax - bareNucleiMin);}
  private double encodeBlandChromatin() { return (double) (blandChromatin - blandChromatinMin) / (blandChromatinMax - blandChromatinMin);}
  private double encodeNormalNucleoli() { return (double) (normalNucleoli - normalNucleoliMin) / (normalNucleoliMax - normalNucleoliMin);}
  private double encodeMitoses() { return (double) (mitoses - mitosesMin) / (mitosesMax - mitosesMin);}

  @Override
  public double getLable() {
    return classes;
  }

  public static void classify () {
    int b = 0;
    int m = 0;
    int total = data.size();

    for (BreastCancer entry : data) {
      if (entry.classes == 2) {
        b++;
      } else {
        m++;
      }
    }

    double accuracy;
    System.out.println("Benign: " + b + "\nMalignant: " + m);
    if (b > m) {
      accuracy = (double) b / total * 100;
      System.out.println("benign");
      System.out.println("Null Model Accuracy: " + accuracy + "%");
  } else {
      accuracy = (double) m / total * 100;
      System.out.println("malignant");
      System.out.println("Null Model Accuracy: " + accuracy + "%");}
  }

  public String toString() {
    return  sampleCode + "," + clumpThickness + "," + uniformitySize + "," + uniformityShape +
            "," + marginalAdhesion + "," + singleCellSize + "," + bareNuclei + "," + blandChromatin +
            "," + normalNucleoli + "," + mitoses + "," + classes;
  }
}