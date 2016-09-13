import java.util.HashMap;
import java.util.Map;

/**
 * Created by Trent on 9/13/2016.
 */
public class FinalDecisionTreeNode {
    public String output = null;
    public String columnName;
    public Map<String, FinalDecisionTreeNode> columnValueToNodeMap = new HashMap<String, FinalDecisionTreeNode>();

    public FinalDecisionTreeNode() {

    }
}
