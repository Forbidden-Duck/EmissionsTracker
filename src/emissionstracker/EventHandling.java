package emissionstracker;

// ActionListener Imports 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// WindowListener Imports
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
// KeyListener Imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// Swing Imports
import javax.swing.*;

public class EventHandling implements WindowListener, ActionListener, KeyListener {

    private int totalX;
    private int totalY;

    private JButton btnClear, btnSave, btnExit;
    private JTextField[][] fields;

    public void setFields(JButton[] btnArray, JTextField[][] txtArray, int[] fieldAxis) {
        btnClear = btnArray[0];
        btnSave = btnArray[1];
        btnExit = btnArray[2];

        fields = txtArray;

        totalX = fieldAxis[0];
        totalY = fieldAxis[1];
    }

    // Manage Action Events
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClear) {
            for (int y = 1; y < totalY; y++) {
                for (int x = 1; x < totalX; x++) {
                    fields[x][y].setText(" ");
                    fields[x][y].setText("");
                }
            }
        }
        if (e.getSource() == btnSave) {
            FileManagement filemng = new FileManagement(fields, new int[]{totalX, totalY});
            filemng.writeDataFile();
            filemng.writeTableFile();
        }
        if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    // Manage Key Events
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        FileManagement filemng = new FileManagement(fields, new int[]{totalX, totalY});
        filemng.calculateTotals();
    }

    // Manage Window Events
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }

    public void windowIconified(WindowEvent we) {
    }

    public void windowOpened(WindowEvent we) {
        FileManagement filemng = new FileManagement(fields, new int[]{totalX, totalY});
        filemng.readDataFile();
        filemng.calculateTotals();
    }

    public void windowClosed(WindowEvent we) {
    }

    public void windowDeiconified(WindowEvent we) {
    }

    public void windowActivated(WindowEvent we) {
    }

    public void windowDeactivated(WindowEvent we) {
    }
}
