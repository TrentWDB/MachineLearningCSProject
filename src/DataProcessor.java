/*
    this class is used to process the decision tree that is stored in a file that was created in decision tree main
    it reads in a file name and creates a decision tree from the file
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    private static int decisionTreeProcessingLineIndex = 0;

    public static List<DataPoint> processData(File file) {
        List<DataPoint> dataPoints = new ArrayList<DataPoint>();

        String fileString = "";
        try {
            fileString = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] fileLines = fileString.split("\\r?\\n");
        String[] columnNames = fileLines[0].split("\\s+");

        for (int i = 1; i < fileLines.length / 3; i++) {
            String[] columnValues = fileLines[i].split("\\s+");

            DataPoint dataPoint = new DataPoint(columnNames, columnValues);
            dataPoints.add(dataPoint);
        }

        return dataPoints;
    }

    public static FinalDecisionTreeNode constructDecisionTree(File file) {
        decisionTreeProcessingLineIndex = 0;

        String treeString = "";
        try {
            treeString = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] treeFileLines = treeString.split("\\r?\\n");

        FinalDecisionTreeNode rootNode = new FinalDecisionTreeNode();

        constructDecisionTree(rootNode, treeFileLines, -1);

        return rootNode;
    }

    private static void constructDecisionTree(FinalDecisionTreeNode node, String[] lines, int depth) {
        while (decisionTreeProcessingLineIndex < lines.length) {
            String line = lines[decisionTreeProcessingLineIndex];
            String[] lineArray = line.split("\\|\\s");

            int lineDepth = lineArray.length - 1;

            String importantPart = lineArray[lineArray.length - 1];
            String[] importantPartParts = importantPart.split("\\s:\\s");
            String[] columnNameAndValue = importantPartParts[0].split("\\s=\\s");

            String columnName = columnNameAndValue[0];
            String columnValue = columnNameAndValue[1];

            String output = "";
            if (importantPartParts.length > 1) {
                output = importantPartParts[1];
            }

            if (lineDepth == depth + 1) {
                decisionTreeProcessingLineIndex++;
                // if its a child of the current node
                FinalDecisionTreeNode newNode = new FinalDecisionTreeNode();
                if (output.length() > 0) {
                    newNode.output = output;
                }

                node.columnName = columnName;
                node.columnValueToNodeMap.put(columnValue, newNode);
                constructDecisionTree(newNode, lines, depth + 1);
            } else {
                return;
            }
        }
    }
}
