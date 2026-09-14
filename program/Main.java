import java.util.ArrayList;

public class Main{
    public static final String FILE_NAME = "data/abalone.data";

    public static final String abalone = "data/abalone.data";
    public static final String breastCancer = "data/breast-cancer-wisconsin.data";
    public static final String car = "data/car.data";
    public static final String fires = "data/forestfires.data";
    public static final String houseVotes = "data/house-votes-84.data";
    public static final String machine = "data/machine.data";

    public static boolean classification;
    public static int numAtributes;

    public static ArrayList<normalizedData> data = new ArrayList<>();

    public static void main(String[] args) {
        //READ FILE FROM FILE NAME
        if (FILE_NAME == abalone){
            System.out.println("FILE ABALONE");
            classification = false;
            numAtributes = 9;
            Abalone.collectData();
            for (int i = 0; i < Abalone.data.size() -1; i++){
                Abalone point = Abalone.data.get(i);
                data.add(new normalizedData(point.getFeatures(), point.getLable()));
                System.out.println("Added:" + point + ", new:" + data.getLast());
            }

        }
        if (FILE_NAME == breastCancer){
            System.out.println("FILE BREST CANCER");
            classification = true;
            numAtributes = 11;
        }
        if (FILE_NAME == car){
            System.out.println("FILE CAR");
            classification = true;
            numAtributes = 6;
        }
        if (FILE_NAME == fires){
            System.out.println("FILE FOREST FIRES");
            classification = false;
            numAtributes = 13;
        }
        if (FILE_NAME == houseVotes){
            System.out.println("FILE HOUSE VOTES");
            classification = true;
            numAtributes = 17;
        }
        if (FILE_NAME == machine){
            System.out.println("FILE MACHINE");
            classification = false;
            numAtributes = 10;
        }

  // NULL MODELS
    // Classification: Return the most common data value
    //HouseVotes.collectData();


    // Regression: Determine the average of each predictor class and return said average

  // K-Nearest Neighbor
    // Classification
    // Regression

        //K NEAREST NEIGHBOR WIHT NEW STRUCTURE FULL OF NORMALIZED DATA
        if(classification = false){


        }
  // Edited/Condensed K-Nearest Neighbor
    // Classification
    // Regression (including ∈ threshold)

  // Class Determinations
    // Classification (plurality vote)
    // Regression (Gaussian kernal)
    }

    public static double euclideanDistance(double[] firstPoint, double[] secondPoint) {

        if (firstPoint.length != secondPoint.length) {
            throw new IllegalArgumentException(
                    "Points must have the same number of features."
            );
        }

        double squaredDistance = 0.0;

        for (int i = 0; i < firstPoint.length; i++) {
            double difference = firstPoint[i] - secondPoint[i];
            squaredDistance += difference * difference;
        }

        return Math.sqrt(squaredDistance);
    }
}