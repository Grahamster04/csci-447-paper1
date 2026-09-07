Public class Abalone {
// "Overview: The data describes the physical measurements of abalone and the associated age.
// Predictor: Rings (int)
// Source: Marine Research Laboratories, Tasmania, 1995
// URL: https://archive.ics.uci.edu/ml/datasets/Abalone
// Notes: Observe that, even though the “.names” file lists 29 classes, this is not to be treated like a
// classification problem. You are trying to predict (regress) the number of rings. All of the features
// are real-valued except for sex. You may choose between one-hot coding this feature, or discarding
// it."
  public char sex;
  public float length;
  public float diameter;
  public float height;
  public float wholeWeight;
  public float shuckedWeight;
  public float visceraWeight;
  public float shellWeight;
  public int rings;
  Abalone(char s, float l, float d, float h, float wW, float sW, float vW, float sW, int r) {
    this.sex = s;
    this.length = l;
    this.diameter = d;
    this.height = h;
    this.wholeWeight = wW;
    this.shuckedWeight = sW;
    this.visceraWeight = vW;
    this.shellWeight = sW;
    this.rings = r;
  }
}
