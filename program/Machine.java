public class Machine {
//    Overview: The data describes relative CPU performance described by features such as cycle time,
//    memory size, etc.
//    Predictor: PRP (int)
//    Source: Tel Aviv University, Israel, 1987
//    URL: https://archive.ics.uci.edu/ml/datasets/Computer+Hardware
//    Notes: The features for vendor name and model name are not useful fo regression, so these features should
//    be discarded. All of the remaining features are real-valued (numeric). The estimated relative
//    performance ERP values were estimated by the authors using a linear regression method. This
//    cannot be used as a feature. You should remove it from the feature set, but save it elsewhere. It
//    might be interesting to test your regressors against this value as part of your experimental results.
//    Even though a class distribution is provided, this is not a classification problem and should not
//    be treated as such
    public int MYCT;
    public int MMIN;
    public int MMAX;
    public int CACH;
    public int CHMIN;
    public int CHMAX;
    public int PRP;
    public int ERP;

    Machine(int mcyt, int mmin, int mmax, int cash, int chmin, int chmax, int prp, int erp){
        MYCT = mcyt;
        MMIN = mmin;
        MMAX = mmax;
        CACH =cash;
        CHMIN = chmin;
        CHMAX = chmax;
        PRP = prp;
        ERP = erp;
    }

}
