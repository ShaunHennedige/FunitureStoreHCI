package raven.application;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import raven.application.form.LoginForm;
import raven.application.form.MainForm;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

//Group 13 - Furniture Store App - Furniture Store App

public class Application extends javax.swing.JFrame {

    private static Application app;
    private final MainForm mainForm;
    private final LoginForm loginForm;

    public Application() {
        initComponents();
        setSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        mainForm = new MainForm();
        loginForm = new LoginForm();
        setContentPane(loginForm);
        setTitle("Furniture Store Desktop Application");
        Notifications.getInstance().setJFrame(this);
    }

    public static void showForm(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainForm.showForm(component);
    }

    public static void login() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainForm);
        app.mainForm.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0, 0);
        app.mainForm.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void logout() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.loginForm);
        app.loginForm.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.loginForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void setSelectedMenu(int index, int subIndex) {
        app.mainForm.setSelectedMenu(index, subIndex);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setUndecorated(true);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.theme");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();

//        SwingUtilities.invokeLater(() -> {
//                app = new Application();
//                app.setVisible(true);
//
//                JFrame loginFrame = new JFrame("Login Form");
//                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                loginFrame.setContentPane(app.loginForm);
//                loginFrame.setSize(new Dimension(400, 300));
//                loginFrame.setLocationRelativeTo(null);
//                loginFrame.setVisible(true);


        ////                // Create a new JFrame instance
//                JFrame frame = new JFrame("Furniture Store App");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                // Create an instance of the MainForm JPanel
//                MainForm mainForm = new MainForm();
//
//                // Set the MainForm JPanel as the content pane of the JFrame
//                frame.setContentPane(app.loginForm);
//
//                // Set the size of the JFrame
//                frame.setSize(new Dimension(1366, 768));
//
//                // Center the JFrame on the screen
//                frame.setLocationRelativeTo(null);
//
//                // Make the JFrame visible
//                frame.setVisible(true);
//            });

        java.awt.EventQueue.invokeLater(() -> {
            app = new Application();
            //  app.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            app.setVisible(true);

        });
    };
}

//
//        SwingUtilities.invokeLater(() -> {
//            // Create a new JFrame instance
//            JFrame frame = new JFrame("Main Form Frame");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            // Create an instance of the MainForm JPanel
//            MainForm mainForm = new MainForm();
//
//            // Set the MainForm JPanel as the content pane of the JFrame
//            frame.setContentPane(mainForm);
//
//            // Set the size of the JFrame
//            frame.setSize(new Dimension(1366, 768));
//
//            // Center the JFrame on the screen
//            frame.setLocationRelativeTo(null);
//
//            // Make the JFrame visible
//            frame.setVisible(true);
//        });




