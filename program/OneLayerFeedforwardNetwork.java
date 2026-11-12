
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class OneLayerFeedforwardNetwork {

    private double[][] hiddenWeights;
    private double[][] outputWeights;

    private double[] hiddenBiases;
    private double[] outputBiases;

    private double learningRate;
    private int epochs;
    private boolean classification;

    private Random rand = new Random();

    // Constructor
    public OneLayerFeedforwardNetwork(
            int numFeatures,
            int hidden1,
            int numOutputs,
            double lr,
            int e,
            boolean isClassification) {

        hiddenWeights = new double[hidden1][numFeatures];
        outputWeights = new double[numOutputs][hidden1];

        hiddenBiases = new double[hidden1];
        outputBiases = new double[numOutputs];

        learningRate = lr;
        epochs = e;
        classification = isClassification;

        initializeWeights();
    }

    // Initialize weights with small random values
    private void initializeWeights() {

        initializeLayer(hiddenWeights);
        initializeLayer(outputWeights);
    }

    private void initializeLayer(double[][] weights) {

        double limit = Math.sqrt(6.0 /
                (weights[0].length + weights.length));

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = (rand.nextDouble() * 2 - 1) * limit;
            }
        }
    }

    // Tanh activation function
    private double tanh(double x) {
        return Math.tanh(x);
    }

    // Derivative of tanh
    private double tanhDerivative(double activatedValue) {
        return 1 - activatedValue * activatedValue;
    }

    // Forward propagation through a layer
    private double[] forwardLayer(
            double[] inputs,
            double[][] weights,
            double[] biases) {

        double[] outputs = new double[weights.length];

        for (int i = 0; i < weights.length; i++) {

            double sum = biases[i];

            for (int j = 0; j < inputs.length; j++) {
                sum += weights[i][j] * inputs[j];
            }

            outputs[i] = tanh(sum);
        }

        return outputs;
    }

    // Softmax output for classification
    private double[] softmax(double[] scores) {

        double max = scores[0];
        for (double score : scores) {
            max = Math.max(max, score);
        }

        double sum = 0;
        double[] probabilities = new double[scores.length];

        for (int i = 0; i < scores.length; i++) {
            probabilities[i] = Math.exp(scores[i] - max);
            sum += probabilities[i];
        }

        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] /= sum;
        }

        return probabilities;
    }

    // Forward propagation through the entire network
    private double[] forward(
            double[] features) {

        double[] hidden1 = forwardLayer(features, hiddenWeights, hiddenBiases);

        double[] outputs = new double[outputWeights.length];

        for (int i = 0; i < outputWeights.length; i++) {
            outputs[i] = outputBiases[i];
            for (int j = 0; j < hidden1.length; j++) {
                outputs[i] += outputWeights[i][j] * hidden1[j];
            }
        }
        if (classification) {
            return softmax(outputs);
        }

        return outputs;
    }

    // Make a prediction
    public double[] predict(double[] features) {
        return forward(features);
    }

    // Classification prediction
    public double predictClass(double[] features) {

        double[] probabilities = predict(features);

        int predictedClass = 0;

        for (int i = 1; i < probabilities.length; i++) {
            if (probabilities[i] > probabilities[predictedClass]) {
                predictedClass = i;
            }
        }

        return predictedClass;
    }

    // Regression prediction
    public double predictValue(double[] features) {
        return predict(features)[0];
    }

    // Train on one example using backpropagation
    public void trainOnExample(double[] features, double target) {

        // Forward propagation
        double[] hidden1 = forwardLayer(
                features, hiddenWeights, hiddenBiases);

        double[] outputs = new double[outputWeights.length];

        for (int i = 0; i < outputWeights.length; i++) {

            outputs[i] = outputBiases[i];

            for (int j = 0; j < hidden1.length; j++) {
                outputs[i] += outputWeights[i][j] * hidden1[j];
            }
        }

        if (classification) {
            outputs = softmax(outputs);
        }

        // Calculate output layer errors
        double[] outputErrors = new double[outputs.length];

        if (classification) {

            for (int i = 0; i < outputs.length; i++) {
                outputErrors[i] = outputs[i] - (i == (int) target ? 1 : 0);
            }

        } else {

            outputErrors[0] = outputs[0] - target;
        }



        // Calculate first hidden layer errors
        double[] hidden1Errors = new double[hidden1.length];

        for (int i = 0; i < hidden1.length; i++) {

            double error = 0;

            for (int j = 0; j < outputErrors.length; j++) {
                error += outputWeights[j][i] * outputErrors[j];
            }

            hidden1Errors[i] =
                    error * tanhDerivative(hidden1[i]);
        }

        // Update output layer weights and biases
        for (int i = 0; i < outputWeights.length; i++) {

            for (int j = 0; j < outputWeights[i].length; j++) {
                outputWeights[i][j] -=
                        learningRate * outputErrors[i] * hidden1[j];
            }

            outputBiases[i] -= learningRate * outputErrors[i];
        }


        // Update first hidden layer weights and biases
        for (int i = 0; i < hiddenWeights.length; i++) {

            for (int j = 0; j < hiddenWeights[i].length; j++) {
                hiddenWeights[i][j] -=
                        learningRate * hidden1Errors[i] * features[j];
            }

            hiddenBiases[i] -= learningRate * hidden1Errors[i];
        }
    }

    // Calculate loss
    public double computeLoss(double[] predictions, double target) {

        if (classification) {

            double probability = predictions[(int) target];

            probability = Math.max(probability, 1e-15);

            return -Math.log(probability);

        } else {

            double error = predictions[0] - target;

            return error * error;
        }
    }

    // Train the network
    public void train(ArrayList<normalizedData> trainingData) {

        for (int epoch = 0; epoch < epochs; epoch++) {

            ArrayList<normalizedData> shuffled =
                    new ArrayList<>(trainingData);

            Collections.shuffle(shuffled);

            double totalLoss = 0;

            for (normalizedData point : shuffled) {

                double[] features = point.getFeatures();
                double target = point.getTarget();

                totalLoss += computeLoss(predict(features), target);
                trainOnExample(features, target);

            }

            double averageLoss = totalLoss / trainingData.size();

            System.out.println(
                    "Epoch: " + epoch +
                            ", Average Loss: " + averageLoss);
        }
    }

    // Calculate accuracy for classification
    public double testAccuracy(ArrayList<normalizedData> testData) {

        int correct = 0;

        for (normalizedData point : testData) {

            double prediction = predictClass(point.getFeatures());

            if (prediction == point.getTarget()) {
                correct++;
            }
        }

        return 100.0 * correct / testData.size();
    }

    // Calculate regression MSE
    public double testMSE(ArrayList<normalizedData> testData) {

        double totalError = 0;

        for (normalizedData point : testData) {

            double prediction = predictValue(point.getFeatures());

            double error = prediction - point.getTarget();

            totalError += error * error;
        }

        return totalError / testData.size();
    }
}

