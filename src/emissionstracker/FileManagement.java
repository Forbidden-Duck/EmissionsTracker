package emissionstracker;

// Import the applicable Java IO libraries
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
// Swing Imports
import javax.swing.*;

public class FileManagement {

    private String dataFileName = "EmissionsTracker.csv";
    private String tableFileName = "EmissionsTable.csv";
    private String[] headingsAtTheTop = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Weekly Average"};
    private String[] headingsOnTheSide = {"Emissions", "9:00am", "10:00am", "11:00am", "12:00pm", "1:00pm", "2:00pm", "3:00pm", "4:00pm", "5:00pm", "Total", "Average"};

    int numberOfEntries = 0;

    private int totalX;
    private int totalY;

    private JTextField[][] fields;

    public FileManagement(JTextField[][] txtArray, int[] fieldsAxis) {
        fields = txtArray;
        totalX = fieldsAxis[0];
        totalY = fieldsAxis[1];
    }

    public void readDataFile() {
        getNumberOfEntries();
        try {
            BufferedReader br = new BufferedReader(new FileReader(dataFileName));

            for (int x = 1; x < totalX; x++) {
                for (int y = 1; y < totalY; y++) {
                    if (y <= numberOfEntries) {
                        String temp[] = br.readLine().split(",");
                        if (temp.length > 2) {
                            fields[x][y].setText(temp[2]);
                        }
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error Reading:");
            e.printStackTrace();
        }
    }

    public void writeDataFile() {
        try {
            BufferedWriter outFile = new BufferedWriter(new FileWriter(dataFileName));

            for (int x = 1; x < totalX; x++) {
                for (int y = 1; y < totalY; y++) {
                    outFile.write(fields[0][y].getText() + "," + fields[x][0].getText() + "," + fields[x][y].getText());
                    outFile.newLine();
                }
            }
            outFile.close();
        } catch (Exception e) {
            System.err.println("Error Writing:");
            e.printStackTrace();
        }
    }
    
    public void writeTableFile() {
        try {
            BufferedWriter outFile = new BufferedWriter(new FileWriter(tableFileName));
            
            for (int y = 0; y < totalY; y++) {
                for(int x = 0; x < totalX - 1; x++) {
                    outFile.write(fields[x][y].getText() + ",");
                }
                outFile.write(fields[6][y].getText() + "");
                outFile.newLine();
            }
            outFile.close();
        } catch (Exception e) {
            System.err.println("Error Writing Table:");
            e.printStackTrace();
        }
    }

    private void getNumberOfEntries() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(dataFileName));

            while (br.readLine() != null) {
                numberOfEntries++;
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error Writing:");
            e.printStackTrace();
        }
    }

    public JTextField[][] getFields() {
        return fields;
    }

    public void calculateTotals() {
        int total = 0;

        for (int y = 1; y < totalY; y++) {
            for (int x = 1; x < totalX; x++) {
                total = total + Integer.parseInt(checkInteger(fields[x][y].getText()));
            }
            fields[6][y].setText("" + String.format("%.2f", ((double) total / ((double) totalX - 2))));
            total = 0;
        }

        for (int x = 1; x < totalX; x++) {
            for (int y = 1; y < totalY; y++) {
                total = total + Integer.parseInt(checkInteger(fields[x][y].getText()));
            }
            fields[x][10].setText("" + total);
            fields[x][11].setText("" + String.format("%.2f", ((double) total / ((double) totalY - 3))));
            total = 0;
        }

        for (int x = 1; x < totalX; x++) {
            total = total + Integer.parseInt(checkInteger(fields[x][10].getText()));
        }
        fields[6][10].setText("" + total);
        fields[6][11].setText("" + String.format("%.2f", ((double) total / (((double) totalY - 3) * ((double) totalX - 2)))));
        total = 0;
    }

    private String checkInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return strValue;
        } catch (Exception e) {
            return "0";
        }
    }
}
