/*
    This class consists of the data point that we use inside the nodes of our tree
    it is like a "row" of the given data
    It also includes a function to clone to a different data point and
    a tostring method to print the datapoint

 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataPoint {
    public HashMap<String, String> columnNameToValueMap = new HashMap<String, String>();
    public List<String> columnNames = new ArrayList<String>();
    public String output;

    public DataPoint() {

    }

    public DataPoint(String[] columnNames, String[] columnValues) {
        for (String columnName : columnNames) {
            if (columnName.equals("class")) {
                continue;
            }

            this.columnNames.add(columnName);
        }

        for (int i = 0; i < this.columnNames.size(); i++) {
            String columnName = this.columnNames.get(i);
            String columnValue = columnValues[i];

            columnNameToValueMap.put(columnName, columnValue);
        }

        output = columnValues[columnValues.length - 1];
    }

    public DataPoint clone() {
        DataPoint dataPoint = new DataPoint();

        dataPoint.columnNameToValueMap = (HashMap<String, String>) this.columnNameToValueMap.clone();
        dataPoint.columnNames = new ArrayList<String>();
        dataPoint.columnNames.addAll(this.columnNames);
        dataPoint.output = this.output;

        return dataPoint;
    }

    public String toString() {
        String returnString = "";
        for (String s : columnNames) {
            returnString += s + "\t";
        }
        returnString += " class\n";
        for (String s : columnNames) {
            returnString += columnNameToValueMap.get(s) + "\t";
        }
        returnString += " " + output;

        return returnString;
    }
}
