//package raven.application.form.other;
//
//import com.formdev.flatlaf.FlatClientProperties;
//
//import javax.swing.*;
//
////Group 13 - Furniture Store App
//public class FormChairsStandard extends javax.swing.JPanel {
//
//    public FormChairsStandard() {
//        initComponents();
//        lb.putClientProperty(FlatClientProperties.STYLE, ""
//                + "font:$h1.font");
//    }
//
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        lb = new javax.swing.JLabel();
//
//        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        lb.setVerticalAlignment(SwingConstants.NORTH);
//        lb.setText("Standard Chairs");
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
//        this.setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
//                .addContainerGap())
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
//                .addContainerGap())
//        );
//    }// </editor-fold>//GEN-END:initComponents
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JLabel lb;
//    // End of variables declaration//GEN-END:variables
//}
//

package raven.application.form.other;

import javax.swing.*;

public class FormBedDouble extends javax.swing.JPanel {

    public FormBedDouble() {
        initComponents();
        embedDoubleBedPanel();
    }

    private void embedDoubleBedPanel() {
        DoubleBed doubleBedPanel = new DoubleBed();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lb, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(doubleBedPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(doubleBedPanel, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setVerticalAlignment(javax.swing.SwingConstants.NORTH);
        lb.setText("Double Beds");

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

