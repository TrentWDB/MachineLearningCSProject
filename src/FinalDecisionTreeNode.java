/*
    This contains the structure for the nodes that will be placed in the decision tree

 */

import java.util.HashMap;
import java.util.Map;

public class FinalDecisionTreeNode {
    public String output = null;
    public String columnName;
    public Map<String, FinalDecisionTreeNode> columnValueToNodeMap = new HashMap<String, FinalDecisionTreeNode>();

    public FinalDecisionTreeNode() {

    }
}
