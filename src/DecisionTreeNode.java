/*
    This is the class that contains the node structure that is used to create the decision tree
    It calculates the entropy to check which class to choose to move onto the next step and
    does this recursively until there are no more classes or there are only pure nodes

 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DecisionTreeNode {
    public String columnName;
    public String columnValue;
    public double entropy;
    private List<DataPoint> dataPoints = new ArrayList<DataPoint>();

    public List<DecisionTreeNode> nodeList = new ArrayList<DecisionTreeNode>();

    public String output;

    public DecisionTreeNode(List<DataPoint> dataPoints, String columnName, String columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
        entropy = calculateEntropy(dataPoints);

        // remove column that is used in this node
        if (columnName != null) {
            this.dataPoints = processDataPoints(dataPoints, columnName);
        } else {
            this.dataPoints = dataPoints;
        }
    }

    public void appendChildren() {
        if (entropy == 0) {
            // this node is perfectly pure so dont look at children
            output = dataPoints.get(0).output;
            return;
        }

        List<String> columnNames = dataPoints.get(0).columnNames;

        HashMap<String, Double> columnNameToConditionalEntropy = new HashMap<String, Double>();
        HashMap<String, List<DecisionTreeNode>> columnNameToNodeList = new HashMap<String, List<DecisionTreeNode>>();
        for (String columnName : columnNames) {
            if (!columnNameToNodeList.containsKey(columnName)) {
                columnNameToNodeList.put(columnName, new ArrayList<DecisionTreeNode>());
            }

            HashMap<String, List<DataPoint>> columnValueToDataPointList = new HashMap<String, List<DataPoint>>();

            for (DataPoint dataPoint : dataPoints) {
                String columnValue = dataPoint.columnNameToValueMap.get(columnName);

                if (!columnValueToDataPointList.containsKey(columnValue)) {
                    columnValueToDataPointList.put(columnValue, new ArrayList<DataPoint>());
                }

                columnValueToDataPointList.get(columnValue).add(dataPoint);
            }

            Set<String> columnValueSet = columnValueToDataPointList.keySet();
            for (String columnValue : columnValueSet) {
                DecisionTreeNode decisionTreeNode = new DecisionTreeNode(columnValueToDataPointList.get(columnValue), columnName, columnValue);
                columnNameToNodeList.get(columnName).add(decisionTreeNode);
            }

            columnNameToConditionalEntropy.put(columnName, calculateConditionalEntropy(columnNameToNodeList.get(columnName)));
        }

        double smallestEntropy = 1;
        String smallestEntropyColumnName = null;
        for (String columnName : columnNames) {
            double conditionalEntropy = columnNameToConditionalEntropy.get(columnName);

            if (conditionalEntropy < smallestEntropy) {
                smallestEntropy = conditionalEntropy;
                smallestEntropyColumnName = columnName;
            }
        }

        if (smallestEntropyColumnName == null) {
            HashMap<String, Integer> outputValueToCount = new HashMap<String, Integer>();

            for (DataPoint dataPoint : dataPoints) {
                if (!outputValueToCount.containsKey(dataPoint.output)) {
                    outputValueToCount.put(dataPoint.output, 0);
                }

                outputValueToCount.put(dataPoint.output, outputValueToCount.get(dataPoint.output) + 1);
            }

            int largestOutputValueCount = 0;
            String largestOutputValue = null;

            Set<String> outputValueSet = outputValueToCount.keySet();
            for (String outputValue : outputValueSet) {
                int outputValueCount = outputValueToCount.get(outputValue);
                if (outputValueCount > largestOutputValueCount) {
                    largestOutputValueCount = outputValueCount;
                    largestOutputValue = outputValue;
                }
            }

            output = largestOutputValue;
        } else {
            for (DecisionTreeNode node : columnNameToNodeList.get(smallestEntropyColumnName)) {
                nodeList.add(node);
                node.appendChildren();
            }
        }
    }

    private double calculateConditionalEntropy(List<DecisionTreeNode> nodeList) {
        double entropy = 0;
        int dataPointCountSummation = 0;
        for (DecisionTreeNode decisionTreeNode : nodeList) {
            dataPointCountSummation += decisionTreeNode.dataPoints.size();
            entropy += decisionTreeNode.entropy * decisionTreeNode.dataPoints.size();
        }

        return entropy / dataPointCountSummation;
    }

    private ArrayList<DataPoint> processDataPoints(List<DataPoint> dataPoints, String columnName) {
        ArrayList<DataPoint> newDataPoints = new ArrayList<DataPoint>();

        for (DataPoint dataPoint : dataPoints) {
            DataPoint newDataPoint = dataPoint.clone();
            newDataPoint.columnNameToValueMap.remove(columnName);
            newDataPoint.columnNames.remove(columnName);

            newDataPoints.add(newDataPoint);
        }

        return newDataPoints;
    }

    private double calculateEntropy(List<DataPoint> filteredDataPoints) {
        HashMap<String, Integer> classValueToCountMap = new HashMap<String, Integer>();

        for (int i = 0; i < filteredDataPoints.size(); i++) {
            DataPoint dataPoint = filteredDataPoints.get(i);

            String classValue = dataPoint.output;
            if (!classValueToCountMap.containsKey(classValue)) {
                classValueToCountMap.put(classValue, 0);
            }

            classValueToCountMap.put(classValue, classValueToCountMap.get(classValue) + 1);
        }

        Set<String> classValueSet = classValueToCountMap.keySet();
        double[] classProbabilities = new double[classValueSet.size()];

        int index = 0;
        for (String classValue : classValueSet) {
            int classValueCount = classValueToCountMap.get(classValue);

            classProbabilities[index] = (double) classValueCount / filteredDataPoints.size();

            index++;
        }

        return LearningFunctions.calculateEntropy(classProbabilities);
    }

    public void printDataSet() {
        for (DataPoint dataPoint : dataPoints) {
            System.out.print(dataPoint);
            System.out.println();
        }
    }
}
