import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ForestFires {
  //Forest Fires [Regression]
// Overview: This is a difficult regression task, where the aim is to predict the burned area of forest
// fires by using meteorological and other data.
// Predictor: area (float)
// Source: University of Minho, Portugal, 2007
// URL: https://archive.ics.uci.edu/ml/datasets/Forest+Fires
// Notes: The output area is very skewed toward 0.0. The authors recommend a log transform of the
// form ˆr(x) = ln(r(x) + 1), where r(x) is the target value associated with example x. The spatial
// coordinates should be treated as numeric features. The month and day features are ordinal
// and should use label encoding; however, be careful in computing distance since these are “cycle”
// features (e.g., in the absence of date information, the distance between December and January is
// only 1.0, not 11.0).

  static ArrayList<ForestFires> totalFires = new ArrayList<>();

  public int xCor; public static int xCorMax;
  public int yCor; public static int yCorMax;
  public String month; //Review this encoding. Maybe cast to integer?
  public String day;
  public float ffmc; public static float ffmcMax;
  public float dmc; public static float dmcMax;
  public float dc; public static float dcMax;
  public float isi; public static float isiMax;
  public float temp; public static float tempMax;
  public float rh; public static float rhMax;
  public float wind; public static float windMax;
  public float rain; public static float rainMax;
  public float area; public static float areaMax;

  ForestFires(int x, int y, String m, String dc, float f, float dm, float d, float i, float t, float r, float w, float ra, float ar) {
    this.xCor = x;
    this.yCor = y;
    this.month = m;
    this.day = dc;
    this.ffmc = f;
    this.dmc = dm;
    this.dc = d;
    this.isi = i;
    this.temp = t;
    this.rh = r;
    this.wind = w;
    this.rain = ra;
    this.area = ar;
  }

  public static ArrayList<ForestFires> collectData() {
    try (BufferedReader br = new BufferedReader(new FileReader("data/forestfires.data"))) {
      String header = br.readLine();
      String line;
      while ((line = br.readLine()) != null) {
        String[] split = line.split(",");
        ForestFires temp = new ForestFires(
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                split[2],
                split[3],
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5]),
                Float.parseFloat(split[6]),
                Float.parseFloat(split[7]),
                Float.parseFloat(split[8]),
                Float.parseFloat(split[9]),
                Float.parseFloat(split[10]),
                Float.parseFloat(split[11]),
                Float.parseFloat(split[12]));

        // Normalization for forest fires
        if (temp.xCor > xCorMax) { xCorMax = temp.xCor; }
        if (temp.yCor > yCorMax) { yCorMax = temp.yCor; }
        if (temp.ffmc > ffmcMax) { ffmcMax = temp.ffmc; }
        if (temp.dmc > dmcMax) { dmcMax = temp.dmc; }
        if (temp.dc > dcMax) { dcMax = temp.dc; }
        if (temp.isi > isiMax) { isiMax = temp.isi; }
        if (temp.temp > tempMax) { tempMax = temp.temp; }
        if (temp.rh > rhMax) { rhMax = temp.rh; }
        if (temp.wind > windMax) { windMax = temp.wind; }
        if (temp.rain > rainMax) { rainMax = temp.rain; }
        if (temp.area > areaMax) { areaMax = temp.area; }

        totalFires.add(temp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return totalFires;
  }

  private static Integer parseInteger(String input) {
    if (input == null || input.trim().equals("?")) {
      return 100; // Or return 0 if primitive int is required
    }
    return Integer.parseInt(input.trim());
  }

  public double[] getFeatures() {
    return new double[] {
            encodexCor(),
            encodeyCor(),
            encodeffmc(),
            encodedmc(),
            encodedc(),
            encodeisi(),
            encodetemp(),
            encoderh(),
            encodewind(),
            encoderain(),
            encodearea(),
    };
  }

  private double encodexCor() { return (double) xCor / xCorMax; }
  private double encodeyCor() { return (double) yCor / yCorMax; }
  private double encodeffmc() { return (double) ffmc / ffmcMax; }
  private double encodedmc() { return (double) dmc / dmcMax; }
  private double encodedc() { return (double) dc / dcMax; }
  private double encodeisi() { return (double) isi / isiMax; }
  private double encodetemp() { return (double) temp / tempMax; }
  private double encoderh() { return (double) rh / rhMax; }
  private double encodewind() { return (double) wind / windMax; }
  private double encoderain() { return (double) rain / rainMax; }
  private double encodearea() { return (double) area / areaMax; }

  public static void classify () {
    float averageArea = 0;
    int numFires = 0;
    for (ForestFires entry : totalFires) {
      averageArea += entry.area;
      numFires++;
    }

    averageArea = averageArea / numFires;
    System.out.println("Average Area: " + averageArea + "\nNumber of Fires: " + numFires);
  }
  
}
