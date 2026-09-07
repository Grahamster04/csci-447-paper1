public class Car{
  //Car Evaluation [Classification]
// Overview: The data is on evaluations of car acceptability based on price, comfort, and technical
// specifications.
// Predictor: CAR: unacc, acc, good, vgood
// Source: Jozef Stefan Institute, Yugoslavia (Slovenia), 1988
// URL: https://archive.ics.uci.edu/ml/datasets/Car+Evaluation
// Notes: All of the features should be treated as if they are nominal even though, technically, they are
// ordinal. This is a four-class problem
  public String buying;
  public String maint;
  public String doors;
  public String persons;
  public String lugBoot;
  public String safety;
  Car(String b, String m, String d, String p, String lB, String s) {
    this.buying = b;
    this.maint = m;
    this.doors = d;
    this.persons = p;
    this.lugBoot = lB;
    this.safety = s;
  }
}
