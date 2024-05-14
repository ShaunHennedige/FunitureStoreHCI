//package raven.application.form.other;
//
//import com.formdev.flatlaf.FlatClientProperties;
//
//import javax.swing.*;
//
////Group 13 - Furniture Store App
//public class FormChairsArm extends JPanel {
//
//    public FormChairsArm() {
//        initComponents();
//        lb.putClientProperty(FlatClientProperties.STYLE, ""
//                + "font:$h1.font");
//    }
//
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        lb = new JLabel();
//
//        lb.setHorizontalAlignment(SwingConstants.CENTER);
//        lb.setVerticalAlignment(SwingConstants.NORTH);
//        lb.setText("Arm Chairs");
//
//        GroupLayout layout = new GroupLayout(this);
//        this.setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(lb, GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
//                .addContainerGap())
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(lb, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
//                .addContainerGap())
//        );
//    }// </editor-fold>//GEN-END:initComponents
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private JLabel lb;
//    // End of variables declaration//GEN-END:variables
//}

package raven.application.form.other;

import javax.swing.*;

public class FormChairsArm extends javax.swing.JPanel {

    public FormChairsArm() {
        initComponents();
        embedArmChairPanel();
    }

    private void embedArmChairPanel() {
        ArmChair armChairPanel = new ArmChair();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(armChairPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(armChairPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setVerticalAlignment(SwingConstants.NORTH);
        lb.setText("Arm Chair");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lb;
    // End of variables declaration//GEN-END:variables
}
