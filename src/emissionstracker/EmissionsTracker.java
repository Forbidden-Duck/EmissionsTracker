// Focus on the application interface
package emissionstracker;

import javax.swing.*;
import java.awt.*;

/**
 * @author Harrison Howard
 */
public class EmissionsTracker extends JFrame {

    private int totalX = 7;
    private int totalY = 12;

    private JTextField[][] fields = new JTextField[totalX][totalY];
    private JButton btnClear, btnSave, btnExit;

    private String[] headingsAtTheTop = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Weekly Average"};
    private String[] headingsOnTheSide = {"Emissions", "9:00am", "10:00am", "11:00am", "12:00pm", "1:00pm", "2:00pm", "3:00pm", "4:00pm", "5:00pm", "Total", "Average"};

    EventHandling event = new EventHandling();

    public static void main(String[] args) {
        EmissionsTracker emissionTrackingApplication = new EmissionsTracker();
        emissionTrackingApplication.run();
    }

    private void run() {
        setBounds(100, 50, 860, 400);
        setTitle("Emissions Tracker");

        displayGUI();
        // Call the method to input the data from the data file
        // Call the method to calculate the totals
        setResizable(false);
        setVisible(true);
    }

    // ------------------------------------------------------------------------------------------
    private void displayGUI() {
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        displayTextFields(springLayout);
        displayButtons(springLayout);
        setupTable(springLayout);
    }

    private void displayTextFields(SpringLayout layout) {
        for (int y = 0; y < totalY; y++) {
            for (int x = 0; x < totalX; x++) {
                int xPos = x * 120 + 20;
                int yPos = y * 25 + 20;
                fields[x][y] = LibraryComponents.LocateAJTextField(this, layout, 9, xPos, yPos);
                fields[x][y].addKeyListener(event);
            }
        }
    }

    private void displayButtons(SpringLayout layout) {
        btnClear = LibraryComponents.LocateAJButton(event, this, layout, "Clear", 20, 330, 80, 25);
        btnSave = LibraryComponents.LocateAJButton(event, this, layout, "Save", 692, 330, 80, 25);
        btnExit = LibraryComponents.LocateAJButton(event, this, layout, "Exit", 762, 330, 80, 25);

        // Add Listeners
        event.setFields(
                new JButton[]{btnClear, btnSave, btnExit},
                fields,
                new int[]{totalX - 1, totalY - 2});
        this.addWindowListener(event);
    }

    private void setupTable(SpringLayout layout) {
        for (int y = 0; y < totalY; y++) {
            fields[0][y].setText(headingsOnTheSide[y]);
            setFieldProperties(0, y, false, 220, 220, 255);
            setFieldProperties(6, y, false, 220, 255, 220);
        }
        for (int x = 0; x < totalX; x++) {
            fields[x][0].setText(headingsAtTheTop[x]);
            setFieldProperties(x, 0, false, 220, 220, 255);
            setFieldProperties(x, 10, false, 220, 255, 220);
            setFieldProperties(x, 11, false, 220, 255, 220);
        }
        setFieldProperties(6, 10, false, 254, 253, 205);
        setFieldProperties(6, 11, false, 254, 253, 205);
    }

    public void setFieldProperties(int x, int y, boolean editable, int r, int g, int b) {
        fields[x][y].setEditable(editable);
        fields[x][y].setBackground(new Color(r, g, b));
    }
}
