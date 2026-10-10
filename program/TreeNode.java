import java.util.HashMap;

public class TreeNode {
    private int featureIndex; // Has an index for the feature, Ie this is catagory 3
    private double prediction;
    private boolean isLeaf;

    private HashMap<Double, TreeNode> children; // and based on what you are for catagory 3, you go into a child node, Ie 1,2, or 3

    public TreeNode(int fi){
        featureIndex = fi;
        children = new HashMap<>();
    }
    public TreeNode(double p) {
        isLeaf = true;
        prediction = p;
    }




    public void addChild(double indicator, TreeNode childNode ){
        children.put(indicator, childNode);
    }
    public TreeNode getChild(double indicator){
        return children.get(indicator);
    }
}
