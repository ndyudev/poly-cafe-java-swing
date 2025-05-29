package poly.cafe.ui.manager;

import poly.cafe.ui.PolyCafeController;
import poly.cafe.ui.ChangePasswordJDialog;
import poly.cafe.util.XJdbc;

public class PolyCafeJFrameManager extends javax.swing.JFrame implements PolyCafeController {

    public PolyCafeJFrameManager() {
        initComponents();
    }



    

    @Override
    public void dispose() {
        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelButton = new javax.swing.JPanel();
        btnSales = new javax.swing.JButton();
        btnHistory = new javax.swing.JButton();
        btnChangePassword = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        PanelIMG = new javax.swing.JPanel();
        btnUserManager = new javax.swing.JButton();
        btnCategoryManager = new javax.swing.JButton();
        btnCardManager = new javax.swing.JButton();
        btnRevenueManager = new javax.swing.JButton();
        btnDrinkManager = new javax.swing.JButton();
        btnBillManager = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panelAvatar = new javax.swing.JPanel();
        lblFullname = new javax.swing.JLabel();
        lblPhoto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PolyCafeJFrame");

        PanelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 0, 102), new java.awt.Color(255, 51, 102), new java.awt.Color(51, 0, 204), new java.awt.Color(153, 0, 153)));

        btnSales.setText("BÁN HÀNG");
        btnSales.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesActionPerformed(evt);
            }
        });

        btnHistory.setText("LỊCH SỬ");
        btnHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryActionPerformed(evt);
            }
        });

        btnChangePassword.setText("ĐỔI MẬT KHẨU");
        btnChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePasswordActionPerformed(evt);
            }
        });

        btnExit.setText("KẾT THÚC");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelButtonLayout = new javax.swing.GroupLayout(PanelButton);
        PanelButton.setLayout(PanelButtonLayout);
        PanelButtonLayout.setHorizontalGroup(
            PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChangePassword)
                    .addComponent(btnSales, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(btnHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelButtonLayout.setVerticalGroup(
            PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelButtonLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHistory)
                    .addComponent(btnSales))
                .addGap(18, 18, 18)
                .addGroup(PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChangePassword)
                    .addComponent(btnExit))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        PanelIMG.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUserManager.setBackground(new java.awt.Color(255, 255, 255));
        btnUserManager.setForeground(new java.awt.Color(0, 0, 0));
        btnUserManager.setText("NGƯỜI SỬ DỤNG");
        btnUserManager.setAlignmentX(1.0F);
        btnUserManager.setAlignmentY(1.0F);
        btnUserManager.setBorderPainted(false);
        btnUserManager.setFocusPainted(false);
        btnUserManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnUserManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, 240, 60));

        btnCategoryManager.setBackground(new java.awt.Color(255, 255, 255));
        btnCategoryManager.setForeground(new java.awt.Color(0, 0, 0));
        btnCategoryManager.setText("LOẠI ĐỒ UỐNG");
        btnCategoryManager.setAlignmentX(1.0F);
        btnCategoryManager.setAlignmentY(1.0F);
        btnCategoryManager.setBorderPainted(false);
        btnCategoryManager.setFocusPainted(false);
        btnCategoryManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnCategoryManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 240, 60));

        btnCardManager.setBackground(new java.awt.Color(255, 255, 255));
        btnCardManager.setForeground(new java.awt.Color(0, 0, 0));
        btnCardManager.setText("THẺ ĐINH DANH");
        btnCardManager.setAlignmentX(1.0F);
        btnCardManager.setAlignmentY(1.0F);
        btnCardManager.setBorderPainted(false);
        btnCardManager.setFocusPainted(false);
        btnCardManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCardManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnCardManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 280, 240, 60));

        btnRevenueManager.setBackground(new java.awt.Color(255, 255, 255));
        btnRevenueManager.setForeground(new java.awt.Color(0, 0, 0));
        btnRevenueManager.setText("DOANH THU");
        btnRevenueManager.setAlignmentX(1.0F);
        btnRevenueManager.setAlignmentY(1.0F);
        btnRevenueManager.setBorderPainted(false);
        btnRevenueManager.setFocusPainted(false);
        btnRevenueManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevenueManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnRevenueManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 240, 60));

        btnDrinkManager.setBackground(new java.awt.Color(255, 255, 255));
        btnDrinkManager.setForeground(new java.awt.Color(0, 0, 0));
        btnDrinkManager.setText("ĐỒ UỐNG");
        btnDrinkManager.setAlignmentX(1.0F);
        btnDrinkManager.setAlignmentY(1.0F);
        btnDrinkManager.setBorderPainted(false);
        btnDrinkManager.setFocusPainted(false);
        btnDrinkManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrinkManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnDrinkManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 260, 60));

        btnBillManager.setBackground(new java.awt.Color(255, 255, 255));
        btnBillManager.setForeground(new java.awt.Color(0, 0, 0));
        btnBillManager.setText("PHIẾU BÁN HÀNG");
        btnBillManager.setAlignmentX(1.0F);
        btnBillManager.setAlignmentY(1.0F);
        btnBillManager.setBorderPainted(false);
        btnBillManager.setFocusPainted(false);
        btnBillManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBillManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnBillManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 260, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Chau Nhat Duy\\Documents\\Java-Swing-Project\\PS44284_ChauNhatDuy_SOF2043\\PS44284_ChauNhatDuy_SOF2043\\src\\main\\java\\poly\\cafe\\icons\\coffee-shop.jpg")); // NOI18N
        PanelIMG.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -50, 800, 470));

        panelAvatar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFullname.setForeground(new java.awt.Color(255, 102, 0));
        lblFullname.setText("Châu Nhật Duy");

        lblPhoto.setIcon(new javax.swing.ImageIcon("C:\\Users\\Chau Nhat Duy\\Documents\\Java-Swing-Project\\PS44284_ChauNhatDuy_SOF2043\\PS44284_ChauNhatDuy_SOF2043\\src\\main\\java\\poly\\cafe\\icons\\avatar.jpg")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(PanelIMG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(PanelIMG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(panelAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPhoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFullname)
                        .addGap(99, 99, 99)
                        .addComponent(PanelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryActionPerformed
        
    }//GEN-LAST:event_btnHistoryActionPerformed

    private void btnSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesActionPerformed
    }//GEN-LAST:event_btnSalesActionPerformed

    private void btnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePasswordActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnChangePasswordActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnUserManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserManagerActionPerformed
    }//GEN-LAST:event_btnUserManagerActionPerformed

    private void btnBillManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillManagerActionPerformed
    }//GEN-LAST:event_btnBillManagerActionPerformed

    private void btnCategoryManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryManagerActionPerformed
    }//GEN-LAST:event_btnCategoryManagerActionPerformed

    private void btnCardManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCardManagerActionPerformed

    }//GEN-LAST:event_btnCardManagerActionPerformed

    private void btnRevenueManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevenueManagerActionPerformed
    }//GEN-LAST:event_btnRevenueManagerActionPerformed

    private void btnDrinkManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrinkManagerActionPerformed
    }//GEN-LAST:event_btnDrinkManagerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PolyCafeJFrameManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PolyCafeJFrameManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PolyCafeJFrameManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PolyCafeJFrameManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PolyCafeJFrameManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelButton;
    private javax.swing.JPanel PanelIMG;
    private javax.swing.JButton btnBillManager;
    private javax.swing.JButton btnCardManager;
    private javax.swing.JButton btnCategoryManager;
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JButton btnDrinkManager;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHistory;
    private javax.swing.JButton btnRevenueManager;
    private javax.swing.JButton btnSales;
    private javax.swing.JButton btnUserManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblFullname;
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JPanel panelAvatar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
        this.setTitle("Poly Cafe Manager");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        // Thêm logic kết nối nếu cần
         XJdbc.openConnection();
    }
}
