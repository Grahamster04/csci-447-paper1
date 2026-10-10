import com.sun.source.tree.Tree;

import javax.print.DocFlavor;
import java.sql.SQLOutput;
import java.util.*;

public class Main{
    // CHANGABLE ATRIBUTES Depending
    //-------------------------------------
    public static final String FILE_NAME = "data/house-votes-84.data";

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

            if (FILE_NAME.equals(abalone)) {
                System.out.println("FILE ABALONE");
                classification = false;
                numAtributes = 9;
                //isNumericAtribute = new boolean[]{false, true, true, true, true, true, true, true, true};
                Abalone.collectData();
                for (int i = 0; i < Abalone.data.size(); i++) {
                    Abalone point = Abalone.data.get(i);
                    data.add(new normalizedData(point.getFeatures(), point.getLable()));
                    //System.out.println("Added:" + point + ", new:" + data.getLast());
                }
                Abalone.classify();


            }
            if (FILE_NAME.equals(breastCancer)) {
                System.out.println("FILE BREAST CANCER");
                classification = true;
                numAtributes = 11;
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
                Car.collectData();

                for (int i = 0; i < Car.data.size(); i++) {
                    Car point = Car.data.get(i);
                    data.add(new normalizedData(point.getFeatures(), point.getLable()));
                    //System.out.println("Added:" + point + ", new:" + data.getLast());
                }
                Car.classify();
            }
            if (FILE_NAME.equals(fires)) {
                System.out.println("FILE FOREST FIRES");
                classification = false;
                numAtributes = 13;
                ForestFires.collectData();

                for (int i = 0; i < ForestFires.data.size(); i++) {
                    ForestFires point = ForestFires.data.get(i);
                    data.add(new normalizedData(point.getFeatures(), point.getLable()));
                    //System.out.println("Added:" + point + ", new:" + data.getLast());
                }
                ForestFires.classify();
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
                numAtributes = 10;
                Machine.collectData();

                for (int i = 0; i < Machine.data.size(); i++) {
                    Machine point = Machine.data.get(i);
                    data.add(new normalizedData(point.getFeatures(), point.getLable()));
                    //System.out.println("Added:" + point + ", new:" + data.getLast());
                }
                Machine.classify();
            }



            //FOR ALL OF THE FILES

            //RANDOMIZE DATA SET
        Collections.shuffle(data);

        splitIndex = (data.size()) / 2;
        lastIndex = data.size();

        ArrayList<normalizedData> firstHalf = new ArrayList<>(data.subList(0, splitIndex));
        ArrayList<normalizedData> secondHalf = new ArrayList<>(data.subList(splitIndex, lastIndex));

        ArrayList<normalizedData> trainingData = firstHalf;
        ArrayList<normalizedData> testData = secondHalf;




        ArrayList<Integer> availableFeatures = new ArrayList<>();
        for (int i = 0; i < numAtributes; i++)
            availableFeatures.add(i);
        TreeNode root = buildTree(trainingData, availableFeatures);
        printTree(root, "--");

        double percentPredictionsCorrect = testAcurracy(testData, root)*100;
        System.out.println("Accuracy: " + percentPredictionsCorrect + "%");
        /*
        ArrayList<normalizedData> mytestdata = new ArrayList<>();
        mytestdata.add(new normalizedData(null, 1));
        mytestdata.add(new normalizedData(null, 2));
        mytestdata.add(new normalizedData(null, 2));
        mytestdata.add(new normalizedData(null, 3));
        mytestdata.add(new normalizedData(null, 1));
        double b = majorityClass(mytestdata);
        System.out.println("Majority? " + b);
         */





    }

    private static double testAcurracy(ArrayList<normalizedData> inputData, TreeNode root) {
        int correct = 0;

        for(normalizedData point : inputData){

            double prediction = predict(point, root);
            if(prediction == point.getTarget()){
                correct++;
            }
        }

        return (double)correct / inputData.size();
    }

    private static double predict(normalizedData point, TreeNode node) {
        while(!node.isLeaf()) {
            int featureIndex = node.getFeatureIndex();
            double value = point.getFeatures()[featureIndex];
            TreeNode child = node.getChildren().get(value);
            if(child == null) {
                return node.getPrediction();
            }
            node = child;
        }
        return node.getPrediction();
    }


    private static TreeNode buildTree(ArrayList<normalizedData> inputData, ArrayList<Integer> availableFeatures){
        // Base Case 1
        if(allSameClass(inputData))
            return new TreeNode(inputData.getFirst().getTarget()); //leaf
        // Base Case 2
        double majorityClass = majorityClass(inputData);
        if(availableFeatures.isEmpty())
            return new TreeNode(majorityClass);

        int bestFeature = findBestFeature(inputData, availableFeatures);

        TreeNode node = new TreeNode(bestFeature, majorityClass);

        HashMap<Double, ArrayList<normalizedData>> groups = splitData(bestFeature, inputData);

        ArrayList<Integer> nextFeatures = new ArrayList<>(availableFeatures);
        nextFeatures.remove(Integer.valueOf(bestFeature));

        for(Double value : groups.keySet()) {
            TreeNode child = buildTree(groups.get(value), nextFeatures);
            node.addChild(value, child);
        }

        return node;
    }




    private static boolean allSameClass(ArrayList<normalizedData> inputData) {
        double target = inputData.getFirst().getTarget();
        for (normalizedData point : inputData){
            if (point.getTarget() != target)
                return false;
        }
        return true;
    }

    private static double majorityClass(ArrayList<normalizedData> data) {
        HashMap<Double, Integer> votes = new HashMap<>();
        for (normalizedData point : data){
            double label = point.getTarget();
            votes.put(label, votes.getOrDefault(label, 0) + 1);
        }
        double winner = -1;
        int maxVotes = -1;

        for (Double label : votes.keySet()) {

            int count = votes.get(label);

            if (count > maxVotes) {
                maxVotes = count;
                winner = label;
            }
        }

        return winner;

    }

    private static int findBestFeature(ArrayList<normalizedData> inputData, ArrayList<Integer> availableFeatures) {
        int bestFeature = -1;
        double bestGain = -1;
        for (int feature : availableFeatures){
            double gain = calculateInfoGain(feature, inputData);
            if (gain>bestGain) {
                bestGain = gain;
                bestFeature = feature;
            }
        }
        return bestFeature;
    }

    private static double calculateInfoGain(int row, ArrayList<normalizedData> inputData){
            double parentEntropy = calculateEntropy(inputData);

            HashMap<Double, ArrayList<normalizedData>> groups = splitData(row, inputData);

            double childEntropy = 0;
            for (ArrayList<normalizedData> subset : groups.values()){
                double weight = (double) subset.size() / data.size();
                childEntropy += weight * calculateEntropy(subset);
            }
        return parentEntropy-childEntropy;
    }

    private static double calculateEntropy(ArrayList<normalizedData> inputdata){
        HashMap<Double, Integer> counts = new HashMap<>();
        for (normalizedData point : inputdata) {
            double label = point.getTarget();
            counts.put(label, counts.getOrDefault(label, 0) + 1);
        }

        double entropy = 0;

        for (int count : counts.values()) {
            double p = (double) count / inputdata.size();
            entropy -= p * (Math.log(p) / Math.log(2));
        }

        return entropy;
    }

    private static HashMap<Double, ArrayList<normalizedData>> splitData(int featureIndex, ArrayList<normalizedData> inputData){
        HashMap<Double,ArrayList<normalizedData>> groups = new HashMap<>();

        for(normalizedData point : inputData) {
            double value = point.getFeatures()[featureIndex];
            groups.putIfAbsent(value, new ArrayList<>());
            groups.get(value).add(point);
        }

        return groups;
    }

    private static double calculateIntrinsicValue(int featureIndex, ArrayList<normalizedData> data){
        HashMap<Double, ArrayList<normalizedData>> groups = splitData(featureIndex, data);

        double iv = 0;

        for(ArrayList<normalizedData> subset : groups.values()) {
            double p = (double) subset.size() / data.size();
            iv -= p * (Math.log(p) / Math.log(2));
        }

        return iv;
    }
    private static double calculateGainRatio(int featureIndex, ArrayList<normalizedData> data)
    {
        double gain = calculateInfoGain(featureIndex, data);
        double iv = calculateIntrinsicValue(featureIndex, data);
        return gain / iv;
    }

    private static void printTree(TreeNode node, String indent) {

        if(node.isLeaf()) {
            System.out.println(indent + "Predict: " + node.getPrediction());
            return;
        }

        System.out.println(indent + "Feature " + node.getFeatureIndex());

        for(Double value : node.getChildren().keySet()) {
            System.out.println(indent + " ├─ Value " + value);
            printTree(node.getChildren().get(value), indent + " │   ");
        }
    }



}