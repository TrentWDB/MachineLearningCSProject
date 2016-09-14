import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DecisionTreeProcessor {
    private static int count = 0;
    private static int correctCount = 0;
    public static void main(String[] args) {
        File treeFile = new File("tree.dat");
        File trainingFile = new File(args[0]);
        File testFile = new File(args[1]);

        FinalDecisionTreeNode decisionTree = DataProcessor.constructDecisionTree(treeFile);

        String treeString = "";
        try {
            treeString = new String(Files.readAllBytes(Paths.get(treeFile.getAbsolutePath())), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] treeLines = treeString.split("\\r?\\n");
        for (String treeLine : treeLines) {
            System.out.println(treeLine);
        }

        System.out.println();
        testFile(decisionTree, trainingFile);
        System.out.println();
        testFile(decisionTree, testFile);
    }

    private static void testFile(FinalDecisionTreeNode decisionTree, File file) {
        correctCount = 0;
        count = 0;

        String testString = "";
        try {
            testString = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] testFileLines = testString.split("\\r?\\n");

        String[] columnNames = testFileLines[0].split("\\s");

        for (int i = 1; i < testFileLines.length; i++) {
            String[] columnValues = testFileLines[i].split("\\s");
            String actualColumnClass = columnValues[columnValues.length - 1];

            TestLine testLine = new TestLine(columnNames, columnValues);

            String classValue = testLine.determineClass(decisionTree);

            if (classValue.equals(actualColumnClass)) {
                correctCount++;
            }
            count++;
        }

        System.out.println("Accuracy on " + file.getName() + " set (" + (testFileLines.length - 1) + " instances): " + (Math.round((double) correctCount / count * 1000) / 10.0) + "%");
    }
}

class TestLine {
    public String[] columnNames;
    public String[] columnValues;

    public TestLine(String[] columnNames, String[] columnValues) {
        this.columnNames = columnNames;
        this.columnValues = columnValues;
    }

    public String determineClass(FinalDecisionTreeNode node) {
        if (node == null) {
            return "";
        }

        if (node.output != null) {
            return node.output;
        }

        int index = 0;
        for (int i = 0; i < columnNames.length; i++) {
            String columnName = columnNames[i];

            if (columnName.equals(node.columnName)) {
                index = i;
                break;
            }
        }

        return determineClass(node.columnValueToNodeMap.get(columnValues[index]));
    }
}
