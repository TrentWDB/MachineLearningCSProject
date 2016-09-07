import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    public static List<DataPoint> processData(File file) {
        List<DataPoint> dataPoints = new ArrayList<DataPoint>();

        String fileString = "";
        try {
            fileString = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] fileLines = fileString.split("\r\n");
        String[] columnNames = fileLines[0].split("\\s+");

        for (int i = 1; i < fileLines.length; i++) {
            String[] columnValues = fileLines[i].split("\\s+");

            DataPoint dataPoint = new DataPoint(columnNames, columnValues);
            dataPoints.add(dataPoint);
        }

        return dataPoints;
    }
}
