import java.util.ArrayList;
import java.util.Random;

public class LinearRegression {

    private double[] weights;
    private double biase;
    private double learningRate;
    private int epocks;

    public LinearRegression(int numFeatures, double lr, int e) {

        weights = new double[numFeatures];
        biase = 0;
        learningRate = lr;
        epocks = e;

        initializeWeights();

    }

    private void initializeWeights(){
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++){
            weights[i] = rand.nextDouble()*.01;

        }
    }

    public double predict(double[] features){

        double score = biase;
        //loop throught features
        for (int i = 0; i < features.length; i++) {
            score += weights[i]*features[i];
        }

        return score;
    }

    public double computeLoss(double prediction, double actualValue){
        return (prediction-actualValue)*(prediction-actualValue);
    }

    public void trainOnExample(double[] features, double actualValue){
        double prediction = predict(features);
        double error = prediction-actualValue;
        for (int i = 0; i<weights.length; i++){
            weights[i] -= error*learningRate*features[i]*2;
        }
        biase -= learningRate*error*2;
    }

    public void train(ArrayList<normalizedData> trainingData){
        for(int epock = 0; epock<epocks; epock++){
            double totalLoss = 0;
            for (normalizedData point : trainingData){
                double[] features = point.getFeatures();
                double target = point.getTarget();


                double loss = computeLoss(predict(features), target);
                trainOnExample(features, target);

                totalLoss += loss;
            }
            double averageLoss = totalLoss/trainingData.size();
            //Print Statements
            System.out.println("Epock: " + epock + ", Average Loss: " + averageLoss);

        }
    }

}
