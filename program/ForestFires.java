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
  public int xCor;
  public int yCor;
  public String month; //Review this encoding. Maybe cast to integer?
  public String day;
  public float ffmc;
  public float dmc;
  public float dc;
  public float isi;
  public float temp;
  public float rh;
  public float wind;
  public float rain;
  public float area;

  ForestFires(int x, int y, String m, String d, float f, float dm, float dc, float i, float t, float r, float w, float ra, float ar) {
    this.xCor = x;
    this.yCor = y;
    this.month = m;
    this.day = d;
    this.ffmc = f;
    this.dmc = dm;
    this.dc = dc;
    this.isi = i;
    this.temp = t;
    this.rh = r;
    this.wind = w;
    this.rain = ra;
    this.area = ar;
  }
  
}
