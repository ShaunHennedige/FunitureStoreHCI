package raven.application.form;

import javax.swing.*;

public class MainFormFrame extends JFrame {

    public MainFormFrame() {
        setTitle("Main Form Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainForm mainForm = new MainForm(); // Creating an instance of MainForm JPanel
        getContentPane().add(mainForm); // Adding MainForm JPanel to the content pane of the JFrame

        pack(); // Pack the JFrame to fit its components
        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true); // Make the JFrame visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFormFrame(); // Creating an instance of MainFormFrame JFrame
        });
    }
}
