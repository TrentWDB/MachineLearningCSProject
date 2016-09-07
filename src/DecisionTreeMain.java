import java.io.File;
import java.util.List;

public class DecisionTreeMain {
    public static void main(String[] args) {
        List<DataPoint> dataPoints = DataProcessor.processData(new File(args[0]));

        DecisionTreeNode parentNode = new DecisionTreeNode(dataPoints, null, null);
        parentNode.appendChildren();

        printTree(parentNode, 0);
    }

    private static void printTree(DecisionTreeNode node, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("| ");
        }

        System.out.print(node.columnName + " = " + node.columnValue + " : ");
        if (node.output != null) {
            System.out.print(node.output);
        }

        System.out.print("    " + node.entropy);

        System.out.println();

        for (DecisionTreeNode childNode : node.nodeList) {
            printTree(childNode, depth + 1);
        }
    }
}
