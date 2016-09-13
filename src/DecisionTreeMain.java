import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class DecisionTreeMain {
    private static PrintWriter printWriter;

    public static void main(String[] args) {
        List<DataPoint> dataPoints = DataProcessor.processData(new File(args[0]));

        DecisionTreeNode parentNode = new DecisionTreeNode(dataPoints, null, null);
        parentNode.appendChildren();

        File file = new File("tree.dat");
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        printTree(parentNode, 0);

        printWriter.flush();
        printWriter.close();
    }

    private static void printTree(DecisionTreeNode node, int depth) {
        if (depth > 0) {
            for (int i = 0; i < depth - 1; i++) {
                printWriter.print("| ");
            }

            printWriter.print(node.columnName + " = " + node.columnValue + " : ");
            if (node.output != null) {
                printWriter.print(node.output);
            }

            // printWriter.print("    " + node.entropy);

            printWriter.println();
        }

        for (DecisionTreeNode childNode : node.nodeList) {
            printTree(childNode, depth + 1);
        }
    }
}
