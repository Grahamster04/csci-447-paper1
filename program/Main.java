import java.util.*;

public class Main {
    // CHANGEABLE ATTRIBUTES Depending
    //-------------------------------------
    public static final String FILE_NAME = "data/forestfires.data";
    //-------------------------------------


    public static final String breastCancer = "data/breast-cancer-wisconsin.data";
    public static final String car = "data/car.data";
    public static final String houseVotes = "data/house-votes-84.data";

    public static final String abalone = "data/abalone.data";
    public static final String machine = "data/machine.data";
    public static final String fires = "data/forestfires.data";

    public static boolean classification;
    public static int numAtributes;
    public static int numClasses = 1; //start at 1 for regression, change for clasification
    public static double lr = 0.01;
    public static int epocks = 200;

    public static ArrayList<normalizedData> data = new ArrayList<>();
    public static int splitIndex;
    public static int lastIndex;


    public static void main(String[] args) {
        //READ FILE FROM FILE NAME
        if (FILE_NAME.equals(abalone)) {
            System.out.println("FILE ABALONE");
            classification = false;
            numAtributes = 8;
            boolean[] abaloneNumericAttributes = new boolean[]{false, true, true, true, true, true, true, true, true}; //[cite: 1]
            Abalone.collectData();
            for (int i = 0; i < Abalone.data.size(); i++) {
                Abalone point = Abalone.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
            }
            Abalone.classify();
            normalizedData.setIsFeatureNumeric(abaloneNumericAttributes);
        }

        if (FILE_NAME.equals(breastCancer)) {
            System.out.println("FILE BREAST CANCER");
            classification = true;
            numAtributes = 10;
            numClasses = 2;
            boolean[] breastCancerNumericAtributes = new boolean[]{true, true, true, true, true, true, true, true, true, true, true};
            BreastCancer.collectData();

            for (int i = 0; i < BreastCancer.data.size(); i++) {
                BreastCancer point = BreastCancer.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
                //System.out.println("Added:" + point + ", new:" + data.getLast());
            }
            BreastCancer.classify();
            normalizedData.setIsFeatureNumeric(breastCancerNumericAtributes);
        }

        if (FILE_NAME.equals(car)) {
            System.out.println("FILE CAR");
            classification = true;
            numAtributes = 6;
            numClasses = 4;
            boolean[] carNumericAtributes = new boolean[]{false, false, false, false, false, false};
            Car.collectData();
            for (int i = 0; i < Car.data.size(); i++) {
                Car point = Car.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
                //System.out.println("Added:" + point + ", new:" + data.getLast());
            }
            Car.classify();
            normalizedData.setIsFeatureNumeric(carNumericAtributes);
        }

        if (FILE_NAME.equals(fires)) {
            System.out.println("FILE FOREST FIRES");
            classification = false;
            numAtributes = 10;
            boolean[] forestNumericAtributes = new boolean[]{false, false, false, false, false, false, false, false, false, false};
            ForestFires.collectData();

            for (int i = 0; i < ForestFires.data.size(); i++) {
                ForestFires point = ForestFires.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
                //System.out.println("Added:" + point + ", new:" + data.getLast());
            }
            ForestFires.classify();
            normalizedData.setIsFeatureNumeric(forestNumericAtributes);
        }

        if (FILE_NAME.equals(houseVotes)) {
            System.out.println("FILE HOUSE VOTES");
            classification = true;
            numAtributes = 16;
            numClasses = 2;
            HouseVotes.collectData();
            boolean[] breastCancerNumericAtributes = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

            for (int i = 0; i < HouseVotes.data.size(); i++) {
                HouseVotes point = HouseVotes.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
                //System.out.println("Added:" + point + ", new:" + data.getLast());
            }
            HouseVotes.classify();
            normalizedData.setIsFeatureNumeric(breastCancerNumericAtributes);
        }

        if (FILE_NAME.equals(machine)) {
            System.out.println("FILE MACHINE");
            classification = false;
            numAtributes = 6;
            Machine.collectData();
            boolean[] machineNumericAttributes = new boolean[]{false, false, false, false, false, false}; // <-- LENGTH 6

            for (int i = 0; i < Machine.data.size(); i++) {
                Machine point = Machine.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
                //System.out.println("Added:" + point + ", new:" + data.getLast());
            }
            Machine.classify();
            normalizedData.setIsFeatureNumeric(machineNumericAttributes);
        }


        System.out.println(
                "Configured attributes: " + numAtributes
        );

        System.out.println(
                "Actual feature count: "
                        + data.get(0).getFeatures().length
        );

        System.out.println(
                "First feature vector: "
                        + Arrays.toString(
                        data.get(0).getFeatures()
                )
        );

        //FOR ALL OF THE FILES



        splitIndex = (data.size()) / 2;
        lastIndex = data.size();

        //RANDOMIZE DATA SET
        Collections.shuffle(data);
        ArrayList<normalizedData> testData = new ArrayList<>(data.subList(0, splitIndex));
        ArrayList<normalizedData> trainingData = new ArrayList<>(data.subList(splitIndex, lastIndex));


        double[] firsts = new double[5];
        double[] seconds = new double[5];

        if (classification)
            runLogistic5x2(trainingData,testData);
        else
            runRegression5x2(trainingData,testData);





    }

    private static void runLogistic5x2(ArrayList<normalizedData> trainingData, ArrayList<normalizedData> testData){
        double[] firsts = new double[5];
        double[] seconds = new double[5];
        double[] learningRates = {
                0.1,
                0.01,
                0.001
        };

        int[] epochValues = {
                50,
                100,
                250,
                500
        };

        for (double learningRate : learningRates) {
            for (int epochs : epochValues) {

                System.out.println();
                System.out.println("Learning Rate: " + learningRate + ", Epochs: " + epochs);

                for (int i = 0; i<5; i++) {

                    Collections.shuffle(data);
                    testData = new ArrayList<>(data.subList(0, splitIndex));
                    trainingData = new ArrayList<>(data.subList(splitIndex, lastIndex));

                    double result = runLinear(trainingData, testData, classification, learningRate, epochs);
                    firsts[i] = result;
                    result = runLinear(testData, trainingData, classification, learningRate, epochs);
                    seconds[i] = result;
                }
                System.out.println(Arrays.toString(firsts));
                System.out.println(Arrays.toString(seconds));
            }
        }
    }

    private static void runRegression5x2(ArrayList<normalizedData> trainingData, ArrayList<normalizedData> testData){
        double[] firsts = new double[5];
        double[] seconds = new double[5];
        double[] learningRates = {
                0.01,
                0.001,
                0.0001
        };

        int[] epochValues = {
                100,
                250,
                500,
                1000
        };

        for (double learningRate : learningRates) {
            for (int epochs : epochValues) {

                System.out.println();
                System.out.println("Learning Rate: " + learningRate + ", Epochs: " + epochs);

                for (int i = 0; i<5; i++) {

                    Collections.shuffle(data);
                    testData = new ArrayList<>(data.subList(0, splitIndex));
                    trainingData = new ArrayList<>(data.subList(splitIndex, lastIndex));

                    double result = runLinear(trainingData, testData, classification, learningRate, epochs);
                    firsts[i] = result;
                    result = runLinear(testData, trainingData, classification, learningRate, epochs);
                    seconds[i] = result;
                }
                System.out.println(Arrays.toString(firsts));
                System.out.println(Arrays.toString(seconds));
            }
        }
    }


    private static double runLinear(ArrayList<normalizedData> trainingData, ArrayList<normalizedData> testData, boolean classification, double lr, int epocks) {
        if (classification){

            LogisticRegression logReg = new LogisticRegression(numAtributes, numClasses, lr, epocks);
            logReg.train(trainingData);
            double accuracy =logReg.testAccuracy(testData);
            System.out.println("Logistic Regression Test Accuracy: " + accuracy + "%");
            return accuracy;
        }
        else {
            LinearRegression linReg = new LinearRegression(numAtributes, lr, epocks);
            linReg.train(trainingData);
            double mse = linReg.testMSE(testData);
            System.out.println("Linear Regression Test MSE: " + mse);
            return mse;
        }
    }
}