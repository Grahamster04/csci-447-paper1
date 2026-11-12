import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Autoencoder {

    private double[][] encoderWeights;
    private double[][] decoderWeights;

    private double[] encoderBiases;
    private double[] decoderBiases;

    private double learningRate;
    private int epochs;

    private Random rand = new Random();

    public Autoencoder(int numFeatures, int encodingSize, double learningRate, int epochs) {
        if (encodingSize >= numFeatures) {
            throw new IllegalArgumentException("Encoding size must be smaller " + "than the number of features.");
        }

        encoderWeights = new double[encodingSize][numFeatures];
        decoderWeights = new double[numFeatures][encodingSize];
        encoderBiases = new double[encodingSize];
        decoderBiases = new double[numFeatures];

        this.learningRate = learningRate;
        this.epochs = epochs;

        initializeWeights();
    }

    private void initializeWeights() {

        initializeLayer(encoderWeights);
        initializeLayer(decoderWeights);
    }

    private void initializeLayer(double[][] weights) {

        double limit = Math.sqrt(6.0 / (weights[0].length + weights.length));

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] =(rand.nextDouble() * 2.0 - 1.0) * limit;
            }
        }
    }
    private double tanh(double value) {
        return Math.tanh(value);
    }

    private double tanhDerivative(double activatedValue) {
        return 1.0 - activatedValue * activatedValue;
    }
    public double[] encode(double[] features) {

        if (features.length != encoderWeights[0].length) {
            throw new IllegalArgumentException("Expected " + encoderWeights[0].length + " features, but received " + features.length);
        }

        double[] encoded = new double[encoderWeights.length];

        for (int node = 0; node < encoderWeights.length; node++) {
            double sum = encoderBiases[node];
            for (int feature = 0; feature < features.length; feature++) {
                sum += encoderWeights[node][feature] * features[feature];
            }
            encoded[node] = tanh(sum);
        }

        return encoded;
    }

    public double[] decode(double[] encoded) {

        if (encoded.length != decoderWeights[0].length) {
            throw new IllegalArgumentException("Expected " + decoderWeights[0].length + " encoded values, but received " + encoded.length);
        }

        double[] reconstruction = new double[decoderWeights.length];
        for (int output = 0; output < decoderWeights.length; output++) {
            double sum = decoderBiases[output];
            for (int encodedIndex = 0; encodedIndex < encoded.length; encodedIndex++) {
                sum += decoderWeights[output][encodedIndex] * encoded[encodedIndex];
            }
            reconstruction[output] = sum;
        }

        return reconstruction;
    }

    public double[] reconstruct(double[] features) {
        double[] encoded = encode(features);
        return decode(encoded);
    }

    public double computeLoss(double[] reconstruction, double[] originalFeatures) {

        double totalSquaredError = 0.0;

        for (int i = 0; i < originalFeatures.length; i++) {
            double error = reconstruction[i] - originalFeatures[i];
            totalSquaredError += error * error;
        }

        return totalSquaredError / originalFeatures.length;
    }

    public double trainOnExample(double[] features){
        double[] encoded = encode(features);
        double[] reconstruction = decode(encoded);
        double loss = computeLoss(reconstruction, features);

        double[] decoderErrors = new double[reconstruction.length];
        for(int i = 0; i< reconstruction.length; i++){
            decoderErrors[i] = reconstruction[i]-features[i];
        }

        double[] encoderErrors = new double[encoded.length];
        for (int i = 0; i< encoded.length; i++){
            double error = 0;
            for (int j = 0; j<decoderErrors.length;j++){
                error += decoderWeights[j][i] *decoderErrors[j];
            }
            encoderErrors[i] = error * tanhDerivative(encoded[i]);

        }
        updateDecoder(encoded, decoderErrors);
        updateEncoder(features, encoderErrors);

        return loss;
    }

    private void updateDecoder(double[] encoded, double[] decoderErrors) {

        for (int i = 0; i < decoderWeights.length; i++) {
            for (int j = 0; j < decoderWeights[i].length; j++) {
                decoderWeights[i][j] -= learningRate * decoderErrors[i] * encoded[j];
            }

            decoderBiases[i] -= learningRate * decoderErrors[i];
        }
    }

    private void updateEncoder(double[] features, double[] encoderErrors) {

        for (int i = 0; i < encoderWeights.length; i++) {
            for (int j = 0; j < encoderWeights[i].length; j++) {
                encoderWeights[i][j] -= learningRate * encoderErrors[i] * features[j];
            }

            encoderBiases[i] -= learningRate * encoderErrors[i];
        }
    }
    public void train(ArrayList<normalizedData> trainingData) {

        for (int epoch = 0; epoch < epochs; epoch++) {

            ArrayList<normalizedData> shuffled = new ArrayList<>(trainingData);

            Collections.shuffle(shuffled);

            double totalLoss = 0;

            for (normalizedData point : shuffled) {
                double[] features = point.getFeatures();
                totalLoss +=  trainOnExample(features);
            }

            double averageLoss = totalLoss / trainingData.size();

            System.out.println("Epoch: " + epoch + ", Average Loss: " + averageLoss);
        }


    }

    public double testReconstructionMSE(ArrayList<normalizedData> testData) {

        double totalLoss = 0.0;
        for (normalizedData point : testData) {
            double[] features = point.getFeatures();
            double[] reconstruction = reconstruct(features);
            totalLoss += computeLoss(reconstruction, features);
        }

        return totalLoss / testData.size();
    }
}