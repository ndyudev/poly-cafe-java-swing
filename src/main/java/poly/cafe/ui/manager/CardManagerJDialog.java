package poly.cafe.ui.manager;

import poly.cafe.dao.CardDAO;
import poly.cafe.dao.impl.CardDAOImpl;
import poly.cafe.entity.Card;
import poly.cafe.util.XDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CardManagerJDialog extends javax.swing.JDialog implements CardController {

    private CardDAO cardDAO;
    private List<Card> cardList;
    private int currentIndex = -1;

    public CardManagerJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        cardDAO = new CardDAOImpl();
        initComponents();
        init();
        open();
    }

    @Override
    public void open() {
        setLocationRelativeTo(null);
        setTitle("Quản lý thẻ - Poly Cafe");
        cardList = cardDAO.findAll();
        fillToTable();
        if (!cardList.isEmpty()) {
            currentIndex = 0;
            setForm(cardList.get(currentIndex));
            tblCards.setRowSelectionInterval(currentIndex, currentIndex);
        }
        setEditable(false);
        updateNavigationButtons();
    }

    @Override
    public void setForm(Card card) {
        if (card == null) {
            return;
        }
        txtIdCard.setText(card.getId() != null ? card.getId().toString() : "");
        int status = card.getStatus();
        chkOperating.setSelected(status == 0);
        chkError.setSelected(status == 1);
        chkLose.setSelected(status == 2);
        if (currentIndex >= 0 && currentIndex < cardList.size()) {
            tblCards.setRowSelectionInterval(currentIndex, currentIndex);
        }
    }

    @Override
    public Card getForm() {
        String idStr = txtIdCard.getText().trim();
        if (idStr.isEmpty()) {
            XDialog.alert("Vui lòng nhập mã thẻ!");
            return null;
        }

        Integer id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            XDialog.alert("Mã thẻ phải là số nguyên!");
            return null;
        }

        int status = getSelectedStatus();
        if (status == -1) {
            XDialog.alert("Vui lòng chọn trạng thái!");
            return null;
        }

        return Card.builder().Id(id).Status(status).build();
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblCards.getModel();
        model.setRowCount(0);
        for (Card card : cardList) {
            model.addRow(new Object[]{card.getId(), getStatusString(card.getStatus()), false});
        }
    }

    @Override
    public void edit() {
        int selectedRow = tblCards.getSelectedRow();
        if (selectedRow >= 0) {
            currentIndex = selectedRow;
            setForm(cardList.get(currentIndex));
            setEditable(true);
            txtIdCard.setEditable(false);
            jTabbedPane1.setSelectedIndex(1);
            updateNavigationButtons();
        }
    }

    @Override
    public void create() {
        Card card = getForm();
        if (card == null) {
            return;
        }

        Card existingCard = cardDAO.findById(card.getId());
        if (existingCard != null) {
            XDialog.alert("Mã thẻ đã tồn tại!");
            return;
        }

        try {
            cardDAO.create(card);
            cardList = cardDAO.findAll();
            fillToTable();
            clear();
            XDialog.alert("Tạo thẻ thành công!");
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tạo thẻ: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        Card card = getForm();
        if (card == null) {
            return;
        }

        Card existingCard = cardDAO.findById(card.getId());
        if (existingCard == null) {
            XDialog.alert("Mã thẻ không tồn tại!");
            return;
        }

        try {
            cardDAO.update(card);
            cardList = cardDAO.findAll();
            fillToTable();
            setForm(cardList.get(currentIndex));
            XDialog.alert("Cập nhật thẻ thành công!");
        } catch (Exception e) {
            XDialog.alert("Lỗi khi cập nhật thẻ: " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        String idStr = txtIdCard.getText().trim();
        if (idStr.isEmpty()) {
            XDialog.alert("Vui lòng nhập mã thẻ!");
            return;
        }

        Integer id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            XDialog.alert("Mã thẻ phải là số nguyên!");
            return;
        }

        if (cardDAO.findById(id) == null) {
            XDialog.alert("Mã thẻ không tồn tại!");
            return;
        }

        if (XDialog.confirm("Bạn có chắc muốn xóa thẻ này?")) {
            try {
                cardDAO.deleteById(id);
                cardList = cardDAO.findAll();
                fillToTable();
                clear();
                currentIndex = -1;
                updateNavigationButtons();
                XDialog.alert("Xóa thẻ thành công!");
            } catch (Exception e) {
                XDialog.alert("Lỗi khi xóa thẻ: " + e.getMessage());
            }
        }
    }

    @Override
    public void clear() {
        txtIdCard.setText("");
        chkOperating.setSelected(false);
        chkError.setSelected(false);
        chkLose.setSelected(false);
        setEditable(true);
        txtIdCard.setEditable(true);
        currentIndex = -1;
        updateNavigationButtons();
    }

    @Override
    public void setEditable(boolean editable) {
        txtIdCard.setEditable(editable);
        chkOperating.setEnabled(editable);
        chkError.setEnabled(editable);
        chkLose.setEnabled(editable);
        btnCreate.setEnabled(editable);
        btnUpdate.setEnabled(editable && currentIndex >= 0);
        btnDelete.setEnabled(editable && currentIndex >= 0);
    }

    @Override
    public void checkAll() {
        for (int i = 0; i < tblCards.getRowCount(); i++) {
            tblCards.setValueAt(true, i, 2);
        }
    }

    @Override
    public void uncheckAll() {
        for (int i = 0; i < tblCards.getRowCount(); i++) {
            tblCards.setValueAt(false, i, 2);
        }
    }

    @Override
    public void deleteCheckedItems() {
        if (XDialog.confirm("Bạn có chắc muốn xóa các thẻ được chọn?")) {
            try {
                boolean hasDeletion = false;
                for (int i = tblCards.getRowCount() - 1; i >= 0; i--) {
                    if ((Boolean) tblCards.getValueAt(i, 2)) {
                        Integer id = (Integer) tblCards.getValueAt(i, 0);
                        cardDAO.deleteById(id);
                        hasDeletion = true;
                    }
                }
                if (hasDeletion) {
                    cardList = cardDAO.findAll();
                    fillToTable();
                    clear();
                    XDialog.alert("Xóa các thẻ được chọn thành công!");
                }
            } catch (Exception e) {
                XDialog.alert("Lỗi khi xóa các thẻ: " + e.getMessage());
            }
        }
    }

    @Override
    public void moveFirst() {
        if (!cardList.isEmpty()) {
            currentIndex = 0;
            moveTo(currentIndex);
        }
    }

    @Override
    public void movePrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            moveTo(currentIndex);
        }
    }

    @Override
    public void moveNext() {
        if (currentIndex < cardList.size() - 1) {
            currentIndex++;
            moveTo(currentIndex);
        }
    }

    @Override
    public void moveLast() {
        if (!cardList.isEmpty()) {
            currentIndex = cardList.size() - 1;
            moveTo(currentIndex);
        }
    }

    @Override
    public void moveTo(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < cardList.size()) {
            currentIndex = rowIndex;
            setForm(cardList.get(currentIndex));
            tblCards.setRowSelectionInterval(currentIndex, currentIndex);
            jTabbedPane1.setSelectedIndex(1);
            setEditable(true);
            txtIdCard.setEditable(false);
            updateNavigationButtons();
        }
    }

    private int getSelectedStatus() {
        if (chkOperating.isSelected()) {
            return 0;
        }
        if (chkError.isSelected()) {
            return 1;
        }
        if (chkLose.isSelected()) {
            return 2;
        }
        return -1;
    }

    private String getStatusString(int status) {
        switch (status) {
            case 0:
                return "Operating";
            case 1:
                return "Error";
            case 2:
                return "Lose";
            default:
                return "Unknown";
        }
    }

    private void ensureSingleCheckboxSelection(JCheckBox selectedCheckBox) {
        if (selectedCheckBox == chkOperating) {
            chkError.setSelected(false);
            chkLose.setSelected(false);
        } else if (selectedCheckBox == chkError) {
            chkOperating.setSelected(false);
            chkLose.setSelected(false);
        } else if (selectedCheckBox == chkLose) {
            chkOperating.setSelected(false);
            chkError.setSelected(false);
        }
    }

    private void updateNavigationButtons() {
        btnMoveFirst.setEnabled(currentIndex > 0);
        btnMovePrevious.setEnabled(currentIndex > 0);
        btnMoveNext.setEnabled(currentIndex < cardList.size() - 1);
        btnMoveLast.setEnabled(currentIndex < cardList.size() - 1);
    }

    private void init() {
        // Thêm sự kiện nhấp đúp vào bảng để chỉnh sửa
        tblCards.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    edit();
                }
            }
        });

        // Đồng bộ checkbox trạng thái
        chkOperating.addActionListener(e -> ensureSingleCheckboxSelection(chkOperating));
        chkError.addActionListener(e -> ensureSingleCheckboxSelection(chkError));
        chkLose.addActionListener(e -> ensureSingleCheckboxSelection(chkLose));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCards = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btnDeleteCheckedItems = new javax.swing.JButton();
        btnUncheckAll = new javax.swing.JButton();
        btnCheckAll = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdCard = new javax.swing.JTextField();
        chkOperating = new javax.swing.JCheckBox();
        chkError = new javax.swing.JCheckBox();
        chkLose = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        btnCreate = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnMoveLast = new javax.swing.JButton();
        btnMoveFirst = new javax.swing.JButton();
        btnMovePrevious = new javax.swing.JButton();
        btnMoveNext = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblCards.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã thẻ", "Trạng thái", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCards);

        btnDeleteCheckedItems.setText("Xóa chọn các mục");
        btnDeleteCheckedItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCheckedItemsActionPerformed(evt);
            }
        });

        btnUncheckAll.setText("Bỏ chọn tất cả");
        btnUncheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUncheckAllActionPerformed(evt);
            }
        });

        btnCheckAll.setText("Chọn tất cả");
        btnCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCheckAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUncheckAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCheckedItems)))
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteCheckedItems)
                    .addComponent(btnUncheckAll)
                    .addComponent(btnCheckAll))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DANH SÁCH", jPanel1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Mã thẻ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Trạng thái");

        chkOperating.setText("Operating");
        chkOperating.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOperatingActionPerformed(evt);
            }
        });

        chkError.setText("Error");
        chkError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkErrorActionPerformed(evt);
            }
        });

        chkLose.setText("Lose");
        chkLose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLoseActionPerformed(evt);
            }
        });

        btnCreate.setText("Tạo mới");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnUpdate.setText("Cập nhập");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText("Nhập mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnMoveLast.setText(">|");
        btnMoveLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveLastActionPerformed(evt);
            }
        });

        btnMoveFirst.setText("|<");
        btnMoveFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveFirstActionPerformed(evt);
            }
        });

        btnMovePrevious.setText("<<");
        btnMovePrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovePreviousActionPerformed(evt);
            }
        });

        btnMoveNext.setText(">>");
        btnMoveNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(chkOperating)
                        .addGap(18, 18, 18)
                        .addComponent(chkError)
                        .addGap(18, 18, 18)
                        .addComponent(chkLose)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addComponent(txtIdCard, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnCreate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                                .addComponent(btnMoveFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMovePrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMoveNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMoveLast, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkOperating)
                    .addComponent(chkLose)
                    .addComponent(chkError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear)
                    .addComponent(btnMoveLast)
                    .addComponent(btnMoveNext)
                    .addComponent(btnMovePrevious)
                    .addComponent(btnMoveFirst))
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("BIỂU MẪU", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoveNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveNextActionPerformed
        moveNext();
    }//GEN-LAST:event_btnMoveNextActionPerformed

    private void btnCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckAllActionPerformed
        checkAll();
    }//GEN-LAST:event_btnCheckAllActionPerformed

    private void btnUncheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUncheckAllActionPerformed
        uncheckAll();
    }//GEN-LAST:event_btnUncheckAllActionPerformed

    private void btnDeleteCheckedItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCheckedItemsActionPerformed
        deleteCheckedItems();
    }//GEN-LAST:event_btnDeleteCheckedItemsActionPerformed

    private void chkOperatingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOperatingActionPerformed

    }//GEN-LAST:event_chkOperatingActionPerformed

    private void chkErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkErrorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkErrorActionPerformed

    private void chkLoseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLoseActionPerformed
        
    }//GEN-LAST:event_chkLoseActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        create();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnMoveFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveFirstActionPerformed
        moveFirst();
    }//GEN-LAST:event_btnMoveFirstActionPerformed

    private void btnMovePreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovePreviousActionPerformed
        movePrevious();
    }//GEN-LAST:event_btnMovePreviousActionPerformed

    private void btnMoveLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveLastActionPerformed
        moveLast();
    }//GEN-LAST:event_btnMoveLastActionPerformed

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
            java.util.logging.Logger.getLogger(CardManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CardManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CardManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CardManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CardManagerJDialog dialog = new CardManagerJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckAll;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteCheckedItems;
    private javax.swing.JButton btnMoveFirst;
    private javax.swing.JButton btnMoveLast;
    private javax.swing.JButton btnMoveNext;
    private javax.swing.JButton btnMovePrevious;
    private javax.swing.JButton btnUncheckAll;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox chkError;
    private javax.swing.JCheckBox chkLose;
    private javax.swing.JCheckBox chkOperating;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblCards;
    private javax.swing.JTextField txtIdCard;
    // End of variables declaration//GEN-END:variables
}
