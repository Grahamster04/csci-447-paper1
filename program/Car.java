import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Car implements normalizedFeatures {
  //Car Evaluation [Classification]
// Overview: The data is on evaluations of car acceptability based on price, comfort, and technical
// specifications.
// Predictor: CAR: unacc, acc, good, vgood
// Source: Jozef Stefan Institute, Yugoslavia (Slovenia), 1988
// URL: https://archive.ics.uci.edu/ml/datasets/Car+Evaluation
// Notes: All of the features should be treated as if they are nominal even though, technically, they are
// ordinal. This is a four-class problem

  static ArrayList<Car> data = new ArrayList<>();

  public String buying;
  public String maint;
  public String doors;
  public String persons;
  public String lugBoot;
  public String safety;
  public String condition;

  Car(String b, String m, String d, String p, String lB, String s, String c) {
    this.buying = b;
    this.maint = m;
    this.doors = d;
    this.persons = p;
    this.lugBoot = lB;
    this.safety = s;
    this.condition = c;
  }

  public static ArrayList<Car> collectData() {
    try (BufferedReader br = new BufferedReader(new FileReader("data/car.data"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] split = line.split(",");
        Car temp = new Car (
                split[0],
                split[1],
                split[2],
                split[3],
                split[4],
                split[5],
                split[6]);
        data.add(temp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  @Override
  public double[] getFeatures() {
    return new double[] {
            encodeBuying(),
            encodeMaint(),
            encodeDoors(),
            encodePersons(),
            encodeLugBoot(),
            encodeSafety()
    };
  }

  private double encodeBuying() {
    switch (buying) {
      case "vhigh": return 0.0;
      case "high":  return 0.333;
      case "med":   return 0.666;
      default:   return 1.0;
    }
  }

  private double encodeMaint() {
    switch (this.maint) {
      case "vhigh": return 0.0;
      case "high":  return 0.333;
      case "med":   return 0.666;
      default:   return 1.0;
    }
  }

  private double encodeDoors() {
    switch (this.doors) {
      case "2":     return 0.0;
      case "3":     return 0.333;
      case "4":     return 0.666;
      default: return 1.0;
    }
  }

  private double encodePersons() {
    switch (this.persons) {
      case "2":    return 0.0;
      case "4":    return 0.5;
      default: return 1.0;
    }
  }

  private double encodeLugBoot() {
    switch (lugBoot) {
      case "small": return 0.0;
      case "med":   return 0.5;
      default:   return 1.0;
    }
  }

  private double encodeSafety() {
    switch (this.safety) {
      case "low":  return 0.0;
      case "med":  return 0.5;
      default: return 1.0;
    }
  }

  @Override
  public double getLable() {
    switch (condition) {
      case "unnac": return 0.0;
      case "acc": return 0.33;
      case "good": return 0.66;
      default: return 1.0;
    }
  }

  public static void classify() {
    int u = 0;
    int a = 0;
    int g = 0;
    int v = 0;

    for (Car entry : data) {
      if (entry.condition.equals("unacc")) {
        u++;
      } else if (entry.condition.equals("acc")){
        a++;
      } else if (entry.condition.equals("good")){
        g++;
      } else {
        v++;
      }
    }

    int[] numbers = {u, a, g, v};
    Arrays.sort(numbers);

    System.out.println("unacc: " + u + "\nacc: " + a + "\ngood: " + g + "\nv-good: " + v);

    if (numbers[numbers.length-1] == u) {
      System.out.println("unacceptable");
    } else if (numbers[numbers.length-1] == a) {
      System.out.println("acceptable");
    } else if (numbers[numbers.length-1] == g) {
      System.out.println("good");
    } else {
      System.out.println("very good");
    }
  }
}
