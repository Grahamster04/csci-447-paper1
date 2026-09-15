public class Main{
    public static final String FILE_NAME = "data/car.data";

    public static final String abalone = "data/abalone.data";
    public static final String breastCancer = "data/breast-cancer-wisconsin.data";
    public static final String car = "data/car.data";
    public static final String fires = "data/forestfires.data";
    public static final String houseVotes = "data/house-votes-84.data";
    public static final String machine = "data/machine.data";

    public static boolean classification;
    public static int numAtributes;


    public static void main(String[] args) {
        //READ FILE FROM FILE NAME
        if (FILE_NAME.equals(abalone)){
            System.out.println("FILE ABALONE");
            classification = false;
            numAtributes = 9;
            // Abalone.collectData();

        }
        if (FILE_NAME.equals(breastCancer)){
            System.out.println("FILE BREAST CANCER");
            classification = true;
            numAtributes = 11;
            BreastCancer.collectData();
            BreastCancer.classify();
        }
        if (FILE_NAME.equals(car)){
            System.out.println("FILE CAR");
            classification = true;
            numAtributes = 6;
            Car.collectData();
            Car.classify();
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
            HouseVotes.collectData();
            HouseVotes.classify();
        }
        if (FILE_NAME == machine){
            System.out.println("FILE MACHINE");
            classification = false;
            numAtributes = 10;
        }

        // NULL MODELS (Implemented in if statements above. Change if you want)
        // Classification: Return the most common data value
        // Regression: Determine the average of each predictor class and return said average

        // K-Nearest Neighbor
        // Classification
        // Regression

        // Edited/Condensed K-Nearest Neighbor
        // Classification
        // Regression (including ∈ threshold)

        // Class Determinations
        // Classification (plurality vote)
        // Regression (Gaussian kernal)
    }
}
