public class normalizedData {
    private final double[] features;
    private final double target;

    public normalizedData(double[] features, double target){
        this.features = features;
        this.target = target;
    }

    public double[] getFeatures(){
        return features;
    }

    public double getTarget(){
        return target;
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < features.length; i++){
            temp = temp + features[i] + ", ";
        }
        return temp;
    }
}
