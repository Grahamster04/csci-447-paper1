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
}
