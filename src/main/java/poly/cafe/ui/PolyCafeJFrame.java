package poly.cafe.ui;

import poly.cafe.util.XAuth;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XDialog;

public final class PolyCafeJFrame extends javax.swing.JFrame implements PolyCafeController {

    public PolyCafeJFrame() {
        initComponents();
        init();
        displayUserInfo();
        checkAccess();
    }

    private void displayUserInfo() {
        if (XAuth.user != null) {
            lblFullname.setText(XAuth.user.getFullname());
//            String photoPath = XAuth.user.getPhoto();
//            if (photoPath != null && !photoPath.isEmpty()) {
//                imagePanel.setIcon("");
//            } else {
//                imagePanel.setIcon(null);
//            }
//        } else {
//            lblFullname.setText("Không xác định");
//            imagePanel.setIcon(null);
        }
    }

    private void checkAccess() {
        if (XAuth.user == null || !XAuth.user.isManager()) {
            btnUserManager.setEnabled(false);
            btnRevenueManager.setEnabled(false);
            btnCategoryManager.setEnabled(false);
            btnDrinkManager.setEnabled(false);
            btnCardManager.setEnabled(false);
            btnBillManager.setEnabled(false);
        }
    }

    @Override
    public void dispose() {
        XJdbc.closeConnection();
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
        lblFullname = new javax.swing.JLabel();
        panelAvatar = new javax.swing.JPanel();
        lblAvatar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PolyCafeJFrame");

        PanelButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 102, 0)));

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
        btnUserManager.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnUserManager.setForeground(new java.awt.Color(0, 0, 0));
        btnUserManager.setText("NGƯỜI SỬ DỤNG");
        btnUserManager.setAlignmentX(1.0F);
        btnUserManager.setAlignmentY(1.0F);
        btnUserManager.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0), 1, true));
        btnUserManager.setBorderPainted(false);
        btnUserManager.setFocusPainted(false);
        btnUserManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnUserManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 240, 60));

        btnCategoryManager.setBackground(new java.awt.Color(255, 255, 255));
        btnCategoryManager.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnCategoryManager.setForeground(new java.awt.Color(0, 0, 0));
        btnCategoryManager.setText("LOẠI ĐỒ UỐNG");
        btnCategoryManager.setAlignmentX(1.0F);
        btnCategoryManager.setAlignmentY(1.0F);
        btnCategoryManager.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0), 1, true));
        btnCategoryManager.setBorderPainted(false);
        btnCategoryManager.setFocusPainted(false);
        btnCategoryManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnCategoryManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 240, 60));

        btnCardManager.setBackground(new java.awt.Color(255, 255, 255));
        btnCardManager.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnCardManager.setForeground(new java.awt.Color(0, 0, 0));
        btnCardManager.setText("THẺ ĐINH DANH");
        btnCardManager.setAlignmentX(1.0F);
        btnCardManager.setAlignmentY(1.0F);
        btnCardManager.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0), 1, true));
        btnCardManager.setBorderPainted(false);
        btnCardManager.setFocusPainted(false);
        btnCardManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCardManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnCardManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 280, 240, 60));

        btnRevenueManager.setBackground(new java.awt.Color(255, 255, 255));
        btnRevenueManager.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnRevenueManager.setForeground(new java.awt.Color(0, 0, 0));
        btnRevenueManager.setText("DOANH THU");
        btnRevenueManager.setAlignmentX(1.0F);
        btnRevenueManager.setAlignmentY(1.0F);
        btnRevenueManager.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0), 1, true));
        btnRevenueManager.setBorderPainted(false);
        btnRevenueManager.setFocusPainted(false);
        btnRevenueManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevenueManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnRevenueManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 240, 60));

        btnDrinkManager.setBackground(new java.awt.Color(255, 255, 255));
        btnDrinkManager.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnDrinkManager.setForeground(new java.awt.Color(0, 0, 0));
        btnDrinkManager.setText("ĐỒ UỐNG");
        btnDrinkManager.setAlignmentX(1.0F);
        btnDrinkManager.setAlignmentY(1.0F);
        btnDrinkManager.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0), 1, true));
        btnDrinkManager.setBorderPainted(false);
        btnDrinkManager.setFocusPainted(false);
        btnDrinkManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDrinkManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnDrinkManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 260, 60));

        btnBillManager.setBackground(new java.awt.Color(255, 255, 255));
        btnBillManager.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btnBillManager.setForeground(new java.awt.Color(0, 0, 0));
        btnBillManager.setText("PHIẾU BÁN HÀNG");
        btnBillManager.setAlignmentX(1.0F);
        btnBillManager.setAlignmentY(1.0F);
        btnBillManager.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0), 1, true));
        btnBillManager.setBorderPainted(false);
        btnBillManager.setFocusPainted(false);
        btnBillManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBillManagerActionPerformed(evt);
            }
        });
        PanelIMG.add(btnBillManager, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 260, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Chau Nhat Duy\\Documents\\Java-Swing-Project\\PS44284_ChauNhatDuy_SOF2043\\PS44284_ChauNhatDuy_SOF2043\\src\\main\\java\\poly\\cafe\\icons\\coffee-shop.jpg")); // NOI18N
        PanelIMG.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -50, 800, 470));

        lblFullname.setForeground(new java.awt.Color(255, 102, 0));
        lblFullname.setText("Châu Nhật Duy");

        lblAvatar.setText("jLabel2");

        javax.swing.GroupLayout panelAvatarLayout = new javax.swing.GroupLayout(panelAvatar);
        panelAvatar.setLayout(panelAvatarLayout);
        panelAvatarLayout.setHorizontalGroup(
            panelAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAvatarLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(lblAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panelAvatarLayout.setVerticalGroup(
            panelAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAvatarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAvatar)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PanelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(74, 74, 74)
                            .addComponent(panelAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelIMG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PanelIMG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(panelAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFullname)
                .addGap(64, 64, 64)
                .addComponent(PanelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryActionPerformed
        XDialog.alert("Tính năng đang phát triển");
    }//GEN-LAST:event_btnHistoryActionPerformed

    private void btnSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesActionPerformed
        XDialog.alert("Tính năng đang phát triển");
    }//GEN-LAST:event_btnSalesActionPerformed

    private void btnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePasswordActionPerformed
        showChangePasswordJDialog(this);
    }//GEN-LAST:event_btnChangePasswordActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        if (XDialog.confirm("Bạn muốn thoát chương trình?")) {
            dispose();
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnUserManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserManagerActionPerformed
        showUserManagerJDialog(this);
    }//GEN-LAST:event_btnUserManagerActionPerformed

    private void btnBillManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillManagerActionPerformed
        XDialog.alert("Tính năng đang phát triển");
    }//GEN-LAST:event_btnBillManagerActionPerformed

    private void btnCategoryManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryManagerActionPerformed
        showCategoryManagerJDialog(this);
    }//GEN-LAST:event_btnCategoryManagerActionPerformed

    private void btnCardManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCardManagerActionPerformed
        showCardManagerJDialog(this);
    }//GEN-LAST:event_btnCardManagerActionPerformed

    private void btnRevenueManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevenueManagerActionPerformed
        XDialog.alert("Tính năng đang phát triển");
    }//GEN-LAST:event_btnRevenueManagerActionPerformed

    private void btnDrinkManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDrinkManagerActionPerformed
        XDialog.alert("Tính năng đang phát triển");
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
            java.util.logging.Logger.getLogger(PolyCafeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PolyCafeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PolyCafeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PolyCafeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new PolyCafeJFrame().setVisible(true);
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
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblFullname;
    private javax.swing.JPanel panelAvatar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
        this.setTitle("Poly Cafe Manager");
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        // Thêm logic kết nối nếu cần
        XJdbc.openConnection();
    }
}
