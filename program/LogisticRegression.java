import java.util.ArrayList;
import java.util.Random;


public class LogisticRegression {

    private double[][] weights;
    private double[] biases;
    private double learningRate;
    private int epocks;
    private static boolean printit = false;


    public LogisticRegression(int numFeatures, int numClasses, double lr, int e) {

        weights = new double[numClasses][numFeatures];
        biases = new double[numClasses];
        learningRate = lr;
        epocks = e;

        initializeWeights();

    }

    public double getLearningRate() {
        return learningRate;
    }

    private void initializeWeights(){
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++){
            for (int j = 0; j < weights[i].length; j++){
                weights[i][j] = rand.nextDouble()*.01;
            }
        }
    }

    private double[] computeScores(double[] features){
        double[] scores = new double[weights.length];
        //Loop through classes
        for (int i = 0; i< weights.length; i++) {
            double score = biases[i];
            //loop throught features
            for (int j = 0; j < features.length; j++) {
                score += weights[i][j]*features[j];
            }
            scores[i] = score;
        }
        return scores;
    }

    private double[] softmax(double[] scores){
        double max = scores[0];
        for (double val : scores){
            if (val>max){
                max = val;
            }
        }

        double sum = 0;
        double[] probabliltys = new double[scores.length];

        for (int i  =0; i< scores.length; i++){
            probabliltys[i] = Math.exp(scores[i] -max);
            sum += probabliltys[i];
        }
        for (int i = 0; i< probabliltys.length; i++) {
            probabliltys[i] /= sum;
        }

        return probabliltys;
    }

    public double[] predictProbabilities(double[] features) {
        double[] scores = computeScores(features);
        return softmax(scores);
    }

    public double predictClass(double[] features) {

        double[] probabilities = predictProbabilities(features);

        int predictedClass = 0;

        for (int i = 1; i < probabilities.length; i++) {
            if (probabilities[i] > probabilities[predictedClass]) {
                predictedClass = i;
            }
        }

        return (double)predictedClass;
    }

    public double computeLoss(double[] probablilties, int correctClass){
        double correctClassProb = probablilties[correctClass];
        if (correctClassProb == 0){
            correctClassProb = .0000000000001;
        }
        return -Math.log(correctClassProb);
    }
    public double[] computeError(double[] probablilitys, double correctClass){
        double[] error = new double[probablilitys.length];
        for (int i = 0; i < error.length; i++){
            if (i == correctClass)
                error[i] = probablilitys[i] -1;
            else
                error[i] = probablilitys[i];
        }
        return error;

    }

    private void updateWeights(double[] features, double[] error) {

        for (int c = 0; c < weights.length; c++) {
            for (int f = 0; f < features.length; f++) {
                weights[c][f] -= learningRate * error[c] * features[f];
            }
        }
    }
    private void updateBiases(double[] error){
        for (int c = 0; c< biases.length; c++){
            biases[c] -= learningRate*error[c];
        }
    }

    public void trainOnExample(double[] features, double actualClass){
        double[] probabilities = predictProbabilities(features);
        double[] error = computeError(probabilities, actualClass);

        updateWeights(features, error);
        updateBiases(error);

    }
    public void printWeights() {

        for (int c = 0; c < weights.length; c++) {
            System.out.println("Class " + c);
            for (int f = 0; f < weights[c].length; f++) {
                System.out.print(weights[c][f] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void train(ArrayList<normalizedData> trainingData){
        for(int epock = 0; epock<epocks; epock++){
            double totalLoss = 0;
            for (normalizedData point : trainingData){
                double[] features = point.getFeatures();
                double target = point.getTarget();

                trainOnExample(features, target);
                double loss = computeLoss(predictProbabilities(features), (int)target);
                totalLoss += loss;
            }
            double averageLoss = totalLoss/trainingData.size();
            if (printit)
                System.out.println("Epock: " + epock + ", Average Loss: " + averageLoss);

        }
    }

    public double testAccuracy(ArrayList<normalizedData> testData) {

        int correct = 0;
        for (normalizedData point : testData){

            double prediction = predictClass(point.getFeatures());
            if (prediction == point.getTarget()){
                correct++;
            }
        }
        return 100.0* correct/testData.size();
    }
}
