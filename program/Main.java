import java.util.*;

public class Main {
    // CHANGEABLE ATTRIBUTES Depending
    //-------------------------------------
    public static final String FILE_NAME = "data/machine.data";
    //-------------------------------------

    public static final String abalone = "data/abalone.data";
    public static final String breastCancer = "data/breast-cancer-wisconsin.data";
    public static final String car = "data/car.data";
    public static final String fires = "data/forestfires.data";
    public static final String houseVotes = "data/house-votes-84.data";
    public static final String machine = "data/machine.data";

    public static boolean classification;
    public static int numAtributes;
    public static int numClasses = 1; //start at 1 for regression, change for clasification

    public static ArrayList<normalizedData> data = new ArrayList<>();
    public static int splitIndex;
    public static int lastIndex;


    public static void main(String[] args) {
        //READ FILE FROM FILE NAME
        if (FILE_NAME.equals(abalone)) {
            System.out.println("FILE ABALONE");
            classification = false;
            numAtributes = 8;
            //numClasses = 27 ish? 27 max;
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
            numAtributes = 9;
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


        //FOR ALL OF THE FILES

        //RANDOMIZE DATA SET
        Collections.shuffle(data);

        splitIndex = (data.size()) / 2;
        lastIndex = data.size();

        ArrayList<normalizedData> testData = new ArrayList<>(data.subList(0, splitIndex));
        ArrayList<normalizedData> trainingData = new ArrayList<>(data.subList(splitIndex, lastIndex));

        //TESTING CODE LOGISTIC
        //LogisticRegression logModel = new LogisticRegression(numAtributes, numClasses, .01, 100);
        //logModel.train(trainingData, testData);

        //TETSING CODE LINEAR
        //LinearRegression linModel = new LinearRegression(numAtributes, .001, 500);
        //linModel.train(trainingData);
        //System.out.println("Test Data average MSE: " + linModel.testMSE(testData));

        //TESTING CODE FEEDFORWARD
        /*
        System.out.println(
                "Example features: "
                        + Arrays.toString(
                        trainingData.get(0).getFeatures()
                )
        );

        System.out.println(
                "Example target: "
                        + trainingData.get(0).getTarget()
        );
        FeedforwardNetwork network = new FeedforwardNetwork(numAtributes,8,4,numClasses,0.01,500,classification);    // Classification mode
        network.train(trainingData);
        if (classification) {
            System.out.println("Average accuracy:"+network.testAccuracy(testData));
        }
        else {
            System.out.println("Average MSE:"+network.testMSE(testData));
        }

        // TESTING CODE FEEDFORWARD NETWORK

        System.out.println("\n---------------------\n\n" + "TESTING FEEDFORWARD NETWORK\n");
        ArrayList<normalizedData> networkTrainingData = new ArrayList<>();
        ArrayList<normalizedData> networkTestData = new ArrayList<>();

        for (normalizedData point : trainingData) {
            double target = point.getTarget();

            networkTrainingData.add(
                    new normalizedData(point.getFeatures(), target)
            );
        }

        for (normalizedData point : testData) {
            double target = point.getTarget();
            if (FILE_NAME.equals(breastCancer)) {
                target = (target == 2.0) ? 0.0 : 1.0;
            }
            networkTestData.add(
                    new normalizedData(point.getFeatures(), target)
            );
        }

        if (classification && numClasses == 0) {
            numClasses = 2;
        }

        int numOutputs = classification ? numClasses : 1;

        FeedforwardNetwork feedforwardModel =
                new FeedforwardNetwork(
                        trainingData.get(0).getFeatures().length, 12, 8, numOutputs,0.01, 500, classification
                );

        feedforwardModel.train(networkTrainingData);

        if (classification) {
            System.out.println("\nFEEDFORWARD CLASSIFICATION RESULTS:");
            double accuracy =
                    feedforwardModel.testAccuracy(networkTestData);
            System.out.println("Accuracy: " + accuracy + "%");

        } else {
            System.out.println("\nFEEDFORWARD REGRESSION RESULTS:");
            double mse =
                    feedforwardModel.testMSE(networkTestData);
            System.out.println("Mean Squared Error: " + mse);
        }

         */

        //AUTOENCODER TEST
        Autoencoder autoencoder = new Autoencoder(numAtributes,3,.01,500);
        autoencoder.train(trainingData);

        System.out.println("Training reconstruction MSE: " + autoencoder.testReconstructionMSE(trainingData));
        System.out.println("Test reconstruction MSE: " + autoencoder.testReconstructionMSE(testData));
    }
}