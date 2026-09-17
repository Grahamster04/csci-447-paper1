import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Abalone implements normalizedFeatures{
  // "Overview: The data describes the physical measurements of abalone and the associated age.
// Predictor: Rings (int)
// Source: Marine Research Laboratories, Tasmania, 1995
// URL: https://archive.ics.uci.edu/ml/datasets/Abalone
// Notes: Observe that, even though the “.names” file lists 29 classes, this is not to be treated like a
// classification problem. You are trying to predict (regress) the number of rings. All of the features
// are real-valued except for sex. You may choose between one-hot coding this feature, or discarding
// it."
  public static ArrayList<Abalone> data = new ArrayList<>();

  public char sex;
  public float length; public static float lengthMax = Float.NEGATIVE_INFINITY; public static float lengthMin = Float.POSITIVE_INFINITY;
  public float diameter; public static float diameterMax = Float.NEGATIVE_INFINITY; public static float diameterMin = Float.POSITIVE_INFINITY;
  public float height; public static float heightMax = Float.NEGATIVE_INFINITY; public static float heightMin = Float.POSITIVE_INFINITY;
  public float wholeWeight; public static float wholeWeightMax = Float.NEGATIVE_INFINITY; public static float wholeWeightMin = Float.POSITIVE_INFINITY;
  public float shuckedWeight;  public static float shuckedWeightMax = Float.NEGATIVE_INFINITY; public static float shuckedWeightMin =Float.POSITIVE_INFINITY;
  public float visceraWeight;  public static float visceraWeightMax = Float.NEGATIVE_INFINITY; public static float visceraWeightMin = Float.POSITIVE_INFINITY;
  public float shellWeight; public static float shellWeightMax = Float.NEGATIVE_INFINITY; public static float shellWeightMin = Float.POSITIVE_INFINITY;
  public int rings;

  Abalone(char s, float l, float d, float h, float wW, float suW, float vW, float seW, int r) {
    this.sex = s;
    this.length = l;
    this.diameter = d;
    this.height = h;
    this.wholeWeight = wW;
    this.shuckedWeight = suW;
    this.visceraWeight = vW;
    this.shellWeight = seW;
    this.rings = r;
  }

  public static void collectData() {
    try (BufferedReader br = new BufferedReader(new FileReader("data/abalone.data"))) {
      String line;

      //Creates Abalone ojects for all lines of data and puts them in an array list
      while ((line = br.readLine()) != null) {
        String[] split = line.split(",");
        Abalone temp = new Abalone(
                split[0].trim().charAt(0),
                Float.parseFloat(split[1].trim()),
                Float.parseFloat(split[2].trim()),
                Float.parseFloat(split[3].trim()),
                Float.parseFloat(split[4].trim()),
                Float.parseFloat(split[5].trim()),
                Float.parseFloat(split[6].trim()),
                Float.parseFloat(split[7].trim()),
                Integer.parseInt(split[8].trim())
        );

        //Used for normalization, gets the highest number of all the catagories
        if(temp.length > lengthMax){ lengthMax = temp.length;}                            if(temp.length < lengthMin){ lengthMin = temp.length;}
        if(temp.diameter > diameterMax){ diameterMax = temp.diameter;}                    if(temp.diameter < diameterMin){ diameterMin = temp.diameter;}
        if(temp.height > heightMax){ heightMax = temp.height;}                            if(temp.height < heightMin){ heightMin = temp.height;}
        if(temp.wholeWeight > wholeWeightMax){ wholeWeightMax = temp.wholeWeight;}        if(temp.wholeWeight < wholeWeightMin){ wholeWeightMin = temp.wholeWeight;}
        if(temp.shuckedWeight > shuckedWeightMax){ shuckedWeightMax = temp.shuckedWeight;}if(temp.shuckedWeight < shuckedWeightMin){ shuckedWeightMin = temp.shuckedWeight;}
        if(temp.visceraWeight > visceraWeightMax){ visceraWeightMax = temp.visceraWeight;}if(temp.visceraWeight < visceraWeightMin){ visceraWeightMin = temp.visceraWeight;}
        if(temp.shellWeight > shellWeightMax){ shellWeightMax = temp.shellWeight;}        if(temp.shellWeight < shellWeightMin){ shellWeightMin = temp.shellWeight;}

        data.add(temp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  @Override
  public double[] getFeatures() {
    return new double[] {
            encodeSex(),
            encodeLength(),
            encodeDiameter(),
            encodeHeight(),
            encodeWholeWeight(),
            encodeShuckedWeight(),
            encodeVisceraWeigth(),
            encodeShellWeight()
    };
  }

  private double encodeShellWeight() {
    return (shellWeight - shellWeightMin)/(shellWeightMax-shellWeightMin);
  }

  private double encodeVisceraWeigth() {
    return (visceraWeight -visceraWeightMin)/(visceraWeightMax - visceraWeightMin);
  }

  private double encodeShuckedWeight() {
    return (shuckedWeight-visceraWeightMin)/(shuckedWeightMax-visceraWeightMin);
  }

  private double encodeWholeWeight() {
    return (wholeWeight-visceraWeightMin)/ (wholeWeightMax-visceraWeightMin);
  }

  private double encodeHeight() {
    return (height-heightMin)/(heightMax-heightMin);
  }

  private double encodeDiameter() {
    return (diameter-diameterMin)/(diameterMax-diameterMin);
  }

  private double encodeLength() {
    return (length-lengthMin)/(lengthMax-lengthMin);
  }

  private double encodeSex() {
    if(sex == 'F')
      return 1;
    else
      return 0;
  }

  @Override
  public double getLable() {
    return rings;
  }

  public static void classify () {
    float averageRings = 0;
    int numAbalones = 0;
    for (Abalone entry : data) {
      averageRings += entry.rings;
      numAbalones++;
    }

    averageRings = averageRings / numAbalones;
    System.out.println("Average Rings: " + averageRings + "\nNumber of Abalones: " + numAbalones);
  }

  public String toString() {
    return sex + "," + length + "," + diameter + "," + height + "," + wholeWeight + "," +
            shuckedWeight + "," + visceraWeight + "," + shellWeight + "," + rings;
  }
}
