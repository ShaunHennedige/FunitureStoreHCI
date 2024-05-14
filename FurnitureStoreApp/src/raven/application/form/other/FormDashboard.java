package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

//Group 13 - Furniture Store App
public class FormDashboard extends javax.swing.JPanel {


    private Image backgroundImage;

    public FormDashboard() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        try {
            // Load the background image
            URL imageURL = getClass().getResource("../../../image/Background.png"); // Provide the path to your image
            if (imageURL != null) {
                backgroundImage = ImageIO.read(imageURL);
            } else {
                System.err.println("Image not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw the background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }





@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();
//        jButton1 = new javax.swing.JButton();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Dashboard");
    }

//        jButton1.setText("Show Notifications Test");
//        jButton1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton1ActionPerformed(evt);
//            }
//        });
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
//        this.setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
//                .addContainerGap())
//            .addGroup(layout.createSequentialGroup()
//                .addGap(325, 325, 325)
//                .addComponent(jButton1)
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(lb)
//                .addGap(173, 173, 173)
//                .addComponent(jButton1)
//                .addContainerGap(237, Short.MAX_VALUE))

//    } </editor-fold>//GEN-END:initComponents

//    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Hello sample message");
//    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lb;
    // End of variables declaration//GEN-END:variables
}
