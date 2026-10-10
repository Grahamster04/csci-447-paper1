public class normalizedData {


    private static boolean[] isFeatureNumeric;
    private final double[] features;
    private final double target;

    public normalizedData(double[] features, double target){
        this.features = features;
        this.target = target;
    }
    public static void setIsFeatureNumeric(boolean[] booleans){
        isFeatureNumeric = booleans;
    }

    public double[] getFeatures(){
        return features;
    }

    public double getTarget(){
        return target;
    }

    public static boolean getIsFeatureNumeric(int index){
        return isFeatureNumeric[index];
    }

    public String toString() {
        String temp = "[";
        for (int i = 0; i < features.length; i++){
            temp = temp + features[i] + ", ";
        }
        temp = temp + "]";
        return temp;
    }
}
