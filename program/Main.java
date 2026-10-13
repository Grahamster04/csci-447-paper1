import java.util.*;

public class Main{
    // CHANGABLE ATRIBUTES Depending
    //-------------------------------------
    public static final String FILE_NAME = "data/car.data";

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
    public static int pruningIndex;


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
        pruningIndex = 9*(data.size()) / 10;

        ArrayList<normalizedData> testData =  new ArrayList<>(data.subList(0, splitIndex));
        ArrayList<normalizedData> trainingData = new ArrayList<>(data.subList(splitIndex, pruningIndex));
        ArrayList<normalizedData> pruningData = new ArrayList<>(data.subList(pruningIndex, lastIndex));







        ArrayList<Integer> availableFeatures = new ArrayList<>();
        for (int i = 0; i < numAtributes; i++)
            availableFeatures.add(i);


        TreeNode root = buildTree(trainingData, availableFeatures);

        //printTree(root, "--");


        double percentPredictionsCorrect = testAcurracy(testData, root);
        int nodeCount = countNodes(root);
        System.out.println("Accuracy: " + percentPredictionsCorrect*100 + "%, Nodes: " + nodeCount);

        pruneTree(root, pruningData);

        percentPredictionsCorrect = testAcurracy(testData, root);
        nodeCount = countNodes(root);
        System.out.println("Pruned:");
        System.out.println("Accuracy: " + percentPredictionsCorrect*100 + "%, Nodes: " + nodeCount);


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


    private static TreeNode buildTree(ArrayList<normalizedData> inputData, ArrayList<Integer> availableFeatures){
        // Base Case 1
        if(allSameClass(inputData))
            return new TreeNode(inputData.getFirst().getTarget()); //leaf
        // Base Case 2
        double majorityClass = majorityClass(inputData);
        if(availableFeatures.isEmpty())
            return new TreeNode(majorityClass);

        SplitResult bestFeature = findBestFeature(inputData, availableFeatures);




        TreeNode node = new TreeNode(bestFeature.featureIndex, majorityClass, bestFeature.threshold, bestFeature.numeric);


        HashMap<Double, ArrayList<normalizedData>> groups = new HashMap<>();

        if (bestFeature.numeric){
            groups = splitDataNumeric(bestFeature.featureIndex,bestFeature.threshold, inputData);
        }
        else {
            groups = splitData(bestFeature.featureIndex, inputData);
        }

        ArrayList<Integer> nextFeatures = new ArrayList<>(availableFeatures);
        if(!bestFeature.numeric)
            nextFeatures.remove(Integer.valueOf(bestFeature.featureIndex));

        for(Double value : groups.keySet()) {
            if(groups.get(value).isEmpty())
                continue;
            TreeNode child = buildTree(groups.get(value), nextFeatures);
            node.addChild(value, child);
        }

        return node;
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

            if(node.isNumeric()) {
                if(value <= node.getSplitThreshold()){
                    //System.out.println("Going LEFT");
                    node = node.getChild(0.0);
                }
                else{
                    node = node.getChild(1.0);
                    //System.out.println("Going LEFT");
                }

            }
            else {
                TreeNode child = node.getChildren().get(value);
                if (child == null) {
                    return node.getPrediction();
                }
                node = child;
            }
        }
        return node.getPrediction();
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

    private static SplitResult findBestFeature(ArrayList<normalizedData> inputData, ArrayList<Integer> availableFeatures) {
        SplitResult bestFeature = new SplitResult();
        double bestGainRatio = -1;


        for (int feature : availableFeatures){
            if (normalizedData.getIsFeatureNumeric(feature)){
                double bestGainForThresholds = -1;
                double bestThreshold = -1;

                ArrayList<Double> thresholds = getCandidateThresholds(feature, inputData);
                for(double threshold : thresholds) {
                    double gainRatio = calculateGainRatioNumeric(feature, threshold, inputData);
                    if (gainRatio>bestGainForThresholds){
                        bestGainForThresholds = gainRatio;
                        bestThreshold = threshold;
                    }
                }
                if (bestGainForThresholds> bestGainRatio){
                    bestGainRatio = bestGainForThresholds;
                    bestFeature.featureIndex = feature;
                    bestFeature.gainRatio = bestGainForThresholds;
                    bestFeature.threshold = bestThreshold;
                    bestFeature.numeric = true;
                }
            }
            else {
                double gainRatio = calculateGainRatio(feature, inputData);
                if (gainRatio > bestGainRatio) {
                    bestGainRatio = gainRatio;
                    bestFeature.featureIndex = feature;
                    bestFeature.gainRatio = gainRatio;
                    bestFeature.numeric = false;
                    bestFeature.threshold = -1;
                }
            }
        }

        return bestFeature;
    }



    private static double calculateInfoGain(int row, ArrayList<normalizedData> inputData){
            double parentEntropy = calculateEntropy(inputData);

            HashMap<Double, ArrayList<normalizedData>> groups = splitData(row, inputData);

            double childEntropy = 0;
            for (ArrayList<normalizedData> subset : groups.values()){
                double weight = (double) subset.size() / inputData.size();
                childEntropy += weight * calculateEntropy(subset);
            }
        return parentEntropy-childEntropy;
    }
    private static double calculateInfoGainNumeric(int row, double threshold, ArrayList<normalizedData> inputData){
        double parentEntropy = calculateEntropy(inputData);

        HashMap<Double, ArrayList<normalizedData>> groups = splitDataNumeric(row, threshold, inputData);

        double childEntropy = 0;
        for (ArrayList<normalizedData> subset : groups.values()){
            double weight = (double) subset.size() / inputData.size();
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
    private static HashMap<Double, ArrayList<normalizedData>> splitDataNumeric(int featureIndex, double threshold, ArrayList<normalizedData> inputData){
        HashMap<Double,ArrayList<normalizedData>> groups = new HashMap<>();

        for(normalizedData point : inputData) {
            double value = point.getFeatures()[featureIndex];
            if (value <= threshold) {
                groups.putIfAbsent(0.0, new ArrayList<>());
                groups.get(0.0).add(point);
            }
            else{
                groups.putIfAbsent(1.0, new ArrayList<>());
                groups.get(1.0).add(point);
            }
        }
        //System.out.println("Feature " + featureIndex +" Threshold " + threshold +" Left=" + groups.get(0.0).size() +" Right=" + groups.get(1.0).size());

        return groups;
    }

    private static double calculateIntrinsicValue(int featureIndex, ArrayList<normalizedData> inputData){
        HashMap<Double, ArrayList<normalizedData>> groups = splitData(featureIndex, inputData);

        double iv = 0;

        for(ArrayList<normalizedData> subset : groups.values()) {
            double p = (double) subset.size() / inputData.size();
            iv -= p * (Math.log(p) / Math.log(2));
        }

        return iv;
    }
    private static double calculateIntrinsicValueNumeric(int featureIndex, double threshold, ArrayList<normalizedData> inputData){
        HashMap<Double, ArrayList<normalizedData>> groups = splitDataNumeric(featureIndex,threshold, inputData);

        double iv = 0;

        for(ArrayList<normalizedData> subset : groups.values()) {
            double p = (double) subset.size() / inputData.size();
            iv -= p * (Math.log(p) / Math.log(2));
        }

        return iv;
    }

    private static double calculateGainRatio(int featureIndex, ArrayList<normalizedData> inputData) {
        double gain = calculateInfoGain(featureIndex, inputData);
        double iv = calculateIntrinsicValue(featureIndex, inputData);
        return gain / iv;
    }
    private static double calculateGainRatioNumeric(int featureIndex, double threshold, ArrayList<normalizedData> inputData) {
        double gain = calculateInfoGainNumeric(featureIndex, threshold, inputData);
        double iv = calculateIntrinsicValueNumeric(featureIndex, threshold, inputData);

        return gain / iv;
    }

    private static ArrayList<Double> getCandidateThresholds(int featureIndex, ArrayList<normalizedData> inputData) {
        if(inputData.isEmpty())
            return new ArrayList<>();

        ArrayList<Double> values = new ArrayList<>();

        for(normalizedData point : inputData) {
            values.add(point.getFeatures()[featureIndex]);
        }

        Collections.sort(values);
        ArrayList<Double> thresholds = new ArrayList<>();

        for(int i = 0; i < values.size() - 1; i++) {
            double current = values.get(i);
            double next = values.get(i + 1);
            if(current != next) {
                double midpoint = (current + next) / 2.0;
                thresholds.add(midpoint);
            }
        }

        return thresholds;
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

    private static void pruneTree(TreeNode root, ArrayList<normalizedData> pruningData) {
        pruneNode(root, pruningData, root);
    }

    private static void pruneNode(TreeNode node, ArrayList<normalizedData> pruningData, TreeNode root) {
        if(node.isLeaf())
            return;

        // recurse into children first
        // Go downward first
        for(TreeNode child :
                node.getChildren().values())
        {
            pruneNode(child,
                    pruningData,
                    root);
        }
        // try pruning this node

        //old stuff saved
        double originalAccuracy = testAcurracy(pruningData, root);
        HashMap<Double, TreeNode> oldChildren = new HashMap<>(node.getChildren());
        boolean oldIsLeaf = node.isLeaf();

        //prune node
        node.setLeaf(true);
        node.clearChildren();

        //find acurracy
        double prunedAcurracy = testAcurracy(pruningData, root);


        if(prunedAcurracy < originalAccuracy) {

            node.setLeaf(oldIsLeaf);
            node.setChildren(oldChildren);
        }



    }

    private static int countNodes(TreeNode root){
        if(root.isLeaf())
            return 1;

        int nodes = 1;

        for(TreeNode child : root.getChildren().values()) {
            nodes += countNodes(child);
        }

        return nodes;
    }
}