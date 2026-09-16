import java.util.*;

public class Main{
    // CHANGABLE ATRIBUTES Depending
    //-------------------------------------
    public static final String FILE_NAME = "data/breast-cancer-wisconsin.data";
    public static int kIndex = 0;
    public static int yIndex = 0;
    public static int eIndex = 0;

    public static int[] k = {1, 3, 5, 7, 9, 11, 15};
    public static double[] y = {.01, .1, .5, 1, 2, 5, 10 };
    public static double[] e = {.5, 1, 1.5, 2, 3, 4, 5};
    //-------------------------------------

    public static final String abalone = "data/abalone.data";
    public static final String breastCancer = "data/breast-cancer-wisconsin.data";
    public static final String car = "data/car.data";
    public static final String fires = "data/forestfires.data";
    public static final String houseVotes = "data/house-votes-84.data";
    public static final String machine = "data/machine.data";

    public static boolean classification;
    public static int numAtributes;

    public static ArrayList<normalizedData> data = new ArrayList<>();
    public static int splitIndex;
    public static int lastIndex;


    public static void main(String[] args) {
        //READ FILE FROM FILE NAME
        if (FILE_NAME.equals(abalone)){
            System.out.println("FILE ABALONE");
            classification = false;
            numAtributes = 9;
            Abalone.collectData();
            for (int i = 0; i < Abalone.data.size(); i++) {
                Abalone point = Abalone.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
                //System.out.println("Added:" + point + ", new:" + data.getLast());
            }


        }
        if (FILE_NAME.equals(breastCancer)){
            System.out.println("FILE BREAST CANCER");
            classification = true;
            numAtributes = 11;
            BreastCancer.collectData();

            for (int i = 0; i < BreastCancer.data.size(); i++) {
                BreastCancer point = BreastCancer.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
                //System.out.println("Added:" + point + ", new:" + data.getLast());
            }
            BreastCancer.classify();
        }
        if (FILE_NAME.equals(car)){
            System.out.println("FILE CAR");
            classification = true;
            numAtributes = 6;
            Car.collectData();
            Car.classify();
        }
        if (FILE_NAME == fires) {
            System.out.println("FILE FOREST FIRES");
            classification = false;
            numAtributes = 13;
            ForestFires.collectData();
            ForestFires.classify();
        }
        if (FILE_NAME == houseVotes) {
            System.out.println("FILE HOUSE VOTES");
            classification = true;
            numAtributes = 17;
            HouseVotes.collectData();
            HouseVotes.classify();
        }
        if (FILE_NAME == machine) {
            System.out.println("FILE MACHINE");
            classification = false;
            numAtributes = 10;
            Machine.collectData();
            Machine.classify();
        }

        if (classification){
            y = new double[1];
            y[0] = 1;
            e = new double[1];
            e[0] = 1;

        }


        //FOR ALL OF THE FILES

        //RANDOMIZE DATA SET
        Collections.shuffle(data);

        splitIndex = (data.size()) / 2;
        lastIndex = data.size();

        ArrayList<normalizedData> firstHalf = new ArrayList<>(data.subList(0, splitIndex));
        ArrayList<normalizedData> secondHalf = new ArrayList<>(data.subList(splitIndex, lastIndex));


        // NULL MODELS
        // Classification: Return the most common data value
        //HouseVotes.collectData();


        // Regression: Determine the average of each predictor class and return said average

        // K-Nearest Neighbor
        // Classification
        // Regression

        //---------------------------------
        //Edited condensed nearest neigbor
        //---------------------------------
        ArrayList<normalizedData> newData = new ArrayList<>(data);

        for(normalizedData point : newData){

        }



        //-------------------------------------------------------------------------------------------
        //K NEAREST NEIGHBOR WITH NEW STRUCTURE FULL OF NORMALIZED DATA
        // ------------------------------------------------------------------------------------------

        //loop through different epsilon values?
        ArrayList<normalizedData> firstHalfEdited = editedTrainingData(firstHalf, e[eIndex], classification);
        ArrayList<normalizedData> secondHalfEdited = editedTrainingData(secondHalf, e[eIndex], classification);

        ArrayList<normalizedData> trainingData;
        ArrayList<normalizedData> testData;
        for (int dataHalfsSwaper = 0; dataHalfsSwaper < 2; dataHalfsSwaper++) { //Swaping the test and training daat
            if (dataHalfsSwaper == 0) {
                trainingData = firstHalfEdited;
                testData = secondHalf;
                System.out.println("-----------FIRST CONFIG-----------------");
            } else {
                trainingData = secondHalfEdited;
                testData = firstHalf;
                System.out.println("-----------SECOND CONFIG----------------");
            }
            //loops throught different k values
            for (eIndex = 0; eIndex < e.length; eIndex++ ) {
                for (kIndex = 0; kIndex < k.length; kIndex++) { //try different k values
                    for (yIndex = 0; yIndex < y.length; yIndex++) { // try different y values


                        //regression variable
                        double squaredErrorSum = 0;

                        //clasification variable
                        int guessCorrect = 0;

                        //both
                        int pointsSurveyed = 0;

                        //LOOP EVERY POINT in testing set, compares it to the training set

                        for (int currentIndex = 0; currentIndex < testData.size(); currentIndex++) {
                            normalizedData[] nearestPoints = new normalizedData[k[kIndex]];
                            double[] nearestDistances = new double[k[kIndex]];
                            Arrays.fill(nearestDistances, Double.POSITIVE_INFINITY);

                            normalizedData measurePoint = testData.get(currentIndex);
                            normalizedData tempPoint;

                            for (int i = 0; i < trainingData.size(); i++) {
                                tempPoint = trainingData.get(i);
                                double distance = euclideanDistance(measurePoint.getFeatures(), tempPoint.getFeatures());


                                int largestIndex = 0;

                                for (int j = 1; j < k[kIndex]; j++) {
                                    if (nearestDistances[j] > nearestDistances[largestIndex]) {
                                        largestIndex = j;
                                    }
                                }
                                if (nearestDistances[largestIndex] > distance) {
                                    nearestPoints[largestIndex] = tempPoint;
                                    nearestDistances[largestIndex] = distance;
                                }

                            }
                            //System.out.println("Point picked: " + measurePoint);
                            //System.out.println("Nearests:");
                            for (int i = 0; i < k[kIndex]; i++) {
                                //System.out.println("Distance: " + nearestDistances[i] + ", Params: " + nearestPoints[i]);
                            }
                            double guess;
                            if (classification) {
                                guess = makeGuessClassification(nearestPoints);
                                //possibly convert guess back to its catagorical right here?
                                if (guess == testData.get(currentIndex).getTarget()) {
                                    guessCorrect++;
                                }
                            } else {
                                guess = makeGuessRegression(nearestDistances, nearestPoints);
                                //System.out.println("Our Guess: " + guess);
                                //System.out.println("Real Value: " + measurePoint.getTarget());
                                squaredErrorSum += Math.pow(measurePoint.getTarget() - guess, 2);
                            }
                            pointsSurveyed++;

                        }
                        System.out.println("E: " + e[eIndex] +", K: " + k[kIndex] + ", Y: " + y[yIndex]);
                        if (classification && (yIndex == 0)) {
                            System.out.println("Percent correct: " + (guessCorrect / pointsSurveyed));
                        } else {
                            System.out.println("Mean squared error: " + (squaredErrorSum / pointsSurveyed));
                        }
                        //System.out.println("Points surveyed: " + pointsSurveyed);


                    }
                }
            }
        }
        //---------------------------------------------------------
        // END K NEAREST
        //---------------------------------------------------------


  // Edited/Condensed K-Nearest Neighbor
    // Classification
    // Regression (including ∈ threshold)

  // Class Determinations
    // Classification (plurality vote)
    // Regression (Gaussian kernal)
    }








    private static double makeGuessRegression( double[] distances, normalizedData[] points) {
        double[] weights = new double[k[kIndex]];
        double[] weightedOutputs = new double[k[kIndex]];
        for(int i = 0; i<k[kIndex]; i++){
            weights[i] = Math.exp(-y[yIndex]*(distances[i]*distances[i]));
            weightedOutputs[i] = points[i].getTarget()*weights[i];
        }

        double totalWeights = 0;
        double totalWeightedOutputs = 0;
        for (int i = 0; i<k[kIndex]; i++){
            totalWeights += weights[i];
            totalWeightedOutputs += weightedOutputs[i];
        }
        double guess = totalWeightedOutputs/totalWeights;
        return guess;

    }

    private static double makeGuessClassification( normalizedData[] points) {
        //TO DO
        double[] outputs = new double[k[kIndex]];
        for (int i = 0; i<k[kIndex]; i++){
            outputs[i] = points[i].getTarget();
        }
        Map<Double, Integer> freqMap = new HashMap<>();
        int maxCount = 0;
        double mostCommonElement = outputs[0];
        for(int i = 0; i<k[kIndex]; i++){
            int count = freqMap.getOrDefault(outputs[i], 0) + 1;
            freqMap.put(outputs[i], count);

            if (count > maxCount){
                mostCommonElement = outputs[i];
            }
            if (count == maxCount){
                Random random = new Random();
                int randomNumber = random.nextInt(1, 3);
                if (randomNumber == 1){
                    mostCommonElement = outputs[i];
                }
            }
        }


               //Tally up the most common output, and return that value as a double
        return 0;
    }

    public static ArrayList<normalizedData> editedTrainingData(ArrayList<normalizedData> originalTrainingData, double e, boolean classification){
        ArrayList<normalizedData> newTrainingData = new ArrayList<>(originalTrainingData);
        boolean removedPoint = true;
        int passNumber = 0;
        while (removedPoint){
            removedPoint = false;
            passNumber++;

            ArrayList<normalizedData> pointsToRemove = new ArrayList<>();
            for (normalizedData pickedPoint : newTrainingData) {
                normalizedData nearestPoint = null;
                double nearestDistance = Double.POSITIVE_INFINITY;
                for (normalizedData possibleNeigbor : newTrainingData){
                    if (pickedPoint == possibleNeigbor){
                        continue;
                    }
                    double distance = euclideanDistance(pickedPoint.getFeatures(), possibleNeigbor.getFeatures());

                    if (distance < nearestDistance){
                        nearestDistance = distance;
                        nearestPoint = possibleNeigbor;
                    }
                }
                if (nearestPoint == null){
                    continue;
                }
                if (classification){
                    if (nearestPoint.getTarget() != pickedPoint.getTarget()){
                        pointsToRemove.add(pickedPoint);
                    }
                }
                else {
                    double prediction = nearestPoint.getTarget();
                    double actual = pickedPoint.getTarget();

                    double error = Math.abs(actual-prediction);
                    if (error > e){
                        pointsToRemove.add(pickedPoint);
                    }
                }

            }

            if (!pointsToRemove.isEmpty()){
                newTrainingData.removeAll(pointsToRemove);
                removedPoint = true;
            }
            System.out.println("Editing pass " + passNumber + ": removed " + pointsToRemove.size() + ", remaining " + newTrainingData.size());
            if (newTrainingData.size() <= 1) {
                break;
            }
        }

        return newTrainingData;
    }


    public static double euclideanDistance(double[] firstPoint, double[] secondPoint) {

        if (firstPoint.length != secondPoint.length) {
            throw new IllegalArgumentException(
                    "Points must have the same number of features."
            );
        }

        double squaredDistance = 0;

        for (int i = 0; i < firstPoint.length; i++) {
            double difference = firstPoint[i] - secondPoint[i];
            squaredDistance += difference * difference;
        }

        return Math.sqrt(squaredDistance);
    }
}