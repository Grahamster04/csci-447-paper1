import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Car {
  //Car Evaluation [Classification]
// Overview: The data is on evaluations of car acceptability based on price, comfort, and technical
// specifications.
// Predictor: CAR: unacc, acc, good, vgood
// Source: Jozef Stefan Institute, Yugoslavia (Slovenia), 1988
// URL: https://archive.ics.uci.edu/ml/datasets/Car+Evaluation
// Notes: All of the features should be treated as if they are nominal even though, technically, they are
// ordinal. This is a four-class problem

  static ArrayList<Car> garage = new ArrayList<>();

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
        garage.add(temp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return garage;
  }

  public static void classify() {
    int u = 0;
    int a = 0;
    int g = 0;
    int v = 0;

    for (Car entry : garage) {
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
